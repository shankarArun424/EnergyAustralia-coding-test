package models;

import java.util.List;

public class Band {
    private String name;
    private List<Festival> festivals;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Festival> getFestivals() {
        return festivals;
    }
    public void setFestivals(List<Festival> festivals) {
        this.festivals = festivals;
    }
}
