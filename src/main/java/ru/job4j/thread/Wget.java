package ru.job4j.thread;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        Path file = Paths.get("output.data");
        try (
                InputStream inputStream = new URL(url).openStream();
                OutputStream outputStream = Files.newOutputStream(file)
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                long elapsedTime = System.nanoTime() - startTime;
                double elapsedSeconds = elapsedTime / 1_000_000_000.0;
                long timeForDownload = bytesRead / speed;
                if (timeForDownload > elapsedSeconds) {
                    Thread.sleep((long) (timeForDownload - elapsedSeconds));
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final long startTime = System.nanoTime();

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
