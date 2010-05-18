package com.bradrydzewski.tinyreport.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Abstract connection to a data source.
 * @author Brad Rydzewski
 */
public abstract class DataConnection {

    private String name;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
