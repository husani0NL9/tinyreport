package com.bradrydzewski.tinyreport.birt;

import com.bradrydzewski.tinyreport.html.Grid;
import com.bradrydzewski.tinyreport.html.GridCell;
import com.bradrydzewski.tinyreport.html.GridRow;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import org.eclipse.birt._2005.design.CellType;
import org.eclipse.birt._2005.design.ColumnType;
import org.eclipse.birt._2005.design.DesignElement;
import org.eclipse.birt._2005.design.GridItemType;
import org.eclipse.birt._2005.design.RowType;

/**
 * Takes a birt GridItemType element and converts it to a tinyreport Grid.
 * @author Brad Rydzewski
 */
public class GridFactory {

    public static Grid createGrid(GridItemType item,
            ReportDefinition reportDefinition) {

        Grid grid = new Grid();
        //need to get style info & other misc properties

        int colCount = 0;
        int rowCount = 0;

        for (DesignElement elem : item.getRowAndColumn()) {

            if (elem instanceof ColumnType) {
                colCount++;
                //we need to record columns in order to get the desired width...
                //for now this isn't provided, but we will need to add it
                
            } else if (elem instanceof RowType) {
                rowCount++;

                //create the row
                GridRow row = new GridRow();
                grid.addRow(row);

                //add each cell to the grid
                for (CellType cellElem : ((RowType) elem).getCell()) {
                    GridCell cell = GridCellFactory.createGridCell(
                            cellElem, reportDefinition);

                    row.addCell(cell);
                }
            }
        }

        return grid;
    }
}
