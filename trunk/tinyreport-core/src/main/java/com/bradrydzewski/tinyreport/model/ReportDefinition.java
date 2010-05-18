package com.bradrydzewski.tinyreport.model;

import com.bradrydzewski.tinyreport.html.Page;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Defines a Report including data sources, parameters, output and formatting
 * @author Brad Rydzewski
 */
@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class ReportDefinition {

    private String name;
    private Date created = new Date();
    private Date updated = new Date();
    private String description;
    private String author;
    private String createdBy;
    private List<DataConnection> dataConnections = new ArrayList<DataConnection>();
    private List<DataQuery> dataQueries = new ArrayList<DataQuery>();
    private List<ParameterGroup> parameters = new ArrayList<ParameterGroup>();
    private Page htmlWebPage = new Page();
    private Map<String, String> properties = new HashMap<String, String>();

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

    @XmlElementWrapper
    @XmlElementRefs({
        @XmlElementRef(name="JdbcConnection", type = JdbcConnection.class)
    })
    public List<DataConnection> getDataConnections() {
        return dataConnections;
    }

    public void setDataConnections(List<DataConnection> dataConnections) {
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

    public List<ParameterGroup> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterGroup> parameters) {
        this.parameters = parameters;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @XmlElementWrapper
    @XmlElementRefs({
        @XmlElementRef(name="JdbcQuery", type = JdbcQuery.class)
    })
    public List<DataQuery> getDataQueries() {
        return dataQueries;
    }

    public void setDataQueries(List<DataQuery> dataQueries) {
        this.dataQueries = dataQueries;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
