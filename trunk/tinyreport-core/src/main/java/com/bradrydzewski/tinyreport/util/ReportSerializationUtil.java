package com.bradrydzewski.tinyreport.util;

import com.bradrydzewski.tinyreport.html.Style;
import com.bradrydzewski.tinyreport.model.DataColumn;
import com.bradrydzewski.tinyreport.model.JdbcConnection;
import com.bradrydzewski.tinyreport.model.JdbcQuery;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import com.bradrydzewski.tinyreport.model.ParameterDateTextBox;
import com.bradrydzewski.tinyreport.model.ParameterGroup;
import com.bradrydzewski.tinyreport.model.ParameterTextBox;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Utility methods to Serialize and De-Serialize reports to and from XML. 
 * All serialization is done using the XStream API.
 *
 * Alternate to XStream is the XMLEncoder class
 * http://java.sun.com/j2se/1.4.2/docs/api/java/beans/XMLEncoder.html
 * @author Brad Rydzewski
 */
public class ReportSerializationUtil {

    private static XStream xstream = null;
    
    static {
        xstream = new XStream();
        xstream.alias("report", ReportDefinition.class);
        xstream.aliasField("connections", ReportDefinition.class, "dataConnections");
        xstream.aliasField("queries", ReportDefinition.class, "dataQueries");
        xstream.alias("param-group", ParameterGroup.class);
        xstream.alias("param-textbox", ParameterTextBox.class);
        xstream.alias("param-datepicker", ParameterDateTextBox.class);
        xstream.alias("conn-jdbc", JdbcConnection.class);
        xstream.alias("query-jdbc", JdbcQuery.class);
        xstream.alias("column", DataColumn.class);
        xstream.alias("style", Style.class);

        xstream.alias("table",com.bradrydzewski.tinyreport.html.Table.class);
        xstream.alias("grid",com.bradrydzewski.tinyreport.html.Grid.class);
        xstream.alias("grid-row",com.bradrydzewski.tinyreport.html.GridRow.class);
        xstream.alias("grid-cell",com.bradrydzewski.tinyreport.html.GridCell.class);
        xstream.alias("text",com.bradrydzewski.tinyreport.html.Text.class);
        xstream.alias("data", com.bradrydzewski.tinyreport.html.DataElement.class);
        xstream.alias("image",com.bradrydzewski.tinyreport.html.Image.class);
    }


    /**
     * Serializes a {@link ReportDefinition} object to XML
     * @param reportDefinition ReportDefinition to serialize
     * @return a ReportDefinition object serialzied to an XML string
     */
    public static String toXMLString(ReportDefinition reportDefinition){
        
//        XStream xstream = new XStream();
//        xstream.alias("report", ReportDefinition.class);
//        xstream.aliasField("connections", ReportDefinition.class, "dataConnections");
//        xstream.aliasField("parameters", ReportDefinition.class, "reportParameters");
//        xstream.aliasField("queries", ReportDefinition.class, "dataQueries");
//        xstream.alias("param-textbox", ReportParameterTextBox.class);
//        xstream.alias("param-datepicker", ReportParameterDateTextBox.class);
//        xstream.alias("conn-jdbc", JdbcConnection.class);
//        xstream.alias("query-jdbc", JdbcQuery.class);
//        xstream.alias("column", DataColumn.class);
//        xstream.alias("style", Style.class);
        String xml = xstream.toXML(reportDefinition);
        return xml;
    }

    /**
     * De-Serializes a ReportDefinition object from an XML String
     * @param xmlString
     * @return
     */
    public static ReportDefinition fromXMLString(String xmlString){
        
        XStream xstream = new XStream();
        return (ReportDefinition)xstream.fromXML(xmlString);
    }
    
    /**
     * De-Serializes a ReportDefinition object from an XML File
     * @param xmlFile
     * @return
     */
    public static ReportDefinition fromXMLFile(File xmlFile){
        try{
            XStream xstream = new XStream();
            ReportDefinition rd =
                    (ReportDefinition)xstream.fromXML(new FileReader(xmlFile));
            
            
            
            return rd;
        }catch(FileNotFoundException ex){
            throw new RuntimeException(ex.getMessage());
        }
    } 

    /**
     * Serializes a ReportDefinition object from an XML File and
     * writes it to disk
     * @param to file to which the report defintion will be written
     * @param from report defintion object to be serialized
     */
    public static void toXMLFile(File to, ReportDefinition from){
        
        String xml = toXMLString(from);
        FileOutputStream stream = null;
        
        try{
            stream = new FileOutputStream(to);
            stream.write(xml.getBytes());
            
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }finally{
            //close the stream
            try{ stream.flush(); }catch(Exception ex){}
            try{ stream.close(); }catch(Exception ex){}
        }
    }     
}
