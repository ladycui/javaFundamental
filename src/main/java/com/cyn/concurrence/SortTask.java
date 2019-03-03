package com.cyn.concurrence;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * this is an example of RecursiveAction from official site
 *
 * @author Cui Yanna on 2019/3/3
 */

public class SortTask extends RecursiveAction {
    // implementation details follow:
    static final int THRESHOLD = 1000;
    final long[] array;
    final int lo, hi;


    SortTask(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    SortTask(long[] array) {
        this(array, 0, array.length);
    }

    /**
     * A "main" ForkJoinTask begins execution when it is explicitly submitted to a ForkJoinPool,
     * or, if not already engaged in a ForkJoin computation, commenced in the ForkJoinPool.commonPool() via fork(), invoke(), or related methods.
     */
    protected void compute() {
        if (hi - lo < THRESHOLD)
            sortSequentially(lo, hi);
        else {
            int mid = (lo + hi) >>> 1;
            invokeAll(new SortTask(array, lo, mid),
                    new SortTask(array, mid, hi));
            merge(lo, mid, hi);
        }
    }

    void sortSequentially(int lo, int hi) {
        Arrays.sort(array, lo, hi);
    }

    /**
     * note here again
     * merge
     */
    void merge(int lo, int mid, int hi) {
        long[] buf = Arrays.copyOfRange(array, lo, mid);
        for (int i = 0, j = lo, k = mid; i < buf.length; j++)
            array[j] = (k == hi || buf[i] < array[k]) ?
                    buf[i++] : array[k++];
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        long[] array = {14,1,5,2,6,3,7};
        pool.submit(new SortTask(array));
        pool.awaitTermination(2, TimeUnit.SECONDS);
        pool.shutdown();
        for (long i : array) {
            System.out.println(i);
        }
    }
}
