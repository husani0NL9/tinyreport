package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.model.Parameter;
import com.bradrydzewski.tinyreport.model.ParameterDateTextBox;
import com.bradrydzewski.tinyreport.model.ParameterGroup;
import com.bradrydzewski.tinyreport.model.ParameterTextBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Brad Rydzewski
 */
public class ParameterFactory {

    private static final Map<String, String> DATE_FORMATS =
            new HashMap<String, String>();

    static {
        //we will use this in the future, but right now
        // we won't support formatting
        //DATE_FORMATS.put("simple", "yyyy-mm-dd");
    }

    public static List<ParameterGroup> getAllParameterGroups(
            XPath xpath, Document doc) {

        List<ParameterGroup> groups = new ArrayList<ParameterGroup>();

        try {

            //STEP 1: get the list of all scalar parameters and put in a default group
            ParameterGroup defaultGroup = new ParameterGroup();
            defaultGroup.setName("DEFAULT");
            XPathExpression expr = xpath.compile("report/parameters");
            Object result = expr.evaluate(doc, XPathConstants.NODE);
            defaultGroup.setParameters(getParameters(xpath, (Node)result));
            groups.add(defaultGroup);
            
            //STEP 2:
            groups.addAll(getParameterGroups(doc,xpath));
            
            //STEP 3:
            groups.addAll(getCascadingParameterGroups(doc,xpath));

        } catch (XPathExpressionException ex) {
            throw new RuntimeException(ex);
        }

        return groups;
    }

    public static List<ParameterGroup> getParameterGroups(Document doc, XPath xpath) {

        List<ParameterGroup> groups = new ArrayList<ParameterGroup>();

        try {
            XPathExpression expr = xpath.compile("report/parameters/parameter-group");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                
                ParameterGroup group = getParameterGroup(nodes.item(i), xpath);
                groups.add(group);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return groups;
    }

    public static ParameterGroup getParameterGroup(Node node, XPath xpath) {
        
        ParameterGroup group = new ParameterGroup();

        try {

            //get the control type to figure out what type of input element (textbox, listbox, etc) to use
            XPathExpression expr = xpath.compile("text-property[@name='displayName']");
            String name = expr.evaluate(node, XPathConstants.STRING).toString();

            //set the name
            group.setName(name);
            //set cascading false
            group.setCascading(false);

            expr = xpath.compile("parameters");
            Object result = expr.evaluate(node, XPathConstants.NODE);
            Node paramsNode = (Node) result;

            //get the report parameters
            group.setParameters(getParameters(xpath, paramsNode));

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        
        return group;
    }

    public static List<ParameterGroup> getCascadingParameterGroups(Document doc, XPath xpath) {
        List<ParameterGroup> groups = new ArrayList<ParameterGroup>();

        try {
            XPathExpression expr = xpath.compile("report/parameters/cascading-parameter-group");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {

                ParameterGroup group = getCascadingParameterGroup(nodes.item(i), xpath);
                groups.add(group);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return groups;
    }

    public static ParameterGroup getCascadingParameterGroup(Node node, XPath xpath) {
        ParameterGroup group = new ParameterGroup();

        try {

            //get the control type to figure out what type of input element (textbox, listbox, etc) to use
            XPathExpression expr = xpath.compile("text-property[@name='displayName']");
            String name = expr.evaluate(node, XPathConstants.STRING).toString();

            //set the name
            group.setName(name);
            //set cascading false
            group.setCascading(true);

            expr = xpath.compile("parameters");
            Object result = expr.evaluate(node, XPathConstants.NODE);
            Node paramsNode = (Node) result;

            //get the report parameters
            group.setParameters(getParameters(xpath, paramsNode));

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }



        return group;
    }

    public static List<Parameter> getParameters(
            XPath xpath,Node node) {

        List<Parameter> params = new ArrayList<Parameter>();

        try {
            XPathExpression expr = xpath.compile("scalar-parameter");
            Object result = expr.evaluate(node, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                Parameter query = getReportParameter(nodes.item(i), xpath);
                params.add(query);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return params;
    }

    protected static Parameter getReportParameter(Node node, XPath xpath)
            throws XPathException, IOException {

        Parameter param = null;

        //get the control type to figure out what type of input element (textbox, listbox, etc) to use
        XPathExpression expr = xpath.compile("property[@name='controlType']");
        String controlType = expr.evaluate(node, XPathConstants.STRING).toString();

        //get the type of data
        expr = xpath.compile("property[@name='dataType']");
        String dataType = expr.evaluate(node, XPathConstants.STRING).toString();

        if (dataType.equals("date")) {
            param = getReportParameterDateTextBox(node, xpath);
        } else {
            param = getReportParameterTextBox(node, xpath);
        }

        param.setName(node.getAttributes().getNamedItem("name").getNodeValue());

        expr = xpath.compile("property[@name='isRequired']");
        boolean required = expr.evaluate(node, XPathConstants.STRING).toString().equals("true");

        expr = xpath.compile("text-property[@name='promptText']");
        String promptText = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("text-property[@name='helpText']");
        String helpText = expr.evaluate(node, XPathConstants.STRING).toString();

        param.setRequired(required);
        param.setPrompt(promptText);
        param.setHelp(helpText);

        return param;
    }

    protected static Parameter getReportParameterDateTextBox(Node node, XPath xpath)
            throws XPathException, IOException {

        ParameterDateTextBox param = new ParameterDateTextBox();

        XPathExpression expr = xpath.compile("structure[@name='format']/property[@name='category']");
        String category = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("structure[@name='format']/property[@name='pattern']");
        String format = expr.evaluate(node, XPathConstants.STRING).toString();

        if (!category.toLowerCase().equals("custom")) {
            format = DATE_FORMATS.get(format);
            if (format == null) {
                format = "yyyy-dd-mm";
            }
        }

        param.setDateFormat(format);

        return param;
    }

    protected static Parameter getReportParameterTextBox(Node node, XPath xpath)
            throws XPathException, IOException {

        ParameterTextBox param = new ParameterTextBox();
        return param;
    }
}
