package com.davidev;

import java.io.Serializable;
import java.util.Objects;

public class InsertResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rowsInserted;
    private long elapsedMillis;

    public InsertResult() {
    }

    public InsertResult(int rowsInserted, long elapsedMillis) {
        this.rowsInserted = rowsInserted;
        this.elapsedMillis = elapsedMillis;
    }

    public int getRowsInserted() {
        return rowsInserted;
    }

    public long getElapsedMillis() {
        return elapsedMillis;
    }

    public void setRowsInserted(int rowsInserted) {
        this.rowsInserted = rowsInserted;
    }

    public void setElapsedMillis(long elapsedMillis) {
        this.elapsedMillis = elapsedMillis;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        InsertResult insertResult = (InsertResult) obj;
        return insertResult.rowsInserted==this.rowsInserted && insertResult.elapsedMillis==this.elapsedMillis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.rowsInserted, this.elapsedMillis);
    }

    @Override
    public String toString() {
        return "InsertResult{" +
                "rowsInserted=" + rowsInserted +
                ", elapsedMillis=" + elapsedMillis +
                '}';
    }
}
