package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;

/**
 * Represents an HTML Element
 * @author Brad Rydzewski
 */
public abstract class Element {

    private String id;
    private String name;
    protected Style style;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public abstract void build(org.apache.ecs.Element parent,
            ReportBuilderArgs args);

}
