package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;
import org.apache.ecs.xhtml.object;

/**
 *
 * @author brad
 */
public class ElementBuilderFactory {

    private static ElementBuilderFactory INSTANCE
            = new ElementBuilderFactory();

    public static ElementBuilderFactory get() {
        return INSTANCE;
    }

    protected GridBuilder gridBuilder = new GridBuilder();

    public ElementBuilder getBuilder(XMLDataObject xml) {

        ElementBuilder builder = null;
        String name = xml.getName();

        if (name.equals("grid")) {
            builder = gridBuilder;
        } else if (name.equals("table")) {
            builder = null;//set to TableBuilder
        }

        return builder;
    }
}
