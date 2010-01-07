package com.bradrydzewski.tinyreport.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Bradley N. Rydzewski
 */
public class PreparedStatementCreatorImpl implements PreparedStatementCreator {

    private List<PreparedStatementParameter> params;
    private String query;
    
    public PreparedStatementCreatorImpl(String query){
        this.query = query;
        this.params = new ArrayList<PreparedStatementParameter>();
    }
    
    public void addParameter(int parameterIndex, Object parameterValue, int sqlType){
        this.params.add( new PreparedStatementParameter(
                    parameterIndex,parameterValue,sqlType
                ));
    }
    
    public PreparedStatement createPreparedStatement(Connection conn) throws DataAccessException, SQLException {
        
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        //need to add each parameter to the prepared statement
        for(PreparedStatementParameter param : params){
            pstmt.setObject(param.getPosition(), param.getValue(), param.getSqlType());
        }
        
        return pstmt;
    }
}
