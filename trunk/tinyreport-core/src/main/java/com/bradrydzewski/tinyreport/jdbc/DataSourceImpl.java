/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bradrydzewski.tinyreport.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Brad
 */
public class DataSourceImpl implements DataSource {

    private String url;
    private String user;
    private String password;
    private String className;

    public DataSourceImpl() {
    }

    public DataSourceImpl(String url, String user, String password, String className) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.className = className;
    }

    public Connection getConnection() throws SQLException {
        return getConnection(getUser(),getPassword());
    }

    public Connection getConnection(String username, String password) throws SQLException {
        try {
            Class.forName(getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(getUrl(), username, password);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
    }

    public void setLoginTimeout(int seconds) throws SQLException {
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("isWrapperFor method not supported yet in " + getClassName());
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("unwrap method not supported yet in " + getClassName());
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
