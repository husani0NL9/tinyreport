package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.model.DataConnection;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Brad Rydzewski
 */
public class ReportConverter {

    public static ReportDefinition convert(File xmlFile) {
        return null;
    }

    public static ReportDefinition convert(String xmlString) {
        return convert(new ByteArrayInputStream(xmlString.getBytes()));
    }

    public static ReportDefinition convert(InputStream input) {

        //Create the ReportDefinition object
        ReportDefinition reportDefinition = new ReportDefinition();

        try {
            //http://www.ibm.com/developerworks/library/x-javaxpathapi.html
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
            
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            //Get the data connections
            reportDefinition.setDataConnections(
                    DataConnectionFactory.getDataConnections(xpath, doc));


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return reportDefinition;
    }
}
