package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.html.Style;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
            XPath xpath, Document doc) {

        Map<String, Style> styleMap = new HashMap<String, Style>();

        try {
            XPathExpression expr = xpath.compile("report/styles/style");// +
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                Style style = getStyle(nodes.item(i), xpath);
                styleMap.put(style.getName(), style);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return styleMap;
    }

    protected static Style getStyle(Node node, XPath xpath)
            throws XPathException, IOException {

        Style style = new Style();
        style.setName(node.getAttributes().getNamedItem("name").getNodeValue());

        XPathExpression expr = xpath.compile("property");
        Object result = expr.evaluate(node, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

        //Style string
        StringBuilder styleString = new StringBuilder();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node propertyNode = nodes.item(i);
            String value = propertyNode.getTextContent();
            String name = propertyNode.getAttributes().getNamedItem("name").getTextContent();
            
            if(STYLE_TO_CSS.containsKey(name))
                name = STYLE_TO_CSS.get(name);

            styleString.append(name).append(":").append(value);
        }

        style.setValue(styleString.toString());

        return style;
    }
}
