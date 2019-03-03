package com.cyn.concurrence;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

import static com.cyn.concurrence.SortTask.THRESHOLD;

/**
 * this is an easier example for RecursiveAction
 * @author Cui Yanna on 2019/3/4
 */

public class IncrementTask extends RecursiveAction {
    final long[] array;
    final int lo, hi;

    IncrementTask(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    IncrementTask(long[] array){
        this(array, 0, array.length);
    }

    protected void compute() {
        if (hi - lo < THRESHOLD) {
            for (int i = lo; i < hi; ++i)
                array[i]++;
        } else {
            int mid = (lo + hi) >>> 1;
            invokeAll(new IncrementTask(array, lo, mid),
                    new IncrementTask(array, mid, hi));
        }
    }

    public static void main(String[] args) {
//        ForkJoinPool pool = new ForkJoinPool();//both are ok
        ForkJoinPool pool = ForkJoinPool.commonPool();
        long[] array = {5, 4, 6, 3, 7, 2, 8};
        pool.submit(new IncrementTask(array));
        try {
            pool.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        for (long i : array) {
            System.out.println(i);
        }
    }
}
