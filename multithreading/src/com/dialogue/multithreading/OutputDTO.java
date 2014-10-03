package com.dialogue.multithreading;

public class OutputDTO {
    // simple output record
    private String data;
    public OutputDTO() {
        super();
        data = "";
    }
    public OutputDTO(String data) {
        super();
        this.data = data;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

}
