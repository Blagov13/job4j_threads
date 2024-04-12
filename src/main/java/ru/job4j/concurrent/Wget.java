package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread loading = new Thread(
                () -> {
                    try {
                        for (int i = 1; i <= 100; i++) {
                            Thread.sleep(1000);
                            System.out.print("\rLoading : " + i  + "%");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        loading.start();
    }
}
