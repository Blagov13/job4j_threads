package ru.job4j;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CASCountTest {
    @Test
    public void whenIncrementThenCountIsIncreased() {
        CASCount casCount = new CASCount();
        casCount.increment();
        assertEquals(1, casCount.get());
        casCount.increment();
        assertEquals(2, casCount.get());
    }

    @Test
    public void whenGetWithoutIncrementThenZero() {
        CASCount casCount = new CASCount();
        assertEquals(0, casCount.get());
    }

    @Test
    public void whenConcurrentIncrementsThenCorrectCount() throws InterruptedException {
        final CASCount casCount = new CASCount();
        int numberOfThreads = 10;
        int incrementsPerThread = 100;
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    casCount.increment();
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assertEquals(numberOfThreads * incrementsPerThread, casCount.get());
    }
}