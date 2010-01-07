package com.bradrydzewski.tinyreport.model;

import com.bradrydzewski.tinyreport.html.Page;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines a Report including data sources, parameters, output and formatting
 * @author Brad Rydzewski
 */
public class ReportDefinition {


    private String name;
    private Date created = new Date();
    private Date updated = new Date();
    private String description;
    private String author;
    private Map<String, DataConnection> dataConnections = new HashMap<String, DataConnection>();
    private Map<String, ReportParameter> reportParameters = new HashMap<String, ReportParameter>();
    private Map<String, DataQuery> dataQueries = new HashMap<String, DataQuery>();
    private Page htmlWebPage = new Page();


    public Page getPage() {
        return htmlWebPage;
    }

    public void setPage(Page htmlDocument) {
        this.htmlWebPage = htmlDocument;
    }
    
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Map<String, DataConnection> getDataConnections() {
        return dataConnections;
    }

    public void setDataConnections(Map<String, DataConnection> dataConnections) {
        this.dataConnections = dataConnections;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ReportParameter> getReportParameters() {
        return reportParameters;
    }

    public void setReportParameters(Map<String, ReportParameter> reportParameters) {
        this.reportParameters = reportParameters;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Map<String, DataQuery> getDataQueries() {
        return dataQueries;
    }

    public void setDataQueries(Map<String, DataQuery> dataQueries) {
        this.dataQueries = dataQueries;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
}
