package com.dialogue.multithreading;

public class InputDTO {
    // simple input record

    private String data;
    private int rowNbr;
    public InputDTO() {
       super();
       data = "";
       rowNbr = 0;
    }
    public InputDTO(String data, int rowNum) {
        super();
        this.data = data;
        rowNbr = rowNum;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public int getRowNbr() {
        return rowNbr;
    }
    public void setRowNbr(int rowNbr) {
        this.rowNbr = rowNbr;
    }

}
