package cn.edu.jxau.beanutils.expression;

import java.util.Objects;

/**
 * Desc:
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2019/9/2
 * Time:下午8:27
 */
public class DefaultResolver implements Resolver {

    private static final char NESTED = '.';
    private static final char MAPPED_START = '(';
    private static final char MAPPED_END = ')';
    private static final char INDEXED_START = '[';
    private static final char INDEXED_END = ']';
    private static final String SPLIT_REGEX = "\\.";

    @Override
    public int getIndex(String expr) {

        if (!isIndexed(expr)) {
            throw new RuntimeException("is not indexed expr，expr=" + expr);
        }
        int start = expr.indexOf(INDEXED_START);
        int end = expr.indexOf(INDEXED_END);
        return Integer.parseInt(expr.substring(start + 1, end));
    }

    @Override
    public String getKey(String expr) {

        if (!isMapped(expr)) {
            throw new RuntimeException("is not mapped expr，expr=" + expr);
        }
        int start = expr.indexOf(MAPPED_START);
        int end = expr.indexOf(MAPPED_END);
        return expr.substring(start + 1, end);
    }

    @Override
    public String getProperty(String expr) {

        if (Objects.isNull(expr) || expr.length() == 0) {
            return null;
        }
        for (int i = 0; i < expr.length(); i++) {
            char cur = expr.charAt(i);
            if (cur == NESTED || cur == MAPPED_START || cur == INDEXED_START) {
                return expr.substring(0, i);
            }
        }
        return expr;
    }

    @Override
    public boolean hasNested(String expr) {

        if (Objects.isNull(expr) || expr.length() == 0) {
            return false;
        }
        return remove(expr) != null;
    }

    @Override
    public boolean isIndexed(String expr) {

        if (Objects.isNull(expr) || expr.length() == 0) {
            return false;
        }
        return expr.indexOf(INDEXED_START) > 0 && expr.indexOf(INDEXED_END) > 0;
    }

    @Override
    public boolean isMapped(String expr) {

        if (Objects.isNull(expr) || expr.length() == 0) {
            return false;
        }
        return expr.indexOf(MAPPED_START) > 0 && expr.indexOf(MAPPED_END) > 0;
    }

    @Override
    public String next(String expr) {

        if (Objects.isNull(expr) || expr.length() == 0) {
            return null;
        }
        String[] arr = expr.split(SPLIT_REGEX);
        return arr[0];
    }

    @Override
    public String remove(String expr) {

        if (Objects.isNull(expr) || expr.length() == 0) {
            return null;
        }
        String property = next(expr);
        if (Objects.equals(property, expr)) { //所删除的property就是当前表达式指定的property
            return null;
        }
        int start = property.length();
        return expr.substring(start + 1);
    }
}
