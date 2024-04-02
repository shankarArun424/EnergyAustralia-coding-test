package models;

import java.util.List;

public class RecordLabelCompany {
    private String label;
    private List<Band> bands;
    public RecordLabelCompany(){}
    public RecordLabelCompany(String label, List<Band> bands){
        this.label = label;
        this.bands = bands;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public List<Band> getBands() {
        return bands;
    }
    public void setBands(List<Band> bands) {
        this.bands = bands;
    }
}