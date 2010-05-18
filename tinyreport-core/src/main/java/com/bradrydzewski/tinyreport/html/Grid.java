package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import org.apache.ecs.xhtml.table;

/**
 * Represents a simple HTML table, containing a set of Rows
 * @author Brad Rydzewski
 */
public class Grid extends Element {

    private List<GridRow> rows = new ArrayList<GridRow>();
    @XmlAttribute
    protected int cellPadding = 0;
    @XmlAttribute
    protected int cellSpacing = 0;
    @XmlAttribute
    protected int border = 0;

    public int getCellPadding() {
        return cellPadding;
    }

    public void setCellPadding(int cellPadding) {
        this.cellPadding = cellPadding;
    }

    public int getCellSpacing() {
        return cellSpacing;
    }

    public void setCellSpacing(int cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    /**
     * Gets the a collection of Rows in the table
     * @return
     */
    public List<GridRow> getRows() {
        return rows;
    }

    /**
     * Sets the Rows in the table
     * @param rows
     */
    public void setRows(List<GridRow> rows) {
        this.rows = rows;
    }
    
    /**
     * Adds one or many rows to the table
     * @param rows
     */
    public void addRows(GridRow... rows){
        if(getRows()==null)
            setRows(new ArrayList<GridRow>());
        
        for(GridRow row : rows){
            getRows().add(row);
        }
    }
    
    /**
     * Adds a single row to the table
     * @param row
     * @return
     */
    public Grid addRow(GridRow row){
        if(getRows()==null)
            setRows(new ArrayList<GridRow>());
        
        getRows().add(row);
        return this;
    }

    public GridRow addRow() {
        GridRow row = new GridRow();
        addRow(row);
        return row;
    }

    @Override
    public void build(org.apache.ecs.Element parent, ReportBuilderArgs args) {

        //crate the table element
        table table = new table();
        table.setPrettyPrint(true);

        //add the style
        if(styleName!=null)
            table.setClass(styleName);

        table.setCellPadding(cellPadding);
        table.setCellSpacing(cellSpacing);
        table.setBorder(border);


        //build rows (<tr>)
        for(GridRow row : getRows()){
            row.build(table, args);
        }

        //add the table to the parent directory
        parent.addElementToRegistry(table);
    }
}
