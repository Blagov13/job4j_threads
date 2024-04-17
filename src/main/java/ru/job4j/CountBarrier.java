package ru.job4j;

public class CountBarrier {
    private boolean flag = false;
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            while (count >= total) {
                flag = true;
                monitor.notifyAll();
            }
        }
    }

    public void await() throws InterruptedException {
        synchronized (monitor) {
            while (!flag) {
                monitor.wait();
            }
        }
    }
}
