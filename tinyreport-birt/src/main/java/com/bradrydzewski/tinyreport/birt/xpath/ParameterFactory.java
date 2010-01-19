package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.model.ReportParameter;
import com.bradrydzewski.tinyreport.model.ReportParameterDateTextBox;
import com.bradrydzewski.tinyreport.model.ReportParameterTextBox;
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
 * @author brad
 */
public class ParameterFactory {

    private static final Map<String,String> DATE_FORMATS =
            new HashMap<String,String>();

    static {
        //DATE_FORMATS.put("backgroundColor", "background-color");
    }

    public static Map<String, ReportParameter> getReportParameters(
            XPath xpath, Document doc) {

        Map<String, ReportParameter> paramMap = new HashMap<String, ReportParameter>();

        try {
            XPathExpression expr = xpath.compile("//scalar-parameter");// +
            //"[@extensionID='org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet']");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                ReportParameter query = getReportParameter(nodes.item(i), xpath);
                paramMap.put(query.getName(), query);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return paramMap;
    }

    protected static ReportParameter getReportParameter(Node node, XPath xpath)
            throws XPathException, IOException {

        ReportParameter param = null;

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

    protected static ReportParameter getReportParameterDateTextBox(Node node, XPath xpath)
            throws XPathException, IOException {

        ReportParameterDateTextBox param = new ReportParameterDateTextBox();

        XPathExpression expr = xpath.compile("structure[@name='format']/property[@name='category']");
        String category = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("structure[@name='format']/property[@name='pattern']");
        String format = expr.evaluate(node, XPathConstants.STRING).toString();

        if (!category.toLowerCase().equals("custom")) {
            format = DATE_FORMATS.get(format);
            if(format==null)
                format="yyyy-dd-mm";
        }

        param.setDateFormat(format);

        return param;
    }

    protected static ReportParameter getReportParameterTextBox(Node node, XPath xpath)
            throws XPathException, IOException {

        ReportParameterTextBox param = new ReportParameterTextBox();
        return param;
    }
}
