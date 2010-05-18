package com.bradrydzewski.tinyreport.html;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Brad Rydzewski
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Style {

    @XmlAttribute
    private String name;
    @XmlValue
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Deprecated
    public String toCSS() {
        return value;
    }
}
