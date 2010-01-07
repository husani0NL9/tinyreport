package com.bradrydzewski.tinyreport.birt;

import com.bradrydzewski.tinyreport.model.DataConnection;
import com.bradrydzewski.tinyreport.model.JdbcConnection;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import java.io.IOException;
import org.eclipse.birt._2005.design.DataSourceType;
import org.eclipse.birt._2005.design.EncryptedPropertyType;
import org.eclipse.birt._2005.design.PropertyValueType;

/**
 * Maps a DataConnection object in tinyreport to a DataSourceType in Birt.
 * @author Brad Rydzewski
 */
public class DataConnectionFactory {

    public static DataConnection createDataConnection(DataSourceType ds,
            ReportDefinition reportDefinition) {

        JdbcConnection conn = new JdbcConnection();
        conn.setName(ds.getName());

        for (Object prop : ds.getPropertyOrExpressionOrXmlProperty()) {

            if (prop instanceof PropertyValueType) {

                String propName = ((PropertyValueType)prop).getName();
                String propValue = ((PropertyValueType)prop).getValue();
                
                if(propName.equals("odaDriverClass")) {
                    conn.setDriverClass(propValue);
                } else if(propName.equals("odaURL")) {
                    conn.setDatabaseUrl(propValue);
                } else if(propName.equals("odaUser")) {
                    conn.setDatabaseUser(propValue);
                } else if(propName.equals("odaJndiName")) {
                    conn.setDatabaseJndiName(propValue);
                } else {
                    System.out.println("Unable to map property ["+propName+
                            "] with value ["+propValue+"] to the " +
                            "JdbcConnection object");
                }
            } else if (prop instanceof EncryptedPropertyType) {
                //the only encrypted property is the database password
                //for now, we will use an unencrypted password
                //why? well, it's not like base64 is a secure encryption anyway!
                String pw = ((EncryptedPropertyType)prop).getValue();

                try {
                    pw = new sun.misc.BASE64Decoder()
                            .decodeBuffer(pw).toString();
                } catch(IOException ex) {
                    //TODO: Use Log4J for Base64 decoding errors
                    System.out.println("Unable to 'decrypt' the password " +
                            "for DataSource name "+conn.getName());
                }
            }
        }

        return conn;
    }
}
