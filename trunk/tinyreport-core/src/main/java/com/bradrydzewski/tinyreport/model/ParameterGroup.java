package com.bradrydzewski.tinyreport.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brad Rydzewski
 */
public class ParameterGroup {

    /**
     * Name of the parameter group.
     */
    private String name;
    /**
     * Defines a parameter group as cascading.
     */
    private boolean cascading = false;
    /**
     * List of parameters that are part of the group.
     */
    private List<Parameter> parameters = new ArrayList<Parameter>();

    public ParameterGroup() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public boolean isCascading() {
        return cascading;
    }

    public void setCascading(boolean cascading) {
        this.cascading = cascading;
    }
}
