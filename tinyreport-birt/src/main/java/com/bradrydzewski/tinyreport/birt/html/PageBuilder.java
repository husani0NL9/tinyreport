package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;
import com.bradrydzewski.tinyreport.html.Page;
import java.util.ArrayList;

/**
 *
 * @author Brad Rydzewski
 */
public class PageBuilder {

    public static void getPage(XMLDataObject xml) {

        Page page = new Page();
        page.setChildElements(new ArrayList<Element>());

        for(XMLDataObject object : xml.getChildNodes()) {
            ElementBuilder builder = ElementBuilderFactory
                    .get().getBuilder(object);
            if(builder!=null) {
                Element element = builder.getElement(object);
                page.getChildElements().add(element);
            }
        }
    }

}
