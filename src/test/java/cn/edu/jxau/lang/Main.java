package cn.edu.jxau.lang;

import org.apache.commons.beanutils.*;
import org.apache.commons.beanutils.expression.DefaultResolver;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/6/4
 * Time:下午7:27
 */
public class Main {

    @Test
    public void main() throws IntrospectionException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Student student = new Student();
        student.setIntegerArr(new Integer[]{1, 2, 3});
        System.out.println(BeanUtils.getProperty(student, "integerArr[0]"));
    }

    @Test
    public void test01() throws InvocationTargetException, IllegalAccessException {

        Student student = new Student();
        BeanUtils.setProperty(student, "id", 10011);
        System.out.println(student);
    }

    @Test
    public void test02() {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread t = new Thread();
        System.out.println(classLoader);
        System.out.println(t.getContextClassLoader());
    }

    @Test
    public void test03() {

        DefaultResolver defaultResolver = new DefaultResolver();
        String expr = "class(key).name";
        System.out.println(defaultResolver.getProperty(expr));
    }

    @Test
    public void test04() {

        DynaBean dynaBean = new LazyDynaBean();
        dynaBean.set("carNumber", "赣A-203902");
        dynaBean.set("brand", null);
        dynaBean.set("arr[0]", new int[]{123});
        System.out.println(dynaBean.get("carNumber"));
        System.out.println(dynaBean.get("brand"));
        System.out.println(dynaBean.get("arr[0]"));
    }

    @Test
    public void test05() throws InstantiationException, IllegalAccessException {

        DynaProperty[] dynaPropArr = new DynaProperty[]{
                new DynaProperty("address", String.class),
                new DynaProperty("firstName", String.class),
                new DynaProperty("lastName", String.class)
        };
        BasicDynaClass dynaClass = new BasicDynaClass("employee", null, dynaPropArr);

        DynaBean bean = dynaClass.newInstance();
        bean.set("address", "江西省南昌市");
        bean.set("firstName", "磊");
        bean.set("lastName", "付");

        System.out.println(bean.get("address"));
        System.out.println(bean.get("lastName"));
        System.out.println(bean.get("firstName"));
    }
}
