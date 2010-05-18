package com.bradrydzewski.tinyreport.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brad Rydzewski
 */
@XmlRootElement
public class JdbcConnection extends DataConnection {

    private String driverClass;
    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;
    private String databaseJndiName;
    

    public String getDatabaseJndiName() {
        return databaseJndiName;
    }

    public void setDatabaseJndiName(String databaseJndiName) {
        this.databaseJndiName = databaseJndiName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }    
    
    
}
