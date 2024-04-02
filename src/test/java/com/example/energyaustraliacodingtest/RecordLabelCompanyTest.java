package com.example.energyaustraliacodingtest;

import models.RecordLabelCompany;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordLabelCompanyTest {

    @Test
    public void testGetLabel() {
        RecordLabelCompany company = new RecordLabelCompany("Test Label", new ArrayList<>());
        assertEquals("Test Label", company.getLabel());
    }

    @Test
    public void testSetLabel() {
        RecordLabelCompany company = new RecordLabelCompany();
        company.setLabel("New Label");
        assertEquals("New Label", company.getLabel());
    }

}
