package com.dialogue.multithreading;

public class ServiceResult {

    private String result;
    private OutputDTO dto;
    public ServiceResult() {
        result = "";
        dto = null;
    }
    public ServiceResult(String result, OutputDTO dto) {
        super();
        this.result = result;
        this.dto = dto;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public OutputDTO getDto() {
        return dto;
    }
    public void setDto(OutputDTO dto) {
        this.dto = dto;
    }

}
