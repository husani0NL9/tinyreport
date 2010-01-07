package com.bradrydzewski.tinyreport.model;

/**
 * Abstract connection to a data source.
 * @author Brad Rydzewski
 */
public abstract class DataConnection {

    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
