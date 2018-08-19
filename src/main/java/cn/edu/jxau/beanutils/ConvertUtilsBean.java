package cn.edu.jxau.beanutils;

import java.util.Objects;
import java.util.Optional;

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
     * 注册 converter
     *
     * @param clazz
     * @param converter
     */
    public void register(Class<?> clazz, Converter converter) {

        Objects.requireNonNull(clazz, "clazz is null");
        Objects.requireNonNull(converter, "converter is null");
        converterMap.put(clazz, converter);
    }

    /**
     * 移除 converter
     *
     * @param clazz
     */
    public void deregister(Class<?> clazz) {

        Objects.requireNonNull(clazz, "clazz is null");
        converterMap.remove(clazz);
    }

    /**
     * 寻找 converter
     *
     * @param clazz
     * @return
     */
    public Optional<Converter> lookup(Class<?> clazz) {

        Objects.requireNonNull(clazz, "clazz is null");
        return Optional.ofNullable(converterMap.get(clazz));
    }

    public Optional<Converter> lookup(Class<?> sourceClass, Class<?> targetClass) {
        return null;
    }

    public String convert(Object value) {

        return (String) convert(value, String.class);
    }


    public Object convert(Object value, Class clazz) {

        Objects.requireNonNull(clazz, "clazz is null");
        Optional<Converter> converterOpt = lookup(clazz);
        return converterOpt.map(converter -> converter.convert(clazz, value))
                .orElse(null);
    }


    private boolean isArray(Object value) {

        if (Objects.isNull(value)) {
            return false;
        }
        return value.getClass().isArray();
    }
}
