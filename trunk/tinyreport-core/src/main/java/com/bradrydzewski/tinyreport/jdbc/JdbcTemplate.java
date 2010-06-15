package com.bradrydzewski.tinyreport.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Provides a template for safe interactions with JDBC API. Instances of
 * <code>JdbcTemplate</code> correctly allocate a connection, let their two
 * collaborators (see {@link com.google.code.gwtreportengine.jdbc.PreparedStatementCreator} 
 * and {@link com.google.code.gwtreportengine.jdbc.RowCallbackHandler}) 
 * perform the specific logic and finally release the JDBC Connection.
 */
public class JdbcTemplate {

    /**
     * Holds the {@link javax.sql.DataSource} with which this template will
     * interact to to retrieve and release JDBC connections.
     */
    private DataSource dataSource;

    /**
     * Creates a <code>JdbcTemplate</code> with the specified
     * <code>dataSource</code> as its origin of data/connections.
     *
     * @param ds A valid {@link javax.sql.DataSource} to pull
     * connections from
     */
    public JdbcTemplate(final DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * Executes a query in the database represented this object's
     * <code>dataSource</code>.
     *
     * @param psCreator An object capable of creating and configuring a {@link
     * java.sql.PreparedStatement} from an open {@link java.sql.Connection}
     * @param callbackHandler An object capable of mapping the rows in a {@link
     * java.sql.ResultSet} to a Gatekeeper domain-model object
     * @return A {@link java.util.List} with the mapped objects resulting from
     *         the executed query. An empty list will be returned in case no
     *         results match the query criteria, <code>null</code> will never be
     *         returned.
     *         returned
     */
    public final DataSet query(final PreparedStatementCreator psCreator,
                      final RowCallbackHandler callbackHandler) {

        Connection conn = null;
        ResultSet rs = null;
        DataSet ds = null;
        List<Column> columns = new ArrayList<Column>();
        List<Object[]> result = new ArrayList<Object[]>();

        try {
            conn = DataSourceUtils.getConnection(this.dataSource);
            PreparedStatement ps = psCreator.createPreparedStatement(conn);

            rs = ps.executeQuery();

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();
            
            for(int i=0;i<columnCount;i++){
                columns.add(Column.fromResultSetMetaData(
                        rsMetaData, i));
            }
            
            while (rs.next()) {
                result.add(callbackHandler.processRow(rs));
            }

            ds = new DataSet(columns,result);

            rs.close();
            ps.close();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            throw new DataAccessException(
                    "An exception occurred while performing the query.", ex);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
        
        return ds;
    }
}

