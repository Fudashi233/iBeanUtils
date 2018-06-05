package cn.edu.jxau.lang;

import org.apache.commons.beanutils.LazyDynaMap;
import org.junit.Test;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/6/4
 * Time:下午7:27
 */
public class Main {

    @Test
    public void test01() {

        LazyDynaMap dynaBean1 = new LazyDynaMap();
        dynaBean1.set("address", 0, "address1");
        dynaBean1.set("address", 1, "address2");
        System.out.println(dynaBean1.get("address").getClass());
    }
}
