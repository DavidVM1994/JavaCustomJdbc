package com.davidev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataLineList implements Serializable {
    private List<DataLine> dataLines = new ArrayList<>();
    public List<DataLine> getDataLines() { return dataLines; }
    public void setDataLines(List<DataLine> dataLines) { this.dataLines = dataLines; }
}
