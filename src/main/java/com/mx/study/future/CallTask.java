package com.mx.study.future;

import java.util.concurrent.Callable;

/**
 * @author mengxu
 * @date 2020/6/22 19:50
 */
public class CallTask implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {

        System.out.println("start");
        Thread.sleep(5000);
        System.out.println("after");

        return 5000;
    }
}
