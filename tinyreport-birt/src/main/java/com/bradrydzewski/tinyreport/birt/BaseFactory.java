package com.bradrydzewski.tinyreport.birt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.birt._2005.design.DesignElement.XmlProperty;
import org.eclipse.birt._2005.design.PropertyValueType;
import org.eclipse.birt._2005.design.TextPropertyType;

/**
 *
 * @author Brad Rydzewski
 */
public abstract class BaseFactory {

    protected static Map<String, String> getPropertyValueMap(List<Object> props) {

        Map<String, String> map = new HashMap<String, String>();
        String propName = null;
        String propValue = null;

        //iterate through each property
        for (Object prop : props) {

            if (prop instanceof PropertyValueType) {

                propName = ((PropertyValueType) prop).getName();
                propValue = ((PropertyValueType) prop).getValue();

            } else if (prop instanceof XmlProperty) {

                propName = ((XmlProperty) prop).getName();
                propValue = ((XmlProperty) prop).getValue();

            } else if (prop instanceof TextPropertyType) {
                propName = ((TextPropertyType) prop).getName();
                propValue = ((TextPropertyType) prop).getValue();
            }

            //add the property to the map
            map.put(propName, propValue);
        }

        return map;
    }
}
