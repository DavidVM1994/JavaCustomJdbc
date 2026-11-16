package com.davidev;

import java.io.Serializable;
import java.util.Objects;

public class DataLine implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer silLineNumber;
    private String silLineContent;
    private Integer silProcess;


    public DataLine(Integer silLineNumber, String silLineContent, Integer silProcess) {
        this.silLineNumber = silLineNumber;
        this.silLineContent = silLineContent;
        this.silProcess = silProcess;
    }

    public Integer getSilLineNumber() {
        return silLineNumber;
    }

    public void setSilLineNumber(Integer silLineNumber) {
        this.silLineNumber = silLineNumber;
    }

    public String getSilLineContent() {
        return silLineContent;
    }

    public void setSilLineContent(String silLineContent) {
        this.silLineContent = silLineContent;
    }

    public Integer getSilProcess() {
        return silProcess;
    }

    public void setSilProcess(Integer silProcess) {
        this.silProcess = silProcess;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass())
            return false;
        DataLine dataLine = (DataLine) obj;
        return Objects.equals(dataLine.silLineContent, this.silLineContent)
                && Objects.equals(dataLine.silLineNumber, this.silLineNumber)
                && Objects.equals(dataLine.silProcess, this.silProcess);
    }

    @Override
    public String toString() {
        return "DataLine{" +
                "silLineNumber=" + silLineNumber +
                ", silLineContent='" + silLineContent + '\'' +
                ", silProcess='" + silProcess + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(silLineNumber, silLineContent, silProcess);
    }
}