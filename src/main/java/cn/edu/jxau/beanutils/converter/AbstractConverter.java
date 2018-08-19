package cn.edu.jxau.beanutils.converter;

import cn.edu.jxau.beanutils.Converter;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/8/19
 * Time:下午5:35
 */
public class AbstractConverter implements Converter{


    @Override
    public <T> T convert(Class<T> type, Object value) {
        return null;
    }


}