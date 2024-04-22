package ru.job4j;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CASCountTest {
    @Test
    public void whenIncrementThenCountIsIncreased() {
        CASCount casCount = new CASCount();
        casCount.increment();
        assertEquals(1, casCount.get());
        casCount.increment();
        assertEquals(2, casCount.get());
    }

    @Test
    public void whenGetWithoutIncrementThenZero() {
        CASCount casCount = new CASCount();
        assertEquals(0, casCount.get());
    }
}