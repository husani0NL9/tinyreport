package com.bradrydzewski.tinyreport.birt.xpath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author brad
 */
public class PropertyFactory {

    public static Map<String,String> getProperties(
            List<XMLDataObject> structures) {

        if(structures==null)
            return null;

        Map<String,String> properties = new HashMap<String,String>();
        for(XMLDataObject structure : structures) {
            String key=structure.getString("property[@name='name']");
            String val=structure.getString("property[@name='data']");
            properties.put(key, val);
        }

        return properties;
    }
}
