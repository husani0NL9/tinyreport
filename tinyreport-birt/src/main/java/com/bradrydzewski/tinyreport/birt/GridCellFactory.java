package com.bradrydzewski.tinyreport.birt;

import com.bradrydzewski.tinyreport.html.GridCell;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import org.eclipse.birt._2005.design.CellType;
import org.eclipse.birt._2005.design.ChartItemType;
import org.eclipse.birt._2005.design.DataItemType;
import org.eclipse.birt._2005.design.DesignElement;
import org.eclipse.birt._2005.design.ExtendedItemType;
import org.eclipse.birt._2005.design.FreeFormItemType;
import org.eclipse.birt._2005.design.GridItemType;
import org.eclipse.birt._2005.design.ImageItemType;
import org.eclipse.birt._2005.design.IncludeItemType;
import org.eclipse.birt._2005.design.LabelItemType;
import org.eclipse.birt._2005.design.TableItemType;
import org.eclipse.birt._2005.design.TextDataItemType;
import org.eclipse.birt._2005.design.TextItemType;

/**
 * Takes a birt CellType element and converts it to a tinyreport GridCell.
 * @author Brad Rydzewski
 */
public class GridCellFactory {

    public static GridCell createGridCell(CellType item,
            ReportDefinition reportDefinition) {

        GridCell cell = new GridCell();
        //need to get child items
        //need to get style

        for(DesignElement elem : item.getBrowserControlOrDataOrChart()) {
            if(elem instanceof ImageItemType) {

            } else if(elem instanceof LabelItemType) {

            } else if(elem instanceof TextItemType) {

            } else if(elem instanceof ExtendedItemType) {

            } else if(elem instanceof DataItemType) {

            } else if(elem instanceof ChartItemType) {

            } else if(elem instanceof FreeFormItemType) {

            } else if(elem instanceof GridItemType) {

            } else if(elem instanceof ImageItemType) {

            } else if(elem instanceof IncludeItemType) {

            } else if(elem instanceof TextDataItemType) {

            } else if(elem instanceof TableItemType) {

            } else {

            }
        }

        return cell;
    }
}
