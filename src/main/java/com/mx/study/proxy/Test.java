package com.mx.study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author mengxu
 * @date 2020/5/30 17:22
 */
public class Test {

    public static void main(String[] args) {
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Player player = new GamePlayer();
        InvocationHandler dynamicProxy = new DynamicProxy(player);
        Player proxy = (Player) Proxy.newProxyInstance(player.getClass().getClassLoader(), player.getClass().getInterfaces(), dynamicProxy);
        proxy.play("hello,world!");
    }
}
