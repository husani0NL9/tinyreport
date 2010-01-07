package com.bradrydzewski.tinyreport.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Class containing static methods to obtain connections from JNDI and close
 * connections if necessary.
 *
 * @author Carlos D. Morales
 * @author Bradley N. Rydzewski
 */
public class DataSourceUtils {

    /**
     * Hidden constructor to avoid instantiating.
     */
    private DataSourceUtils() {
    }

    /**
     * Get data source from JNDI, under java:comp/env/jdbc.
     *
     * @param jndiName The JNDI name of a data source
     * @return A <code>javax.sql.DataSource</code> bound to
     *         <code>jndiName</code>
     * @throws NamingException if the data source cannot be located
     */
    public static DataSource getDataSourceFromJndi(final String jndiName) throws NamingException {
        Context initCtx = null;
        try {
            // Obtain the initial JNDI context
            initCtx = new InitialContext();
            // Perform JNDI lookup to obtain resource manager
            // connection factory
            return (javax.sql.DataSource) initCtx
                    .lookup("java:comp/env/jdbc/" + jndiName);
        } catch (NamingException ex) {
            throw new DataAccessException(
                    "Naming exception in JNDIConnectionFactory looking up "
                    + "data source [" + jndiName + "]", ex);
        } finally {
            try {
                if (initCtx != null) {
                    initCtx.close();
                }
            } catch (NamingException ex) {
                // This shouldn't ever happen
                throw new DataAccessException(
                        "Naming exception closing context in "
                        + "JNDIConnectionFactory.getConnection", ex);
            }
        }
    } 

    /**
     * Correctly changes exceptions if an exception occurs while retrieving the
     * connection from the <code>ds</code> DataSource
     *
     * @param ds A valid data source to pull the connection from
     * @return A <code>java.sql.Connection</code> retrieved from the data
     *         source
     * @throws DataAccessException If an exception is thrown while closing the
     *                             connection
     */
    public static Connection getConnection(DataSource ds) throws DataAccessException {
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            throw new DataAccessException("DataSource " + ds, ex);
        }
    }

    /**
     * Close the connection if necessary
     *
     * @param con connection to close if necessary. If this is null, this call
     * will be ignored
     * @throws DataAccessException If an exception is thrown while closing the
     *                             connection
     */
    public static void closeConnection(Connection con) throws DataAccessException {
        if (con == null)
            return;

        try {
            con.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to close connection", ex);
        }
    }
    
}