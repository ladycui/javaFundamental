package com.cyn.concurrence;

import java.util.concurrent.Future;

/**
 * ${Description}
 *
 * @author Cui Yanna on 2019/3/3
 */

public class MyThread extends Thread {
    private int num = 5;

    public synchronized void run() {
        num--;
        System.out.println(getName() + " num = " + num);
        System.out.println(this.currentThread().getName() + " num = " + num);//通过Runnable接口获取当前线程对象，必须使用Thread.currentThread() or this.currentThread()
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread t1 = new Thread(myThread, "t1");//Thread implements Runnable
        Thread t2 = new Thread(myThread, "t2");
        Thread t3 = new Thread(myThread, "t3");
        Thread t4 = new Thread(myThread, "t4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();

//        Future

    }


}
