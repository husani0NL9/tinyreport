package com.bradrydzewski.tinyreport.model;

/**
 * Input parameter used to build a report.
 * @author Brad Rydzewski
 */
public abstract class ReportParameter {

    /**
     * Name of the report parameter.
     * @serial
     */
    private String name;

    /**
     * Prompt given to end user when requesting input parameter.
     */
    private String prompt;

    /**
     * Value of input collected from end user.
     */
    private Object value;

    /**
     * Is report parameter required.
     */
    private boolean required;

    /**
     * Gets the name of the parameter.
     * @return parameter name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name of the parameter.
     * @param nm parameter name.
     */
    public final void setName(final String nm) {
        this.name = nm;
    }

    /**
     * Gets message given to the end-user when requesting input parameter.
     * @return message requesting input parameter.
     */
    public final String getPrompt() {
        return prompt;
    }

    /**
     * Sets message given to the end-user when requesting input parameter.
     * @param prmpt message displayed when requesting input parameters.
     */
    public final void setPrompt(final String prmpt) {
        this.prompt = prmpt;
    }

    /**
     * Gets the value of the input parameter provided by the end user.
     * @return parameter value.
     */
    public final Object getValue() {
        return value;
    }

    /**
     * Sets the value of the input parameter provided by the end user.
     * @param val parameter value.
     */
    public void setValue(final Object val) {
        this.value = val;
    }

    /**
     * Gets flag indicating if the parameter is required.
     * @return Required field flag.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets flag indicating the parameter are required or not.
     * @param required Required field flag.
     */
    public void setRequired(boolean required) {
        this.required = required;
    }
}
