package com.bradrydzewski.tinyreport.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Input parameter used to build a report. The parameter will be
 * requested from the end-user in the form of a text box (free-form).
 * @author Brad Rydzewski
 */
public class ParameterDateTextBox extends Parameter {

    public ParameterDateTextBox() {
    }

    public ParameterDateTextBox(String nm, String prmpt) {
        this.setName(nm);
        this.setPrompt(prmpt);
        this.setValue(nm);
    }

    public ParameterDateTextBox(String nm, String prmpt, Object val) {
        this.setName(nm);
        this.setPrompt(prmpt);
        this.setValue(val);
    }

    private String dateFormat = "yyyy-MM-dd";

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    /**
     * Sets the value of the input parameter provided by the end user, 
     * and converts to a valid Date object using the date format
     * that is provided.
     * @param val parameter value, in Date format.
     */
    @Override
    public void setValue(final Object val) {
        
        try {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            Date date = df.parse(val.toString());
            super.setValue(date);
        } catch(ParseException ex) {
            throw new RuntimeException(ex);
        }

    }
    
}
