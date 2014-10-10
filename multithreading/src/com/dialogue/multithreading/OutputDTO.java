package com.dialogue.multithreading;

public class OutputDTO {
    // simple output record
    private String data;
    private int rowNbr;
    public OutputDTO() {
        super();
        data = "";
        rowNbr = 0;
    }
    public OutputDTO(String data) {
        super();
        this.data = data;
        rowNbr = 0;
    }
    public OutputDTO(String data, int rowNbr) {
		super();
		this.data = data;
		this.rowNbr = rowNbr;
	}
    public OutputDTO(InputDTO dto) {
		super();
		this.data = dto.getData();
		this.rowNbr = dto.getRowNbr();
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
