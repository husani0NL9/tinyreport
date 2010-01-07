/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bradrydzewski.tinyreport.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


    
/**
 * Creates appropriate instances of <code>javax.sql.DataSource</code> for the
 * reporting system data-access objects to use.
 *
 * @author Bradley N. Rydzewski
 * @author Carlos D. Morales
 */
public final class DataSourceFactory {

    /**
     * Not visible constructor to avoid instantiation of this class.
     */
    private DataSourceFactory() {

    }
    
    /**
     * Creates a DataSource based on the configuration specified in the
     * <code>ReportDesign</code> file. This method tries to query a
     * JNDI tree to find a <code>DataSource</code> object bound, if it does not
     * succeed, creates a dummy implementation using plain <code>Driver
     * Manager</code>
     *
     * @return A <code>javax.sql.DataSource</code> object either locally built
     *         or pulled from JNDI
     */
    public static DataSource createDataSource(String url, String user, String password, String className, String jndiPath) {

        DataSource result = null;
        
        
        try{
            result = getFromJndi(jndiPath);
        }catch(Exception ex){} //here we supress the error, and revert to driver manager connection

        if (result == null) {
            result = buildFromDriverManager(url,user,password,className);
        }

        if (result != null) {
            return result;
        }

        throw new IllegalStateException("A DataSource could not be found or configured");
    }

    /**
     * Returns a Data source from a JNDI tree which was specified in the
     * <code>DataSourceDefinition</code> object.
     * @return
     *         A configured <code>DataSource</code> ready to use.
     */
    private static DataSource getFromJndi(String jndiName) {
        
        if (jndiName==null || jndiName.length() == 0) {
            return null;
        }
        DataSource dataSource;
        try {
            Context jndiCtx = new InitialContext();
            dataSource = (DataSource) jndiCtx.lookup(jndiName);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Exception in Getting Connection for Jndi Name ["+jndiName+"]", ex);
        }
        return dataSource;
    }

    /**
     * Returns a <code>DataSource</code> that was built from the
     * <code>DriverManager</code>.
     * @return
     *         A configured <code>Datasource</code>.
     */
    private static DataSource buildFromDriverManager(String url, String user, String password, String className) {
        return new DataSourceImpl(url, user, password, className);
    }
}
