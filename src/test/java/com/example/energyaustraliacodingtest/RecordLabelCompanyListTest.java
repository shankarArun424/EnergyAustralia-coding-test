package com.example.energyaustraliacodingtest;

import models.RecordLabelCompany;
import models.RecordLabelCompanyList;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecordLabelCompanyListTest {

    @Test
    public void testGetRecordLabelList() {
        RecordLabelCompanyList recordLabelCompanyList = new RecordLabelCompanyList();
        assertNotNull(recordLabelCompanyList.getRecordLabelList());
        assertEquals(0, recordLabelCompanyList.getRecordLabelList().size());
    }

    @Test
    public void testSetRecordLabelList() {
        RecordLabelCompanyList recordLabelCompanyList = new RecordLabelCompanyList();

        List<RecordLabelCompany> companies = new ArrayList<>();
        companies.add(new RecordLabelCompany("Label 1", new ArrayList<>()));
        companies.add(new RecordLabelCompany("Label 2", new ArrayList<>()));
        recordLabelCompanyList.setRecordLabelCompanyList(companies);

        assertNotNull(recordLabelCompanyList.getRecordLabelList());
        assertEquals(2, recordLabelCompanyList.getRecordLabelList().size());
        assertEquals("Label 1", recordLabelCompanyList.getRecordLabelList().get(0).getLabel());
        assertEquals("Label 2", recordLabelCompanyList.getRecordLabelList().get(1).getLabel());
    }
}
