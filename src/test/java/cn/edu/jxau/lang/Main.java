package cn.edu.jxau.lang;

import org.apache.commons.beanutils.LazyDynaMap;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

        Map<String,String> map = new HashMap<>();
        map.put("a","1");
        Collection<String> values = map.values();
        map.put("b","2");
        System.out.println(values);
    }
}
