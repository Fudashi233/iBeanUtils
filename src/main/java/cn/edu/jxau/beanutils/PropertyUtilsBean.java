package cn.edu.jxau.beanutils;


import cn.edu.jxau.beanutils.expression.DefaultResolver;
import cn.edu.jxau.beanutils.expression.Resolver;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2019/9/2
 * Time:下午9:11
 */
public class PropertyUtilsBean {

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private BeanIntrospectionCache beanIntrospectionCache = new BeanIntrospectionCache();
    private Resolver resolver = new DefaultResolver();

    public PropertyDescriptor getPropertyDescriptor(Object bean, String expr) throws IntrospectionException,
            InvocationTargetException, IllegalAccessException {

        while (resolver.hasNested(expr)) {
            String curNextExpr = resolver.next(expr);
            Object nestBean = getProperty(bean, curNextExpr);
            if (Objects.isNull(nestBean)) {
                throw new RuntimeException("获取内嵌bean失败，class=" + bean.getClass() + "，expr=" + expr);
            }
            expr = resolver.remove(expr);
            bean = nestBean;
        }
        String propName = resolver.getProperty(expr);
        if (Objects.isNull(propName)) {
            return null;
        }
        return beanIntrospectionCache.getPropDesc(bean, propName);
    }

    public Object getProperty(Object bean, String name) throws IllegalAccessException,
            IntrospectionException, InvocationTargetException {
        return getNestedProperty(bean, name);
    }

    public Object getSimpleProperty(Object bean, String expr) throws InvocationTargetException,
            IllegalAccessException, IntrospectionException {

        Objects.requireNonNull(bean, "bean cannot be null");
        Objects.requireNonNull(expr, "name cannot be null");
        if (resolver.hasNested(expr)) {
            throw new IllegalArgumentException("property expression has nested，expr：" + expr);
        } else if (resolver.isIndexed(expr)) {
            throw new IllegalArgumentException("property expression is indexed，expr：" + expr);
        } else if (resolver.isMapped(expr)) {
            throw new IllegalArgumentException("property expression is mapped，expr：" + expr);
        }
        PropertyDescriptor propDesc = getPropertyDescriptor(bean, expr);
        if (Objects.isNull(propDesc)) {
            return null;
        }
        Method readMethod = propDesc.getReadMethod();
        return Objects.isNull(readMethod) ? null : readMethod.invoke(bean, EMPTY_OBJECT_ARRAY);
    }

    public Object getIndexedProperty(Object bean, String expr) throws IntrospectionException,
            InvocationTargetException, IllegalAccessException {

        Objects.requireNonNull(bean, "bean cannot be null");
        Objects.requireNonNull(expr, "expr cannot be null");
        int index = resolver.getIndex(expr);
        if (index < 0) {
            throw new IllegalArgumentException("property expression cannot get the index，expr：" + expr);
        }
        String propName = resolver.getProperty(expr);
        PropertyDescriptor propDesc = getPropertyDescriptor(bean, propName);
        Method readMethod = propDesc.getReadMethod();
        Object prop = Objects.isNull(readMethod) ? null : readMethod.invoke(bean, EMPTY_OBJECT_ARRAY);
        if (Objects.isNull(prop)) {
            return null;
        }
        if (prop.getClass().isArray()) {
            return Array.get(prop, index);
        } else if (prop instanceof List) {
            return ((List) prop).get(index);
        } else {
            throw new RuntimeException("the prop can not indexed");
        }
    }

    public Object getMappedProperty(Object bean, String expr) throws IntrospectionException,
            InvocationTargetException, IllegalAccessException {

        Objects.requireNonNull(bean, "bean cannot be null");
        Objects.requireNonNull(expr, "expr cannot be null");
        String key = resolver.getKey(expr);
        if (Objects.isNull(key)) {
            throw new IllegalArgumentException("property expression cannot get the key，expr：" + expr);
        }
        String propName = resolver.getProperty(expr);
        PropertyDescriptor propDesc = getPropertyDescriptor(bean, propName);
        propDesc.getReadMethod();
        Method readMethod = propDesc.getReadMethod();
        Object prop = Objects.isNull(readMethod) ? null : readMethod.invoke(bean, EMPTY_OBJECT_ARRAY);
        if (Objects.isNull(prop)) {
            return null;
        }
        if (prop instanceof Map) {
            return ((Map) prop).get(key);
        } else {
            throw new RuntimeException("the prop can not mapped");
        }
    }

    public Object getPropertyOfMapBean(Map<?, ?> mapBean, String expr) {

        String key = resolver.getProperty(expr);
        if (Objects.isNull(key)) {
            throw new IllegalArgumentException("property expression cannot get the property，expr：" + expr);
        }
        return mapBean.get(key);
    }

