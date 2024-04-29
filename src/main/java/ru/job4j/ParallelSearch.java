package ru.job4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T target;
    private final int start;
    private final int end;

    public ParallelSearch(T[] array, T target, int start, int end) {
        this.array = array;
        this.target = target;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int length = end - start;
        if (length <= 10) {
            return search();
        }
        int mid = start + (length / 2);
        ParallelSearch<T> leftTask = new ParallelSearch<>(array, target, start, mid);
        ParallelSearch<T> rightTask = new ParallelSearch<>(array, target, mid, end);
        leftTask.fork();
        rightTask.fork();
        int rightResult = rightTask.join();
        int leftResult = leftTask.join();
        return Math.max(leftResult, rightResult);
    }

    public static <T> int indexOf(T[] array, T target) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Wrong");
        }
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearch<>(array, target, 0, array.length));
    }

    private int search() {
        for (int i = start; i < end; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }
}
