package ru.job4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelArraySearch {
    public static <T> int indexOf(T[] array, T target) {
        if (array.length <= 10) {
            return linearSearch(array, target);
        } else {
            ForkJoinPool pool = new ForkJoinPool();
            return pool.invoke(new ParallelSearch<>(array, target, 0, array.length));
        }
    }

    private static <T> int linearSearch(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private static class ParallelSearch<T> extends RecursiveTask<Integer> {
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
                for (int i = start; i < end; i++) {
                    if (array[i].equals(target)) {
                        return i;
                    }
                }
                return -1;
            } else {
                int mid = start + (length / 2);
                ParallelSearch<T> leftTask = new ParallelSearch<>(array, target, start, mid);
                ParallelSearch<T> rightTask = new ParallelSearch<>(array, target, mid, end);
                leftTask.fork();
                int rightResult = rightTask.compute();
                int leftResult = leftTask.join();
                if (leftResult != -1) {
                    return leftResult;
                } else {
                    return rightResult;
                }
            }
        }
    }
}