    public Object getNestedProperty(Object bean, String expr) throws IllegalAccessException,
            IntrospectionException, InvocationTargetException {

        Objects.requireNonNull(bean, "bean cannot be null");
        Objects.requireNonNull(expr, "expr cannot be null");
        while (resolver.hasNested(expr)) {
            String curNestedExpr = resolver.next(expr);
            Object curNestedBean = null;
            if (bean instanceof Map) {
                curNestedBean = getPropertyOfMapBean((Map<?, ?>) bean, curNestedExpr);
            } else if (resolver.isIndexed(expr)) {
                curNestedBean = getIndexedProperty(bean, curNestedExpr);
            } else if (resolver.isMapped(expr)) {
                curNestedBean = getMappedProperty(bean, curNestedExpr);
            } else {
                curNestedBean = getSimpleProperty(bean, curNestedExpr);
            }
            expr = resolver.remove(expr);
            bean = curNestedBean;
        }
        if (bean instanceof Map) {
            return getPropertyOfMapBean((Map<?, ?>) bean, expr);
        } else if (resolver.isIndexed(expr)) {
            return getIndexedProperty(bean, expr);
        } else if (resolver.isMapped(expr)) {
            return getMappedProperty(bean, expr);
        } else {
            return getSimpleProperty(bean, expr);
        }
    }

    public void setIndexedProperty(Object bean, String expr, Object value) throws IllegalAccessException,
            IntrospectionException, InvocationTargetException {

        Objects.requireNonNull(bean, "bean cannot be null");
        Objects.requireNonNull(bean, "expr cannot be null");
        int index = resolver.getIndex(expr);
        if (index < 0) {
            throw new IllegalArgumentException("property expression cannot get the index，expr：" + expr);
        }
        String propName = resolver.getProperty(expr);
        PropertyDescriptor propDesc = getPropertyDescriptor(bean, propName);
        Method readMethod = propDesc.getReadMethod();
        Object prop = Objects.isNull(readMethod) ? null : readMethod.invoke(bean, EMPTY_OBJECT_ARRAY);
        if (Objects.isNull(prop)) {
            return;
        }
        if (prop.getClass().isArray()) {
            Array.set(prop, index, value);
        } else if (prop instanceof List) {
            ((List) prop).set(index, value);
        } else {
            throw new RuntimeException("the prop can not indexed");
        }
    }

    public void setMappedProperty(Object bean, String expr, Object value) throws IllegalAccessException,
            IntrospectionException, InvocationTargetException {

        Objects.requireNonNull(bean, "bean cannot be null");
        Objects.requireNonNull(bean, "expr cannot be null");
        String key = resolver.getKey(expr);
        if (Objects.isNull(key)) {
            throw new IllegalArgumentException("property expression cannot get the key，expr：" + expr);
        }
        String propName = resolver.getProperty(expr);
        PropertyDescriptor propDesc = getPropertyDescriptor(bean, propName);
        Method readMethod = propDesc.getReadMethod();
        Object prop = Objects.isNull(readMethod) ? null : readMethod.invoke(bean, EMPTY_OBJECT_ARRAY);
        if (Objects.isNull(prop)) {
            return;
        }
        if (prop instanceof Map) {
            ((Map) prop).put(key, value);
        } else {
            throw new RuntimeException("the prop can not mapped");
        }
    }

    public void setSimpleProperty(Object bean, String expr, Object value) throws InvocationTargetException,
            IllegalAccessException, IntrospectionException {

        Objects.requireNonNull(bean, "bean cannot be null");
        Objects.requireNonNull(expr, "name cannot be null");
        if (resolver.hasNested(expr)) {
            throw new IllegalArgumentException("property expression has nested，expr：" + expr);
        } else if (resolver.isIndexed(expr)) {
            throw new IllegalArgumentException("property expression is indexed，expr：" + expr);
        } else if (resolver.isMapped(expr)) {
            throw new IllegalArgumentException("property expression is mapped，expr：" + expr);
        }
        PropertyDescriptor propDesc = getPropertyDescriptor(bean, expr);
        if (Objects.isNull(propDesc)) {
            return;
        }
        Method writeMethod = propDesc.getWriteMethod();
        if (Objects.nonNull(writeMethod)) {
            writeMethod.invoke(bean, value);
        }
    }

    public void setPropertyOfMapBean(Map<String, Object> mapBean, String expr, Object value) {

        String key = resolver.getProperty(expr);
        if (Objects.isNull(key)) {
            throw new IllegalArgumentException("property expression cannot get the property，expr：" + expr);
        }
        mapBean.put(key, value);
    }

    public void setNestedProperty(Object bean, String expr, Object value) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        Objects.requireNonNull(bean, "bean cannot be null");
        Objects.requireNonNull(expr, "expr cannot be null");
        while (resolver.hasNested(expr)) {
            String curNestedExpr = resolver.next(expr);
            Object curNestedBean = null;
            if (bean instanceof Map) {
                curNestedBean = getPropertyOfMapBean((Map<?, ?>) bean, curNestedExpr);
            } else if (resolver.isMapped(curNestedExpr)) {
                curNestedBean = getMappedProperty(bean, expr);
            } else if (resolver.isIndexed(curNestedExpr)) {
                curNestedBean = getIndexedProperty(bean, expr);
            } else {
                curNestedBean = getSimpleProperty(bean, expr);
            }
            expr = resolver.remove(expr);
            bean = curNestedBean;
        }

        if (bean instanceof Map) {
            setPropertyOfMapBean((Map<String, Object>) bean, expr, value);
        } else if (resolver.isIndexed(expr)) {
            setIndexedProperty(bean, expr, value);
        } else if (resolver.isMapped(expr)) {
            setMappedProperty(bean, expr, value);
        } else {
            setSimpleProperty(bean, expr, value);
        }
    }

    public void setProperty(Object bean, String expr, Object value) throws IllegalAccessException,
            IntrospectionException, InvocationTargetException {

        setNestedProperty(bean, expr, value);
    }
}
