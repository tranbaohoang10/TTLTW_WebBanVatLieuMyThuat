package vn.edu.nlu.fit.mythuatshop.Model;

import java.security.Timestamp;

public class Log {
    private int id;
    private String label;
    private Timestamp time;
    private String location;
    private String beforeData;
    private String afterData;

    public Log() {
    }

    public Log(int id, String label, Timestamp time, String location, String beforeData, String afterData) {
        this.id = id;
        this.label = label;
        this.time = time;
        this.location = location;
        this.beforeData = beforeData;
        this.afterData = afterData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBeforeData() {
        return beforeData;
    }

    public void setBeforeData(String beforeData) {
        this.beforeData = beforeData;
    }

    public String getAfterData() {
        return afterData;
    }

    public void setAfterData(String afterData) {
        this.afterData = afterData;
    }
}
