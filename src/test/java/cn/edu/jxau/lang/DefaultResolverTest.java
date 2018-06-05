package cn.edu.jxau.lang;

import org.apache.commons.beanutils.expression.DefaultResolver;
import org.junit.Test;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/6/4
 * Time:下午6:19
 */
public class DefaultResolverTest {

    @Test
    public void test01() {

        DefaultResolver defaultResolver = new DefaultResolver();
        System.out.println(defaultResolver.next("address.telephone[1]"));
    }
}
