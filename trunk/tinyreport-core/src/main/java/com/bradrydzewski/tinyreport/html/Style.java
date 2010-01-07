/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bradrydzewski.tinyreport.html;

/**
 *
 * @author Brad
 */
public class Style {

    private String name;
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
