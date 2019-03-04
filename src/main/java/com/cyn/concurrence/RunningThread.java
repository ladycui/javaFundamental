package com.cyn.concurrence;

/**
 * @author Cui Yanna on 2019/3/4
 */

public class RunningThread extends Thread {
    /**
     * The Java volatile keyword is used to mark a Java variable as "being stored in main memory".
     * More precisely that means, that every read of a volatile variable will be read from the computer's main memory,
     *      and not from the CPU cache, and that every write to a volatile variable will be written to main memory, and not just to the CPU cache
     */
    private boolean isRunning = true;//add "volatile" or not

    private void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void run() {
        System.out.println("here is run()");
        while (isRunning == true) {
        }
        System.out.println("this thread stopped");
    }

    public static void main(String[] args) throws Exception {
        RunningThread thread = new RunningThread();
        thread.start();
        Thread.sleep(3000);
        thread.setRunning(false);
        System.out.println("isRunning is:" + thread.isRunning);
        Thread.sleep(1000);
        System.out.println(thread.isRunning);
    }

}
