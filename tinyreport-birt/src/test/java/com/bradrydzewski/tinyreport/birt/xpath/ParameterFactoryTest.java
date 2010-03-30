package com.bradrydzewski.tinyreport.birt.xpath;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brad Rydzewski
 */
public class ParameterFactoryTest {

    private StringBuilder textParameter = new StringBuilder()
            .append("<scalar-parameter name='NewParameter' id='59'>")
            .append(" <property name='hidden'>true</property>")
            .append(" <text-property name='helpText'>some help text</text-property>")
            .append(" <text-property name='promptText'>Test text</text-property>")
            .append(" <property name='valueType'>static</property>")
            .append(" <property name='isRequired'>true</property>")
            .append(" <property name='dataType'>string</property>")
            .append(" <property name='distinct'>true</property>")
            .append(" <property name='paramType'>simple</property>")
            .append(" <property name='controlType'>text-box</property>")
            .append(" <structure name='format'>")
            .append("  <property name='category'>Unformatted</property>")
            .append(" </structure>")
            .append("</scalar-parameter>");


    @Test
    public void testGetReportParameters() {
    }

    @Test
    public void testGetReportParameter() throws Exception {
    }

    @Test
    public void testGetReportParameterDateTextBox() throws Exception {
    }

    @Test
    public void testGetReportParameterTextBox() throws Exception {
    }

}