package com.mx.study.proxy;

/**
 * @author mengxu
 * @date 2020/5/30 17:18
 */
public class GamePlayer implements Player {
    @Override
    public String play(String str) {
        System.out.println(str);
        return str;
    }
}
