package com.bradrydzewski.tinyreport.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brad Rydzewski
 */
public class DataSet implements Cloneable {

    private int[] columnTypes;
    private String[] columnNames;
    private List<Object[]> rows = new ArrayList<Object[]>();

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public int[] getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(int[] columnTypes) {
        this.columnTypes = columnTypes;
    }

    public List<Object[]> getRows() {
        return rows;
    }

    public void setRows(List<Object[]> rows) {
        this.rows = rows;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
  
        return null;
    }
    
}
