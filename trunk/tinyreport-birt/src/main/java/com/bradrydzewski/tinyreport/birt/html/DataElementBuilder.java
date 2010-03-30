package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.DataElement;
import com.bradrydzewski.tinyreport.html.Element;

/**
 *
 * @author Brad Rydzewski
 */
public class DataElementBuilder extends ElementBuilder {

    @Override
    public Element getElement(XMLDataObject xml) {

        System.out.println("Building data object");
        DataElement dataElement = new DataElement();
        dataElement.setColumn(xml.getString("property[@name='resultSetColumn']"));

        return dataElement;
    }

}
