package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.model.DataConnection;
import com.bradrydzewski.tinyreport.model.JdbcConnection;
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
 * Maps a DataConnection object in tinyreport to a DataSourceType in Birt.
 * 
 * <data-sources>
 *   <oda-data-source extensionID="org.eclipse.birt.report.data.oda.jdbc" name="Classic Models" id="5">
 *     <property name="odaDriverClass">org.apache.derby.jdbc.ClientDriver</property>
 *     <property name="odaURL">jdbc:derby://localhost:1527/classicmodels</property>
 *     <property name="odaUser">userName</property>
 *     <encrypted-property name="odaPassword" encryptionID="base64">dGVzdA==</encrypted-property>
 *   </oda-data-source>
 * </data-sources>
 *
 * @author Brad Rydzewski
 */
public class DataConnectionFactory {

    public static Map<String, DataConnection> getDataConnections(
            XPath xpath, Document doc) {

        Map<String, DataConnection> connMap = new HashMap<String, DataConnection>();

        try {
            XPathExpression expr = xpath.compile("report/data-sources/oda-data-source");// +
                    //"[@extensionID='org.apache.derby.jdbc.ClientDriver']");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                DataConnection conn = getJdbcConnection(nodes.item(i), xpath);
                connMap.put(conn.getName(), conn);
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return connMap;
    }

    protected static JdbcConnection getJdbcConnection(Node node, XPath xpath)
            throws XPathException, IOException {

        JdbcConnection conn = new JdbcConnection();
        conn.setName(node.getAttributes().getNamedItem("name").getNodeValue());

        XPathExpression expr = xpath.compile("property[@name='odaDriverClass']");
        String driverClass = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("property[@name='odaUser']");
        String user = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("encrypted-property[@name='odaPassword']");
        String password = expr.evaluate(node, XPathConstants.STRING).toString();
        password = new String(new sun.misc.BASE64Decoder().decodeBuffer(password));

        expr = xpath.compile("property[@name='odaURL']");
        String url = expr.evaluate(node, XPathConstants.STRING).toString();

        expr = xpath.compile("property[@name='odaJndiName']");
        String jndi = expr.evaluate(node, XPathConstants.STRING).toString();

        conn.setDatabaseUser(user);
        conn.setDatabasePassword(password);
        conn.setDatabaseUrl(url);
        conn.setDriverClass(driverClass);
        conn.setDatabaseJndiName(jndi);

        return conn;
    }
}