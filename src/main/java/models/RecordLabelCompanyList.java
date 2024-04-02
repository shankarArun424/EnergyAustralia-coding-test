package models;

import com.example.energyaustraliacodingtest.EnergyAustraliaCodingTestApplication;

import java.util.ArrayList;
import java.util.List;

public class RecordLabelCompanyList {
    private List<RecordLabelCompany> recordLableList;
    public RecordLabelCompanyList() {
        recordLableList = new ArrayList<>();
    }
    public void setRecordLabelCompanyList(List<RecordLabelCompany> recordLableList) {
        this.recordLableList = recordLableList;
    }
}
