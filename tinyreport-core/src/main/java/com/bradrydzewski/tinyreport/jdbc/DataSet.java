package com.bradrydzewski.tinyreport.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Brad Rydzewski
 */
public class DataSet {

//    private int[] columnTypes;
//    private String[] columnNames;
    private List<Column> columns = new ArrayList<Column>();
    private List<Object[]> rows = new ArrayList<Object[]>();
    private Map<String,Integer> columnMap = new HashMap<String,Integer>();

    public DataSet(List<Column> columns, List<Object[]> rows) {
        assert(columns!=null && columns.size()>0);
        this.columns = columns;
        this.rows = rows;
        this.createColumnMap();
    }

//
//    public String[] getColumnNames() {
//        return columnNames;
//    }
//
//    public void setColumnNames(String[] columnNames) {
//        this.columnNames = columnNames;
//    }
//
//    public int[] getColumnTypes() {
//        return columnTypes;
//    }
//
//    public void setColumnTypes(int[] columnTypes) {
//        this.columnTypes = columnTypes;
//    }

    public final List<Object[]> getRows() {
        return rows;
    }

    public final List<Column> getColumns() {
        return columns;
    }

    public final int getColumnIndex(String name) {
        return columnMap.get(name);
    }

    private void createColumnMap() {
        columnMap.clear();
        for(Column c : columns) {
            columnMap.put(c.getName(), c.getOrder());
        }
    }
}
