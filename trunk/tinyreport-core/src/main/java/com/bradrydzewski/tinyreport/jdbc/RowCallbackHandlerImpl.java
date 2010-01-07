package com.bradrydzewski.tinyreport.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Bradley N. Rydzewski
 */
public class RowCallbackHandlerImpl implements RowCallbackHandler {


    public Object processRow(ResultSet rs) throws SQLException {
        
        int columnCount = rs.getMetaData().getColumnCount();
        Object[] row = new Object[columnCount];
        
        for(int i=1; i<=columnCount; i++){
            row[i-1] = rs.getObject(i);
        }
        
        return row;
    }

}
