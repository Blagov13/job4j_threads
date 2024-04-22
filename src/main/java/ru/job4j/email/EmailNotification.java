package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification() {
        this.pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = "Notification " + user.getUsername()
                    + " to email " + user.getEmail();
            String body = "Add a new event to " + user.getUsername();
            send(subject, body, user.getEmail());
        });
    }

    public void send(String subject, String body, String email) {
        System.out.println(" ");
    }

    public void close() {
        pool.shutdown();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
