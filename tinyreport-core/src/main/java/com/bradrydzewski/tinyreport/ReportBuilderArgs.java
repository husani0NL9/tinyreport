/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bradrydzewski.tinyreport;

import com.bradrydzewski.tinyreport.jdbc.DataSet;
import com.bradrydzewski.tinyreport.model.Parameter;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import java.util.Map;
import javax.script.ScriptEngine;
import org.apache.ecs.Document;

/**
 *
 * @author Brad
 */
public class ReportBuilderArgs {

    private Document document;
    private ReportDefinition reportDefinition;
    private DataSet currentDataSet;
    private Object[] currentDataRow;
    private ScriptEngine scriptEngine;
    private Map<String,DataSet> results;
    private Map<String, Parameter> parameters;

    public ReportBuilderArgs(ReportDefinition reportDefinition,
            Map<String, DataSet> results,
            ScriptEngine scriptEngine, Document document) {

        this.scriptEngine = scriptEngine;
        this.results = results;
        this.reportDefinition = reportDefinition;
        this.document = document;
    }

    public ReportDefinition getReportDefinition(){
        return this.reportDefinition;
    }

    public Map<String, DataSet> getResults(){
        return this.results;
    }

    /**
     * Gets the report as an HTML document.
     * @return HTML report document.
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Gets the DataSet currently being processed by the build engine
     * @return
     */
    public DataSet getCurrentDataSet() {
        return currentDataSet;
    }

    /**
     * Sets the DataSet currently being processed by the build engine
     * @param currentDataSet
     */
    public void setCurrentDataSet(DataSet currentDataSet) {
        this.currentDataSet = currentDataSet;
    }

    /**
     * Gets the DataRow currently being processed by the build engine
     * @return
     */
    public Object[] getCurrentDataRow() {
        return currentDataRow;
    }

    /**
     * Sets the DataRow currently being processed by the build engine
     * @param dataRow
     */
    public void setCurrentDataRow(Object[] dataRow) {
        this.currentDataRow = dataRow;
    }

    /**
     * Gets the script engine to be used for custom scripting
     * @return
     */
    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    public Map<String, Parameter> getParameters() {
        return parameters;
    }

    /**
     * Get's the Column value for the current data row.
     * @return
     */
    public Object getValue(String column) {
        return this.getValue(currentDataSet.getColumnIndex(column));
    }

    public Object getValue(int index) {
        return currentDataRow[index];
    }
}
