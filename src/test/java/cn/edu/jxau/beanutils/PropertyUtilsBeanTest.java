package cn.edu.jxau.beanutils;

import cn.edu.jxau.lang.Student;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PropertyUtilsBeanTest {

    @Test
    public void getProperty() throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        Student student = new Student();
        student.setAge(11);
        student.setIntegerArr(new Integer[]{1, 2, 3, 4});
        HashMap<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        student.setIntegerMap(map);
        System.out.println(propertyUtilsBean.getProperty(student, "age"));
        System.out.println(propertyUtilsBean.getProperty(student, "integerArr[0]"));
        System.out.println(propertyUtilsBean.getProperty(student, "integerArr[3]"));
        System.out.println(propertyUtilsBean.getProperty(student, "integerMap"));
        System.out.println(propertyUtilsBean.getProperty(student, "integerMap(D)"));
        System.out.println(propertyUtilsBean.getProperty(map, "A"));
    }

    @Test
    public void setProperty() throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        Student student = new Student();
        propertyUtilsBean.setProperty(student, "age", 17);
        propertyUtilsBean.setProperty(student, "integerArr", new Integer[]{1, 2, 3, 4});
        propertyUtilsBean.setProperty(student, "integerArr[1]", 5);
        propertyUtilsBean.setProperty(student, "integerMap", new HashMap<>());
        propertyUtilsBean.setProperty(student, "integerMap(A)", 2);
        System.out.println(student);

        HashMap<String, Integer> map = new HashMap<>();
        propertyUtilsBean.setProperty(map, "map", 123);
        System.out.println(map);
    }
}