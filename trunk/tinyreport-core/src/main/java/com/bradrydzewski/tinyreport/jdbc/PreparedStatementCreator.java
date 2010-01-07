package com.bradrydzewski.tinyreport.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Defines a single operation for components capable of configuring a a {@link
 * java.sql.PreparedStatement} to be reado for execution.
 *
 * @author Bradley N. Rydzewski
 * @author Carlos D. Morales
 */
public interface PreparedStatementCreator {

    /**
     * Creates a prepared statement with an arbitrary SQL statement using the
     * provided <code>conn</code> object to create it
     *
     * @param conn An open JDBC database connection
     * @return A {@link java.sql.PreparedStatement} object ready for execution
     *         (SQL statement and parameters configured)
     * @throws DataAccessException If an error configuring the prepared
     *                             statement occurrs
     * @throws SQLException        If a low-level {@link java.sql.SQLException}
     *                             occurrs
     */
    public PreparedStatement createPreparedStatement(Connection conn) throws
            DataAccessException, SQLException;
}
