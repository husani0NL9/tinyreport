package com.bradrydzewski.tinyreport.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Defines a single operation that components in charge or processing a JDBC
 * query result set should implement to create domain-model objects from rows in
 * the result-set.
 *
 * @author Bradley N. Rydzewski
 * @author Carlos D. Morales
 */
public interface RowCallbackHandler {

    /**
     * Returns an arbitrary domain-model object from a row or rows read from the
     * <code>rs</code> {@link java.sql.ResultSet}.
     *
     * @param rs An open {@link java.sql.ResultSet} to read data from
     * @return A domain-model object of arbitrary type containing the database
     *         information as a Java object
     * @throws SQLException If a database low-level error occurrs
     */
    public Object processRow(ResultSet rs) throws SQLException;

}