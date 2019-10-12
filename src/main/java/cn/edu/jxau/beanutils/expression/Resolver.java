package cn.edu.jxau.beanutils.expression;

/**
 * Desc: 属性表达式解析器
 * ------------------------------------
 * Author:fulei04@meituan.com
 * Date:2019/9/2
 * Time:下午8:23
 */
public interface Resolver {

    int getIndex(String expr);

    String getKey(String expr);

    /**
     * 获取表达式最近的属性（property）名
     * @param expr
     * @return
     */
    String getProperty(String expr);

    /**
     * 是否有内嵌的（nexted）属性
     * @param expr
     * @return
     */
    boolean hasNested(String expr);

    boolean isIndexed(String expr);

    boolean isMapped(String expr);

    /**
     * 获取表达式最近的属性（property）
     *
     * @param expr
     * @return
     */
    String next(String expr);

    /**
     * 删除表达式最近的属性（property）
     *
     * @param expr
     * @return
     */
    String remove(String expr);
}
