package com.bradrydzewski.tinyreport.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author Brad Rydzewski
 */
public class Column {

    private String name;
    private int type;
    private int order;

    private Column() {

    }

    public Column(String name, int order, int type) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getType() {
        return type;
    }

    public static Column fromResultSetMetaData(
            ResultSetMetaData metaData, int index)
            throws SQLException {

        Column col = new Column();
        col.name = metaData.getColumnName(index);
        col.type = metaData.getColumnType(index);
        col.order = index;
        return col;
    }
}
