package com.bradrydzewski.tinyreport.model;

import com.bradrydzewski.tinyreport.jdbc.DataSet;
import com.bradrydzewski.tinyreport.jdbc.DataSourceFactory;
import com.bradrydzewski.tinyreport.jdbc.JdbcTemplate;
import com.bradrydzewski.tinyreport.jdbc.PreparedStatementCreatorImpl;
import com.bradrydzewski.tinyreport.jdbc.RowCallbackHandlerImpl;
import com.bradrydzewski.tinyreport.util.DataTypeConversionUtil;
import java.util.Map;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brad Rydzewski
 */
@XmlRootElement
public class JdbcQuery extends DataQuery {

    
    private String sqlQuery;


    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    @Override
    public DataSet execute(DataConnection dataConnection, Map<String, Parameter> params) {

            //gets the jdbc connection stored in the ReportDefinition
            JdbcConnection conn = (JdbcConnection)dataConnection;

            //gets datasource
            DataSource ds = DataSourceFactory.createDataSource(
                    conn.getDatabaseUrl(),
                    conn.getDatabaseUser(),
                    conn.getDatabasePassword(),
                    conn.getDriverClass(),
                    conn.getDatabaseJndiName());
            
            //create query
            PreparedStatementCreatorImpl pstmt = 
                    new PreparedStatementCreatorImpl(getSqlQuery());
            
            //add params
            for(JdbcParameter param : getParameters()){
                
                //need to convert value to expected parameter type
                Object reportParamValue = DataTypeConversionUtil
                        .getCastedObjectToType(param.getType(),
                        params.get(param.getReportParameter()));
                
                //get the sql type to give to the prepared statement
                int sqlType = DataTypeConversionUtil
                        .getSqlTypeFromDataTypeEnum(param.getType());
                
                //add parameter to the prepared statement
                pstmt.addParameter(param.getPosition(), reportParamValue, sqlType);
            }
            
            //create jdbc template
            JdbcTemplate template = new JdbcTemplate(ds);
            DataSet results = template.query(pstmt,new RowCallbackHandlerImpl());

            //return results
            return results;
    }
    
}
