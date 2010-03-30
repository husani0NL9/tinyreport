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

    private static final Map<String, String> STYLE_TO_CSS =
            new HashMap<String, String>();

    static {

        STYLE_TO_CSS.put("height", "height");
        STYLE_TO_CSS.put("width", "width");
        STYLE_TO_CSS.put("backgroundColor", "background-color");
        //Padding
        STYLE_TO_CSS.put("paddingRight", "padding-right");
        STYLE_TO_CSS.put("paddingBottom", "padding-bottom");
        STYLE_TO_CSS.put("paddingLeft", "padding-left");
        STYLE_TO_CSS.put("paddingTop", "padding-top");
        //Border Widths
        STYLE_TO_CSS.put("borderBottomWidth", "border-bottom-width");
        STYLE_TO_CSS.put("borderTopWidth", "border-top-width");
        STYLE_TO_CSS.put("borderLeftWidth", "border-left-width");
        STYLE_TO_CSS.put("borderRightWidth", "border-right-width");
        //Border Styles
        STYLE_TO_CSS.put("borderBottomStyle", "border-bottom-style");
        STYLE_TO_CSS.put("borderTopStyle", "border-top-style");
        STYLE_TO_CSS.put("borderLeftStyle", "border-left-style");
        STYLE_TO_CSS.put("borderRightStyle", "border-right-style");
        //Border Color
        STYLE_TO_CSS.put("borderBottomColor", "border-bottom-style");
        STYLE_TO_CSS.put("borderTopColor", "border-top-style");
        STYLE_TO_CSS.put("borderLeftColor", "border-left-style");
        STYLE_TO_CSS.put("borderRightColor", "border-right-style");
        //Font
        STYLE_TO_CSS.put("fontFamily", "font-family");
        STYLE_TO_CSS.put("fontSize", "font-size");

    }

    public static Map<String, Style> getStyles(
            XMLDataObject xml) {

        Map<String, Style> styleMap = new HashMap<String, Style>();

        try {
            List<XMLDataObject> objects =
                    xml.getList("report/styles/style");

            for (XMLDataObject object : objects) {
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
        style.setValue(getCssString(xml));

        return style;
    }

    public static String getCssString(XMLDataObject xml) {

        //Style string
        StringBuilder styleString = new StringBuilder();

        //Get the list of properties
        List<XMLDataObject> objects = xml.getList("property");

        //Loop through properties to get the style
        for (XMLDataObject object : objects) {

            String value = object.getTextContent();
            String name = object.getString("@name");

            if (STYLE_TO_CSS.containsKey(name)) {
                name = STYLE_TO_CSS.get(name);
                styleString.append(name).append(":").append(value).append(";");
            }
        }

        String css = styleString.toString();
        return (css.length() == 0) ? null : css;
    }
}
