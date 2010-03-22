package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.birt.html.ElementBuilder;
import com.bradrydzewski.tinyreport.birt.html.ElementBuilderFactory;
import com.bradrydzewski.tinyreport.html.Style;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;
import javax.xml.xpath.XPath;
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

            //Creating an XMLDataOjbect. This is based loosely on
            // the SDO framework, to encapulate all of the XPath logic.
            //This is not currently used in all the Factory classes,
            // but will be used going forward
            XMLDataObject xmlDataObject = XMLDataObject.createFromStream(input);

            //We set these values for factories not yet using the
            // XMLDataObject
            Document doc = (Document)xmlDataObject.getNode();
            XPath xpath = xmlDataObject.getXPath();

            //Get the data connections
            reportDefinition.setDataConnections(
                    DataConnectionFactory.getDataConnections(xpath, doc));

            //Get the data sets
            reportDefinition.setDataQueries(
                    DataQueryFactory.getDataQueries(xpath, doc));

            //Get the report parameters
            reportDefinition.setParameters(
                    ParameterFactory.getAllParameterGroups(xpath, doc));

            //Get the styles
            Map<String, Style> styles =
                    StyleFactory.getStyles(xmlDataObject);
            for(Style style : styles.values())
                reportDefinition.getPage().getStyles().add(style);

            
            XMLDataObject bodyObject =
                    xmlDataObject.getXMLDataObject("report/body");

            for(XMLDataObject elementObject : bodyObject.getChildNodes()) {
                ElementBuilder builder =
                        ElementBuilderFactory.get().getBuilder(elementObject);
                if(builder!=null) {
                    reportDefinition.getPage().addChildElement(
                            builder.getElement(elementObject));
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return reportDefinition;
    }
}
