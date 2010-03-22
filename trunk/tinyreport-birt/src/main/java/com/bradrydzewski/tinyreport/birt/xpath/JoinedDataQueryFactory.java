package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.model.JoinType;
import com.bradrydzewski.tinyreport.model.JoinedDataQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class JoinedDataQueryFactory {

    protected static Map<String,JoinType> JOIN_TYPES = new HashMap<String,JoinType>();

    static {
        JOIN_TYPES.put("left", JoinType.LEFT);
        JOIN_TYPES.put("left", JoinType.LEFT_OUTER);
        JOIN_TYPES.put("inner", JoinType.INNER);
    }

    public static List<JoinedDataQuery> getJoinedDataQueries(
            XPath xpath, Document doc) {

        List<JoinedDataQuery> list = new ArrayList<JoinedDataQuery>();

        try {
            XPathExpression expr = xpath.compile("report/data-sets/joint-data-set");// +
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                JoinedDataQuery query =
                        getJoinedDataQuery(nodes.item(i), xpath);
                list.add(query);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return list;
    }

    protected static JoinedDataQuery getJoinedDataQuery(Node node, XPath xpath)
            throws XPathException, IOException {

        JoinedDataQuery query = new JoinedDataQuery();

        query.setName(node.getAttributes().getNamedItem("name").getNodeValue());

        XPathExpression expr = xpath.compile("list-property[@name='joinConditions']/structure/property[@name='joinType']");
        String joinType = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("list-property[@name='joinConditions']/structure/property[@name='leftDataSet']");
        String leftDataSet = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("list-property[@name='joinConditions']/structure/property[@name='rightDataSet']");
        String rightDataSet = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("list-property[@name='joinConditions']/structure/expression[@name='rightExpression']");
        String leftColumn = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("list-property[@name='joinConditions']/structure/expression[@name='leftExpression']");
        String rightColumn = expr.evaluate(node, XPathConstants.STRING).toString();


        query.setJoinType(JOIN_TYPES.get(joinType));
        query.setDataQueryLeft(leftDataSet);
        query.setDataQueryRight(rightDataSet);
        query.setLeftColumn(leftColumn);
        query.setRightColumn(rightColumn);

        return query;
    }
}
