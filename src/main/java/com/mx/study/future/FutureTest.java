package com.mx.study.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author mengxu
 * @date 2020/6/22 19:49
 */
public class FutureTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService1 = Executors.newWorkStealingPool();
        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("hello world");
        System.out.println(future.get());
        System.out.println("------------------------");
        System.out.println("start----------");
    }
}
