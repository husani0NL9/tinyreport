package com.bradrydzewski.tinyreport.model;

/**
 * Input parameter used to build a report. The parameter will be
 * requested from the end-user in the form of a text box (free-form).
 * @author Brad Rydzewski
 */
public class ParameterRegexTextBox extends Parameter {

    private String validationString;
    
    public ParameterRegexTextBox() {
    }

    public ParameterRegexTextBox(String nm, String prmpt) {
        this.setName(nm);
        this.setPrompt(prmpt);
        this.setValue(nm);
    }

    public ParameterRegexTextBox(String nm, String prmpt,
            String validationString, Object val) {
        this.setName(nm);
        this.setPrompt(prmpt);
        this.setValue(val);
        this.setValidationString(validationString);
    }

    public String getValidationString() {
        return validationString;
    }

    public void setValidationString(String validationString) {
        this.validationString = validationString;
    }
    
    

}
