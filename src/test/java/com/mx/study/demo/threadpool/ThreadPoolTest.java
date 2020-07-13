package com.mx.study.demo.threadpool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mengxu
 * @date 2020/7/13 9:13
 */
public class ThreadPoolTest {


    public static void main(String[] args) throws Exception {

        Runnable runnable = ()->{
            try {
                TimeUnit.SECONDS.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnable1 = () -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,10,TimeUnit.SECONDS,new ArrayBlockingQueue<>(2));
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable);
        threadPoolExecutor.execute(runnable1);
        threadPoolExecutor.execute(runnable1);
        threadPoolExecutor.execute(runnable1);
        threadPoolExecutor.execute(runnable1);


        Runnable moniter = () -> {
            while (true){
                System.out.println("--------------------------------");
                System.out.println("poolSize: " + threadPoolExecutor.getPoolSize());
                System.out.println("block queue size: " + threadPoolExecutor.getQueue().size());
                System.out.println("task count: " + threadPoolExecutor.getTaskCount());
                System.out.println("active count: " + threadPoolExecutor.getActiveCount());

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(moniter).start();

    }
}
