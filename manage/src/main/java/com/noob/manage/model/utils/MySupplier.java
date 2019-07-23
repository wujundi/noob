package com.noob.manage.model.utils;

/**
 * MySupplier
 *
 * @author Gao Shen
 * @version 16/2/27
 */
@FunctionalInterface
public interface MySupplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;
}

// 2019-04-29 23:10 为了配合实现 lambda 表达式而专门设立的类
// 2019-07-22 19:51 就理解成是一个“塑料袋”，用来装什么都行，
// 这里指定了 FunctionalInterface，然后 ResultBundleBuilder 通篇都只接收 MySupplier
// 最终也就促成了 ResultBundleBuilder 像一个包装纸一样，可以包装任何【通过lambda表达式调用而得到的集合结果】