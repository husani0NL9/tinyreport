package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.html.Style;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This class will extract css styles from a BIRT report.
 * Birt represents styles as a property element. The property
 * node has a "name" element whireport/styles/stylech is the style attribute being
 * defined (i.e. width, height, border, etc) and the nodes inner text
 * is the style attribute's value</p>
 *
 * <p>Styles can be defined as a group of style property elements
 * at a global report-level. here is an example XML snippet:
 * <pre>
 *   <style name="BackgroundLightGray" id="1">
 *     <property name="backgroundColor">#EAEAEA</property>
 *     <property name="fontFamilly">arial</property>
     </style>
 * </pre>
 * These styles can then be referenced
 * by UI elements defined in the report. This is basically
 * the equivalent of css styles defined in the head of your html
 * document.</p>
 *
 * <p>Style attribes can also be defined as children elements
 * of a UI elment. This is basially the equivalent of inine styles
 * in an html document. See the following XML snippet:
 * <pre>
 *   <label>
 *     <property name="borderBottomColor">#808080</property>
 *     <property name="borderBottomStyle">solid</property>
 *     <property name="borderBottomWidth">thin</property>
 *     <property name="paddingTop">2pt</property>
 *     <property name="paddingLeft">2pt</property>
 *   </label>
 * </pre></p>
 *
 * <p>Birt styles align very closely to css styles. The main difference
 * is Birt uses camel case when describing a style attribute (such as
 * borderWidth) where css separates by dashes (such as border-width).
 * This class will handle that mapping to convert from Birt to
 * standard css naming conventions.</p>
 *
 * @author Brad Rydzewski
 */
public class StyleFactory {

    /**
     * Map where key is the Birt style name and the value
     * is the valid css style name.
     */
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
        STYLE_TO_CSS.put("borderBottomColor", "border-bottom-color");
        STYLE_TO_CSS.put("borderTopColor", "border-top-color");
        STYLE_TO_CSS.put("borderLeftColor", "border-left-color");
        STYLE_TO_CSS.put("borderRightColor", "border-right-color");
        //Font
        STYLE_TO_CSS.put("fontFamily", "font-family");
        STYLE_TO_CSS.put("fontSize", "font-size");
        STYLE_TO_CSS.put("fontWeight", "font-weight");
        //Text
        STYLE_TO_CSS.put("textAlign","text-align");
        STYLE_TO_CSS.put("verticalAlign","vertical-align");

    }


    /**
     * Retrieves a Map of styles from a Birt report. The styles
     * are retrieved from report/styles/style, which is the global
     * definition of Birt styles.
     * @param xml Birt xml document.
     * @return Map of Style objects.
     */
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

    /**
     * Converts a Birt Style node in a tinyreport Style object. The
     * Style object will contain the name of the Birt style, and a
     * single CSS string containing all of the styles defined
     * in the property nodes.
     * @param xml Birt style node.
     * @return Style class containing the style name and css string.
     */
    protected static Style getStyle(XMLDataObject xml) {

        Style style = new Style();
        style.setName(xml.getString("@name"));
        style.setValue(getCssString(xml));

        return style;
    }

    /**
     * Given an xml node, this will find all child property elements.
     * Any elements that are Birt styles will be converted to css styles.
     * All css styles will be concatinated into a sinle css string.
     * @param Birt XML node.
     * @return Css string
     */
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
