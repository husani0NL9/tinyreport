package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brad Rydzewski
 */
public class ElementBuilderFactory {

    private static ElementBuilderFactory INSTANCE
            = new ElementBuilderFactory();

    public static ElementBuilderFactory get() {
        return INSTANCE;
    }
    protected DataElementBuilder dataBuilder = new DataElementBuilder();
    protected GridBuilder gridBuilder = new GridBuilder();
    protected TextBuilder textBuilder = new TextBuilder();
    protected TableBuilder tableBuilder = new TableBuilder();
    protected ImageBuilder imageBuilder = new ImageBuilder();

    public List<Element> buildChildren(XMLDataObject xml) {

        List<Element> elementList = new ArrayList<Element>();
        for(XMLDataObject elementObject : xml.getChildNodes()) {
                ElementBuilder builder =
                        ElementBuilderFactory.get().getBuilder(elementObject);
                if(builder!=null) {
                    Element element = builder.getElement(elementObject);
                    elementList.add(element);
                }
            }
        return elementList;
    }

    public ElementBuilder getBuilder(XMLDataObject xml) {

        ElementBuilder builder = null;
        String name = xml.getName();

        if (name.equals("grid")) {
            builder = gridBuilder;
        } else if (name.equals("table")) {
            builder = tableBuilder;
        } else if (name.equals("label")) {
            builder = textBuilder;
        } else if (name.equals("text")) {
            builder = textBuilder;
        } else if (name.equals("data")) {
            builder = dataBuilder;
        } else if (name.equals("image")) {
            builder = imageBuilder;
        } else {
            System.out.println("unknown node: "+name);
        }

        return builder;
    }
}
