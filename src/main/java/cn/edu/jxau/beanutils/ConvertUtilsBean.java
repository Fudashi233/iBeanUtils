package cn.edu.jxau.beanutils;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2018/8/12
 * Time:下午5:04
 */
public class ConvertUtilsBean {


    public final static ConvertUtilsBean INSTANCE = new ConvertUtilsBean();
    private WeakFastHashMap<Class<?>, Converter> converterMap = new WeakFastHashMap<>();


    /**
     * 注册converter
     *
     * @param clazz
     * @param converter
     */
    public void register(Class<?> clazz, Converter converter) {
        converterMap.put(clazz, converter);
    }
}
