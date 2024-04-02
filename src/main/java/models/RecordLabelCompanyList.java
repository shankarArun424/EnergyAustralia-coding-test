package models;

import java.util.ArrayList;
import java.util.List;

public class RecordLabelCompanyList {
    private List<RecordLabelCompany> recordLableList;
    public RecordLabelCompanyList() {
        recordLableList = new ArrayList<>();
    }

    public List<RecordLabelCompany> getRecordLabelList() {
        return recordLableList;
    }
    public void setRecordLabelCompanyList(List<RecordLabelCompany> recordLableList) {
        this.recordLableList = recordLableList;
    }
}
