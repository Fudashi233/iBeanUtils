package cn.edu.jxau.beanutils.expression;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultResolverTest {

    @Test
    public void next() {

        DefaultResolver defaultResolver = new DefaultResolver();
        System.out.println(defaultResolver.next("student.name"));
        System.out.println(defaultResolver.next("map(a).name"));
        System.out.println(defaultResolver.next("arr[0].name"));
    }

    @Test
    public void getProperty() {

        DefaultResolver defaultResolver = new DefaultResolver();
        System.out.println(defaultResolver.getProperty("student.name"));
        System.out.println(defaultResolver.getProperty("map(a).name"));
        System.out.println(defaultResolver.getProperty("arr[0].name"));
    }

    @Test
    public void remove() {

        DefaultResolver defaultResolver = new DefaultResolver();
        System.out.println(defaultResolver.remove("student.name"));
        System.out.println(defaultResolver.remove("map(a).name"));
        System.out.println(defaultResolver.remove("arr[0].name"));
    }

    @Test
    public void getKey() {

        DefaultResolver defaultResolver = new DefaultResolver();
        System.out.println(defaultResolver.getKey("map(a).name"));
    }

}