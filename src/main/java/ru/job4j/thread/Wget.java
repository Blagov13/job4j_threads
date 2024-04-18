package ru.job4j.thread;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String locationFile;

    public Wget(String url, int speed, String locationFile) {
        this.url = url;
        this.speed = speed;
        this.locationFile = locationFile;
    }

    @Override
    public void run() {
        try (
                BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());
                FileOutputStream outputStream = new FileOutputStream(locationFile)
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long timeBefore = System.currentTimeMillis();
            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long time = System.currentTimeMillis() - timeBefore;
                    if (time < 1000) {
                        Thread.sleep(1000 - time);
                    }
                    timeBefore = System.currentTimeMillis();
                    downloadData = 0;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException();
        }
        try {
            String url = args[0];
            int speed = Integer.parseInt(args[1]);
            String fileLocation = args[2];
            Thread wget = new Thread(new Wget(url, speed, fileLocation));
            wget.start();
            wget.join();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
