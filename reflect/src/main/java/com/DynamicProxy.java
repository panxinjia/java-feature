package com;

import java.io.Closeable;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态创建某个接口的实例
 * @author xiaopan
 */
public class DynamicProxy {

    public static void main(String[] args) {
        // 动态创建一个接口的实例
        Hello instance = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                (proxy, method, args1) -> null
        );
        System.out.println(instance);
    }
}


interface Hello extends Closeable {


}
