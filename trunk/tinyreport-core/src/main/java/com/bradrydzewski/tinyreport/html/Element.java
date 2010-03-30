package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;

/**
 * Represents an HTML Element
 * @author Brad Rydzewski
 */
public abstract class Element {

    private String id;
    private String name;
    protected String styleName;
    private String styleString;

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

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String style) {
        this.styleName = style;
    }

    public String getStyleString() {
        return styleString;
    }

    public void setStyleString(String styleString) {
        this.styleString = styleString;
    }

    public abstract void build(org.apache.ecs.Element parent,
            ReportBuilderArgs args);

}
