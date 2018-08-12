package cn.edu.jxau.beanutils;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/8/12
 * Time:下午5:39
 */
public interface Converter {

    /**
     * 将 value 转换成 type 类型
     *
     * @param type
     * @param value
     * @param <T>
     * @return
     */
    public <T> T convert(Class<T> type, Object value);
}
