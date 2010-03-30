package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;
import com.bradrydzewski.tinyreport.html.GridCell;
import com.bradrydzewski.tinyreport.html.GridRow;
import com.bradrydzewski.tinyreport.html.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brad
 */
public class TableBuilder extends ElementBuilder {

    @Override
    public Element getElement(XMLDataObject xml) {

        Table table = new Table();

        String dataSet = xml.getString("property[@name='dataSet']");
        table.setDataQuery(dataSet);

        List<XMLDataObject> headerRows = xml.getList("header/row");
        for(XMLDataObject row : headerRows) {
            table.getHeaderRows().add(getGridRow(row));
        }

        List<XMLDataObject> bodyRows = xml.getList("detail/row");
        for(XMLDataObject row : bodyRows) {
            table.getRows().add(getGridRow(row));
        }

        return table;
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

        List<Element> childElements =
                ElementBuilderFactory.get().buildChildren(xml);

        if(childElements!=null && childElements.size()>0)
            cell.setChild(childElements.get(0));

        return cell;
    }
}
