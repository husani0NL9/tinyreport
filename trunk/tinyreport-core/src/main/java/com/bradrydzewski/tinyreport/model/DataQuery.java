package com.bradrydzewski.tinyreport.model;

import com.bradrydzewski.tinyreport.filter.Filter;
import com.bradrydzewski.tinyreport.jdbc.DataSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Abstract class representing a query of any data source, such as database,
 * CSV, Excel, POJO, etc.
 * TODO: Remove JdbcParameter list since it is specific to the JdbcQuery impl.
 * @author Brad Rydzewski
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DataQuery {

    /**
     * The name of the data query.
     * @serial
     */
    @XmlAttribute
    private String name;
    /**
     * Columns in the data query result set.
     * @serial
     */
    private List<DataColumn> columns = new ArrayList<DataColumn>();
    /**
     * Input parameters used to build the data query.
     */
    private List<JdbcParameter> parameters = new ArrayList<JdbcParameter>();
    /**
     * Filters used to limit the data query's results.
     */
    private List<Filter> filters = new ArrayList<Filter>();
    /**
     * Name of the DataConnection the query will use.
     */
    private String connection;


    /**
     * Gets the list of {@link DataColumn} objects defined in
     * this data query.
     * @return list of data columns.
     */
    public final List<DataColumn> getColumns() {
        return columns;
    }

    /**
     * Sets the list of {@link DataColumn} objects in the dataset.
     * @param cols List of data columns.
     */
    public final void setColumns(final List<DataColumn> cols) {
        this.columns = cols;
    }

    /**
     * Gets a list of parameters used to build a data query
     * (ie prepared statement).
     * @return list of data query parameters.
     */
    public final List<JdbcParameter> getParameters() {
        return parameters;
    }

    /**
     * Sets a list of parameters used to build a data query
     * (ie prepared statement).
     * @param params list of query parameters.
     */
    public final void setParameters(final List<JdbcParameter> params) {
        this.parameters = params;
    }

    /**
     * Gets the name of the data query.
     * @return query name.
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name of the data query.
     * @param nm name of the query.
     */
    public final void setName(final String nm) {
        this.name = nm;
    }

    /**
     * Gets a list of filters that are applied to the data
     * query results to limit the data set.
     * @return list of filters.
     */
    public final List<Filter> getFilters() {
        return filters;
    }

    /**
     * Sets the list of Fitlers that should be applied to limit the data
     * set returned by this data query.
     * @param filtrs list of filters.
     */
    public final void setFilters(final List<Filter> filtrs) {
        this.filters = filtrs;
    }

    /**
     * Gets the Name of the DataConnection that should be used when
     * executing the query.
     * @return Name of DataConnection.
     */
    public String getDataConnection() {
        return connection;
    }

    /**
     * Sets the Name of the DataConnection that should be used when
     * executing the query.
     * @param connection Name of DataConnection.
     */
    public void setDataConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Executes the data query and adds the result to the
     * ReportDefinition's DataSet map.
     * @param dataConnection DataConnection used to connect to the data source
     *                          and execute the query
     * @param params List of parameters provided as input.
     * @return DataSet contain query results.
     */
    public abstract DataSet execute(DataConnection dataConnection,
            Map<String, Parameter> params);
}
