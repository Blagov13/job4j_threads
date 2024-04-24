package ru.job4j;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ParallelArraySearchTest {
    @Test
    public void differentDataTypes() {
        String[] stringArray = {"apple", "banana", "auto", "big", "car"};
        int index = ParallelArraySearch.indexOf(stringArray, "auto");
        assertEquals(2, index);

        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4, 5.5};
        index = ParallelArraySearch.indexOf(doubleArray, 3.3);
        assertEquals(2, index);
    }

    @Test
    public void smallArrayLinearSearch() {
        Integer[] smallArray = {1, 2, 3, 4, 5};
        int index = ParallelArraySearch.indexOf(smallArray, 3);
        assertEquals(2, index);
    }

    @Test
    public void largeArrayParallelSearch() {
        Integer[] largeArray = new Integer[100];
        for (int i = 0; i < 100; i++) {
            largeArray[i] = i + 1;
        }
        int index = ParallelArraySearch.indexOf(largeArray, 75);
        assertEquals(74, index);
    }

    @Test
    public void elementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int index = ParallelArraySearch.indexOf(array, 11);
        assertEquals(-1, index);
    }
}