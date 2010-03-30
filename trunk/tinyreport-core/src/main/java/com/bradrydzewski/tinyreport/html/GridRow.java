package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;
import java.util.ArrayList;
import java.util.List;
import org.apache.ecs.xhtml.tr;

/**
 * Represents a row in a standard HTML table, corespoding to a <tr> tag
 * @author Brad Rydzewski
 */
public class GridRow extends Element {

    private List<GridCell> cells;

    /**
     * Gets a list of cells in a row
     * @return
     */
    public List<GridCell> getCells() {
        return cells;
    }

    /**
     * Sets a list of cells in a ro
     * @param cells
     */
    public void setCells(List<GridCell> cells) {
        this.cells = cells;
    }

    /**
     * Adds one or many cells to the row
     * @param cells
     */
    public GridRow addCells(GridCell... cells) {
        if (getCells() == null) {
            setCells(new ArrayList<GridCell>());
        }

        for (GridCell c : cells) {
            getCells().add(c);
        }

        return this;
    }

    /**
     * Adds a single cell to the row
     * @param cell
     * @return
     */
    public GridRow addCell(GridCell cell) {
        if (getCells() == null) {
            setCells(new ArrayList<GridCell>());
        }

        getCells().add(cell);
        return this;
    }

    @Override
    public void build(org.apache.ecs.Element parent, ReportBuilderArgs args) {

        //create the row element
        tr tr = new tr();
        tr.setPrettyPrint(true);

        //add the style
        if(styleName!=null)
            tr.setClass(styleName);

        //build rows (<tr>)
        for(GridCell row : cells){
            row.build(tr, args);
        }

        //add the table to the parent directory
        parent.addElementToRegistry(tr);

    }

}
