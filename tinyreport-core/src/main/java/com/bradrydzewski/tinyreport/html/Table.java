package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;
import com.bradrydzewski.tinyreport.filter.Filter;
import com.bradrydzewski.tinyreport.jdbc.DataSet;
import com.bradrydzewski.tinyreport.model.DataQuery;
import com.bradrydzewski.tinyreport.util.DataSetUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.ecs.xhtml.table;
import org.apache.ecs.xhtml.tbody;
import org.apache.ecs.xhtml.tfoot;
import org.apache.ecs.xhtml.thead;

/**
 *
 * @author Brad
 */
public class Table extends Grid {

    private List<GridRow> headerRows = new ArrayList<GridRow>();
    private List<GridRow> footerRows = new ArrayList<GridRow>();
    private List<DataGroup> dataGroups = new ArrayList<DataGroup>();
    private DataQuery dataQuery;
    private List<Filter> filters;

    public List<DataGroup> getDataGroups() {
        return dataGroups;
    }

    public void setDataGroups(List<DataGroup> dataGroups) {
        this.dataGroups = dataGroups;
    }

    public List<GridRow> getFooterRows() {
        return footerRows;
    }

    public void setFooterRows(List<GridRow> footerRows) {
        this.footerRows = footerRows;
    }

    public List<GridRow> getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(List<GridRow> headerRows) {
        this.headerRows = headerRows;
    }

    public DataQuery getDataQuery() {
        return dataQuery;
    }

    public void setDataQuery(DataQuery dataQuery) {
        this.dataQuery = dataQuery;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public void build(org.apache.ecs.Element parent, ReportBuilderArgs args) {

        //crate the table element
        table table = new table();
        table.setPrettyPrint(true);

        //add the style
        if (style != null) {
            table.setClass(style.getName());
        }

        //set table attributes
        table.setCellPadding(cellPadding);
        table.setCellSpacing(cellSpacing);
        table.setBorder(border);

        //get the data set that the table is bound to, apply any filters
        DataSet dataSet = args.getResults().get(dataQuery.getName());
        dataSet = DataSetUtil.getFilteredDataSet(dataSet, filters);
        //set data set in report builder args
        args.setCurrentDataSet(dataSet);

        //build the header rows
        buildTableHeaderRows(table, args);

        //build table based on whether it is grouped or not
        if (dataGroups == null || dataGroups.isEmpty()) {
            buildTableWithoutGroups(table, args);
        } else {
            buildTableWithGroups(table, args);
        }

        //build footer rows
        buildTableFooterRows(table, args);

        //add the table to the parent directory
        parent.addElementToRegistry(table);

    }

    void buildTableHeaderRows(table table, ReportBuilderArgs args) {

        thead header = new thead();
        header.setPrettyPrint(true);
        table.addElement(header);

        for (GridRow row : getHeaderRows()) {
            row.build(header, args);
        }
    }

    void buildTableFooterRows(table table, ReportBuilderArgs args) {

        tfoot footer = new tfoot();
        footer.setPrettyPrint(true);
        table.addElement(footer);

        for (GridRow row : getFooterRows()) {
            row.build(footer, args);
        }
    }

    void buildTableWithoutGroups(table table, ReportBuilderArgs args) {

        tbody tbody = new tbody();
        tbody.setPrettyPrint(true);
        table.addElement(tbody);

        for (Object[] dataRow : args.getCurrentDataSet().getRows()) {
            //build rows (<tr>)
            for (GridRow row : getRows()) {
                args.setCurrentDataRow(dataRow);
                row.build(tbody, args);
            }
        }
    }

    void buildTableWithGroups(table table, ReportBuilderArgs args) {

        //1) Need to sort groups (desc?)
        //2) Need to sort data based on groups?? do we?? i don't know!
        //3) Render header rows
        //4) Render body rows
        //5) Render footer rows


        tbody tbody = new tbody();
        tbody.setPrettyPrint(true);
        table.addElement(tbody);

        for (Object[] dataRow : args.getCurrentDataSet().getRows()) {
            //build rows (<tr>)
            for (GridRow row : getRows()) {
                args.setCurrentDataRow(dataRow);
                row.build(tbody, args);
            }
        }
    }
}
