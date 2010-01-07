package com.bradrydzewski.tinyreport.model;

/**
 * Input parameter used to build a report. The parameter will be
 * requested from the end-user in the form of a text box (free-form).
 * @author Brad Rydzewski
 */
public class ReportParameterTextBox extends ReportParameter {

    public ReportParameterTextBox() {
    }

    public ReportParameterTextBox(String nm, String prmpt) {
        this.setName(nm);
        this.setPrompt(prmpt);
        this.setValue(nm);
    }

    public ReportParameterTextBox(String nm, String prmpt, Object val) {
        this.setName(nm);
        this.setPrompt(prmpt);
        this.setValue(val);
    }

}
