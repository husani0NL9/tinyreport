package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.model.DataColumn;
import com.bradrydzewski.tinyreport.model.DataQuery;
import com.bradrydzewski.tinyreport.model.DataType;
import com.bradrydzewski.tinyreport.model.JdbcQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class DataQueryFactory {

    public static List<DataQuery> getDataQueries(
            XPath xpath, Document doc) {

        List<DataQuery> queries = new ArrayList<DataQuery>();

        try {
            XPathExpression expr = xpath.compile("report/data-sets/oda-data-set");// +
            //"[@extensionID='org.eclipse.birt.report.data.oda.jdbc.JdbcSelectDataSet']");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                DataQuery query = getJdbcQuery(nodes.item(i), xpath);
                queries.add(query);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return queries;
    }

    protected static JdbcQuery getJdbcQuery(Node node, XPath xpath)
            throws XPathException, IOException {

        JdbcQuery query = new JdbcQuery();

        query.setName(node.getAttributes().getNamedItem("name").getNodeValue());

        XPathExpression expr = xpath.compile("property[@name='dataSource']");
        String dataSource = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("xml-property[@name='queryText']");
        String queryText = expr.evaluate(node, XPathConstants.STRING).toString();

        
        query.setDataConnection(dataSource);
        query.setSqlQuery(queryText);

        //get the data columns
        query.setColumns(getDataColumns(node, xpath));

        return query;
    }

    protected static List<DataColumn> getDataColumns(Node node, XPath xpath)
            throws XPathException, IOException {

        

        List<DataColumn> columns = new ArrayList<DataColumn>();

        XPathExpression expr = xpath.compile("structure[@name='cachedMetaData']/list-property[@name='resultSet']/structure");
        Object result = expr.evaluate(node, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                DataColumn col = getDataColumn(nodes.item(i), xpath);
                columns.add(col);
            }

        return columns;
    }

    protected static DataColumn getDataColumn(Node node, XPath xpath)
            throws XPathException, IOException {

        DataColumn col = new DataColumn();

        XPathExpression expr = xpath.compile("property[@name='position']");
        String position = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("property[@name='name']");
        String name = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("property[@name='dataType']");
        String dataType = expr.evaluate(node, XPathConstants.STRING).toString();


        col.setName(name);
        col.setType(DataType.valueOf(dataType.toUpperCase()));
        col.setOrder(Integer.valueOf(position));
        return col;
    }
}
