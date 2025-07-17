package com.prueba.demo.enums;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OccupationTest {

    @Test
    void shouldContainAllExpectedValues() {
        Occupation[] values = Occupation.values();

        assertEquals(3, values.length);
        assertTrue(List.of(values).contains(Occupation.EMPLOYEE));
        assertTrue(List.of(values).contains(Occupation.INDEPENDENT));
        assertTrue(List.of(values).contains(Occupation.PENSIONER));
    }

    @Test
    void shouldReturnCorrectEnumFromName() {
        assertEquals(Occupation.EMPLOYEE, Occupation.valueOf("EMPLOYEE"));
        assertEquals(Occupation.INDEPENDENT, Occupation.valueOf("INDEPENDENT"));
        assertEquals(Occupation.PENSIONER, Occupation.valueOf("PENSIONER"));
    }

    @Test
    void shouldThrowExceptionForInvalidEnumName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Occupation.valueOf("STUDENT");
        });
    }
}
