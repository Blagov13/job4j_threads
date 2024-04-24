package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = new Sums();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sums[i].setRowSum(sums[i].getRowSum() + matrix[i][j]);
                sums[j].setColSum(sums[j].getColSum() + matrix[i][j]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        int n = matrix.length;
        CompletableFuture<Sums>[] completableFutures = new CompletableFuture[n];
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            completableFutures[i] = completableSum(matrix, i);
        }
        CompletableFuture.allOf(completableFutures).join();
        for (int i = 0; i < n; i++) {
            try {
                sums[i] = completableFutures[i].get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sums;
    }

    public static CompletableFuture<Sums> completableSum(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                sums.setRowSum(sums.getRowSum() + matrix[i][j]);
                sums.setColSum(sums.getColSum() + matrix[j][i]);
            }
            return sums;
        });
    }
}
