package com.github.dexecutor.core.task;

import java.util.function.Supplier;

/**
 * 使得抽象类Task 可以 进行lamba表达式表述
 * @Author: fansy
 * @Time: 2019/4/25 9:54
 * @Email: fansy1990@foxmail.com
 */
public class TaskLamba<T,R> extends Task<T,R> {
    private final Supplier<? extends R> supplier;
    public TaskLamba(Supplier<? extends R> supplier)
    {
        this.supplier = supplier;
    }
    @Override
    public R execute()
    {
        return this.supplier.get();
    }
}
