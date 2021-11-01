package com.inter;

@FunctionalInterface
public interface Fib<E>{

    E get(int n);

    default void bar() {
        // 默认方法
    }

    static void foo() {
        // 静态方法
    }

    private void func() {
        // 方法
    }
}
