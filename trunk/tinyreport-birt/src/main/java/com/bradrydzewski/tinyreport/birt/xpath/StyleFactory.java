package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.html.Style;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPathException;

/**
 *
 * @author Brad Rydzewski
 */
public class StyleFactory {

    private static final Map<String,String> STYLE_TO_CSS =
            new HashMap<String,String>();

    static {
        STYLE_TO_CSS.put("backgroundColor", "background-color");
    }


    public static Map<String, Style> getStyles(
            XMLDataObject xml) {

        Map<String, Style> styleMap = new HashMap<String, Style>();

        try {
            List<XMLDataObject> objects =
                    xml.getList("report/styles/style");

            for(XMLDataObject object : objects) {
                Style style = getStyle(object);
                styleMap.put(style.getName(), style);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return styleMap;
    }

    protected static Style getStyle(XMLDataObject xml)
            throws XPathException, IOException {

        Style style = new Style();
        style.setName(xml.getString("@name"));

        //Style string
        StringBuilder styleString = new StringBuilder();

        //Get the list of properties
        List<XMLDataObject> objects = xml.getList("property");

        //Loop through properties to get the style
        for(XMLDataObject object : objects) {

            String value = object.getTextContent();
            String name = object.getString("@name");

            if(STYLE_TO_CSS.containsKey(name))
                name = STYLE_TO_CSS.get(name);

            styleString.append(name).append(":").append(value);
        }

        //Set the css style string
        style.setValue(styleString.toString());

        return style;
    }
}
