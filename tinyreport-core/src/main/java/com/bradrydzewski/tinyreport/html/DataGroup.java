package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.model.DataColumn;
import com.bradrydzewski.tinyreport.model.SortOrder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brad
 */
public class DataGroup implements Comparable<DataGroup> {

    private DataColumn dataColumn;
    private SortOrder sortOrder = SortOrder.Ascending;
    private int order = 0;
    private List<GridRow> headerRows = new ArrayList<GridRow>();
    private List<GridRow> footerRows = new ArrayList<GridRow>();

    public DataGroup(){}
    public DataGroup(DataColumn dataColumn, SortOrder sortOrder){
        setDataColumn(dataColumn);
        setSortOrder(sortOrder);
    }

    public DataColumn getDataColumn() {
        return dataColumn;
    }

    public void setDataColumn(DataColumn dataColumn) {
        this.dataColumn = dataColumn;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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


    @Override
    public int compareTo(DataGroup o) {

	int valA = o.getOrder();
	int valB = this.getOrder();
	return (valA<valB ? -1 : (valA==valB ? 0 : 1));

    }
}
