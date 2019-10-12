package cn.edu.jxau.beanutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Objects;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2019/9/4
 * Time:下午7:32
 */
public class BeanIntrospectionCache {

    private WeakFastHashMap<Class, PropertyDescriptor[]> propDescArrMap;

    public BeanIntrospectionCache() {
        this.propDescArrMap = new WeakFastHashMap<>();
    }

    public PropertyDescriptor getPropDesc(Object bean, String name) throws IntrospectionException {

        PropertyDescriptor[] propDescArr = propDescArrMap.get(bean.getClass());
        if (Objects.isNull(propDescArr)) {
            propDescArr = putPropDesc(bean);
        }
        if (Objects.isNull(propDescArr)) {
            return null;
        }
        for (PropertyDescriptor propDesc : propDescArr) {
            if (Objects.equals(name, propDesc.getName())) {
                return propDesc;
            }
        }
        return null;
    }

    private PropertyDescriptor[] putPropDesc(Object bean) throws IntrospectionException {

        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propDescArr = beanInfo.getPropertyDescriptors();
        propDescArrMap.put(bean.getClass(), propDescArr);
        return propDescArr;
    }

}
