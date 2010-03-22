package com.bradrydzewski.tinyreport.birt.xpath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author brad
 */
public class XMLDataObject {

    public static XMLDataObject createFromString(String xml) throws Exception {

        return createFromStream(new ByteArrayInputStream(xml.getBytes()));
    }

    public static XMLDataObject createFromStream(InputStream input)
            throws Exception {

        //convert string to an xml document
        Node node = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);

        //create an xpath instance
        XPath xpath = XPathFactory.newInstance().newXPath();

        XMLDataObject xmlDataObject = new XMLDataObject(xpath, node);

        return xmlDataObject;
    }
    private XPath xpath = null;
    private Node node = null;

    public Node getNode() {
        return node;
    }

    public XPath getXPath() {
        return xpath;
    }

    private XMLDataObject(XPath xpath, Node node) {
        this.xpath = xpath;
        this.node = node;
    }

    //TODO: Add Date parser
    public Date getDate(String path, String format) {

        return null;
    }

    public String getString(String path) {
        try {
            XPathExpression expr = xpath.compile(path);
            return expr.evaluate(node, XPathConstants.STRING).toString();
        } catch (XPathExpressionException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int getInteger(String path) throws XPathExpressionException {
        return Integer.parseInt(getString(path));
    }

    public double getDouble(String path) throws XPathExpressionException {
        return Double.parseDouble(getString(path));
    }

    public float getFloat(String path) throws XPathExpressionException {
        return Float.parseFloat(getString(path));
    }

    public boolean getBoolean(String path) {
        return Boolean.parseBoolean(getString(path));
    }

    public List<XMLDataObject> getList(String path) {

        List<XMLDataObject> dataObjectList =
                new ArrayList<XMLDataObject>();

        try {

            XPathExpression expr = xpath.compile(path);
            Object result = expr.evaluate(node, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                XMLDataObject childDataObject = new XMLDataObject(xpath, nodes.item(i));
                dataObjectList.add(childDataObject);
            }
        } catch (XPathExpressionException ex) {
            throw new RuntimeException(ex);
        }

        return dataObjectList;
    }

    public String getName() {
        return node.getNodeName();
    }

    public String getTextContent() {
        return node.getTextContent();
    }

    public String getValue() {
        return node.getNodeValue();
    }

    public List<XMLDataObject> getChildNodes() {

        List<XMLDataObject> dataObjectList =
                new ArrayList<XMLDataObject>();

        NodeList nodes = (NodeList) node.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            XMLDataObject childDataObject =
                    new XMLDataObject(xpath, nodes.item(i));
            dataObjectList.add(childDataObject);
        }

        return dataObjectList;
    }
}
