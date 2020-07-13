package com.mx.study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author mengxu
 * @date 2020/5/30 17:16
 */
public class DynamicProxy implements InvocationHandler {

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public DynamicProxy(Player player) {
        this.player = player;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(player, args);
        return null;
    }
}
