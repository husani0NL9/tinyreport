package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;
import com.bradrydzewski.tinyreport.html.Grid;
import com.bradrydzewski.tinyreport.html.GridCell;
import com.bradrydzewski.tinyreport.html.GridRow;
import com.bradrydzewski.tinyreport.html.Text;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brad Rydzewski
 */
public class GridBuilder extends ElementBuilder {

    public Element getElement(XMLDataObject xml) {

        Grid grid = new Grid();
        grid.setRows(new ArrayList<GridRow>());

        List<XMLDataObject> rows = xml.getList("row");
        
        for(XMLDataObject row : rows) {
            GridRow gridRow = getGridRow(row);
            grid.addRow(gridRow);
        }

        return grid;
    }

    public GridRow getGridRow(XMLDataObject xml) {
        GridRow gridRow = new GridRow();
        gridRow.setCells(new ArrayList<GridCell>());

        List<XMLDataObject> objects = xml.getList("cell");
        for(XMLDataObject object : objects) {
            gridRow.addCell(getGridCell(object));
        }

        return gridRow;
    }

    public GridCell getGridCell(XMLDataObject xml) {
        GridCell cell = new GridCell();
        cell.setChild(new Text());
        return cell;
    }
}
