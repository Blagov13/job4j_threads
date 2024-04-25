package ru.job4j.pools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RolColSumTest {
    @Test
    public void whenSumMatrix() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] expected = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        Sums[] result = RolColSum.sum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    public void whenAsyncSumMatrix() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] expected = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        Sums[] result = RolColSum.asyncSum(matrix);
        assertArrayEquals(expected, result);
    }
}