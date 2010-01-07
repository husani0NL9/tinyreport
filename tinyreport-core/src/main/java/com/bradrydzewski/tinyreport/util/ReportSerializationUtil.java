package com.bradrydzewski.tinyreport.util;

import com.bradrydzewski.tinyreport.model.ReportDefinition;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Utility methods to Serialize and De-Serialize reports to and from XML. 
 * All serialization is done using the XStream API.
 * @author Brad Rydzewski
 */
public class ReportSerializationUtil {

    
    /**
     * Serializes a {@link ReportDefinition} object to XML
     * @param reportDefinition ReportDefinition to serialize
     * @return a ReportDefinition object serialzied to an XML string
     */
    public static String toXMLString(ReportDefinition reportDefinition){
        
        XStream xstream = new XStream();
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
