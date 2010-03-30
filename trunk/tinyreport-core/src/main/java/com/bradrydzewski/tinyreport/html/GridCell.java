package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;
import org.apache.ecs.xhtml.td;

/**
 *
 * @author Brad Rydzewski
 */
public class GridCell extends Element {

    private int order;
    private int columnSpan;
    private int rowSpan;
    private Element child;
    
    public GridCell() {
        
    }
    public GridCell(Element child) {
        this(child,0,0);
    }
    public GridCell(Element child, String style) {
        setChild(child);
        setStyleName(style);
    }
    public GridCell(Element child, int cSpan, int rSpan) {
        setChild(child);
        setColumnSpan(cSpan);
        setRowSpan(rSpan);
    }

    public Element getChild() {
        return child;
    }

    public void setChild(Element child) {
        this.child = child;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    @Override
    public void build(org.apache.ecs.Element parent, ReportBuilderArgs args)  {

        //create the cell element
        td td = new td();
        td.setPrettyPrint(true);

        //add the style
        if(styleName!=null)
            td.setClass(styleName);

        //build the child element
        child.build(td, args);

        //add the table to the parent directory
        parent.addElementToRegistry(td);

    }
}
