package com.example.energyaustraliacodingtest;

import models.Festival;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FestivalTest {

    @Test
    public void testGetName() {
        Festival festival = new Festival();
        festival.setName("Test Festival");
        assertEquals("Test Festival", festival.getName());
    }

    @Test
    public void testSetName() {
        Festival festival = new Festival();
        festival.setName("Test Festival");
        assertEquals("Test Festival", festival.getName());

        festival.setName("Updated Festival");
        assertEquals("Updated Festival", festival.getName());
    }
}
