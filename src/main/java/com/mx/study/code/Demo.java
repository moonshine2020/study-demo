package com.mx.study.code;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mengxu
 * @date 2020/6/30 10:39
 */
public class Demo {

    // 公平锁
    private static Lock fairLock = new ReentrantLock(true);

    // 非公平锁
    private static Lock nonFairLock = new ReentrantLock(false);

    // 计数器
    private static int fairCount = 0;

    // 计数器
    private static int nonFairCount = 0;

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Character> charQueue = new SynchronousQueue<>();
        SynchronousQueue<Integer> integerQueue = new SynchronousQueue<>();

        TransferQueue<Character> charTransferQueue = new LinkedTransferQueue<>();
        TransferQueue<Integer> integerTransferQueue = new LinkedTransferQueue<>();

        new Thread(()->{
            try {
                for(int i = 1; i <= 26; i++){
                    System.out.print(charQueue.take());
                    integerQueue.put(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                for(char i = 'A'; i <= 'Z'; i++ ){
                    charQueue.put(i);
                    System.out.print(integerQueue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println("---------------------");

        new Thread(()->{
            try {
                for(int i = 1; i <= 26; i++){
                    System.out.print(charTransferQueue.take());
                    integerTransferQueue.transfer(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(()->{
            try {
                for(char i = 'A'; i <= 'Z'; i++ ){
                    charTransferQueue.transfer(i);
                    System.out.print(integerTransferQueue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,60L,
                TimeUnit.SECONDS,new LinkedBlockingQueue<>(),new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    public static long testFairLock(int threadNum) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        // 创建threadNum个线程，让其以公平锁的方式，对fairCount进行自增操作
        List<Thread> fairList = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            fairList.add(new Thread(() -> {
                    fairLock.lock();
                    fairCount++;
                    fairLock.unlock();
                countDownLatch.countDown();
            }));
        }

        long startTime = System.currentTimeMillis();
        for (Thread thread : fairList) {
            thread.start();
        }
        // 让所有线程执行完
        countDownLatch.await();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static long testNonFairLock(int threadNum) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        // 创建threadNum个线程，让其以非公平锁的方式，对nonFairCountCount进行自增操作
        List<Thread> nonFairList = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            nonFairList.add(new Thread(() -> {
                    nonFairLock.lock();
                    nonFairCount++;
                    nonFairLock.unlock();
                countDownLatch.countDown();
            }));
        }
        long startTime = System.currentTimeMillis();
        for (Thread thread : nonFairList) {
            thread.start();
        }
        // 让所有线程执行完
        countDownLatch.await();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
