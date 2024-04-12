package ru.job4j.concurrent;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> { }
        );
        System.out.println(another.getName());
        another.start();
        Thread second = new Thread(
                () -> { }
        );
        System.out.println(second.getName());
        second.start();
        while (another.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("thread 1: " + another.getState());
            System.out.println("thread 2: " + second.getState());
        }
        System.out.println("work TERMINATED");
    }
}
