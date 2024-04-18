package ru.job4j.consumer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class SimpleBlockingQueueTest {
    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        List<Integer> buffer = new LinkedList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread consumer = new Thread(() -> {
            while (buffer.size() < 5) {
                try {
                    buffer.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(Arrays.asList(0, 1, 2, 3, 4), buffer);
    }

    @Test
    public void whenFetchAllThenGetItString() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(10);
        List<String> buffer = new LinkedList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    queue.offer("String " + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread consumer = new Thread(() -> {
            while (buffer.size() < 3) {
                try {
                    buffer.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(Arrays.asList("String 0", "String 1", "String 2"), buffer);
    }
}