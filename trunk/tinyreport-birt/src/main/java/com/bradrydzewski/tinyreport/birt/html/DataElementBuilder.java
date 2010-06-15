package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.StyleFactory;
import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.DataElement;
import com.bradrydzewski.tinyreport.html.Element;

/**
 *
 * @author Brad Rydzewski
 */
public class DataElementBuilder extends ElementBuilder {

    private static final String COLUMN_XPATH = "property[@name='resultSetColumn']";
    private static final String SCRIPT_XPATH = "expression[@name='valueExpr']";
    private static final String STYLE_XPATH = "property[@name='style']";

    @Override
    public Element getElement(XMLDataObject xml) {

        DataElement dataElement = new DataElement();


        String columnName = xml.getString(COLUMN_XPATH);
        if(columnName!=null && !columnName.isEmpty())
            dataElement.setColumn(columnName);

        String styleName = xml.getString(STYLE_XPATH);
        if(styleName!=null && !styleName.isEmpty())
            dataElement.setStyleName(styleName);

        String script = xml.getString(SCRIPT_XPATH);
        if(script!=null && !script.isEmpty()) {
            String columnFromScript = getColumnFromScript(script);
            if(columnFromScript==null) {
                dataElement.setStyleName(script);
                dataElement.setDynamic(true);
            } else {
                dataElement.setColumn(columnFromScript);
                dataElement.setDynamic(false);
            }
        }

        String styleString = StyleFactory.getCssString(xml);
        if(styleString!=null)
            dataElement.setStyleString(styleString);


        return dataElement;
    }

    /**
     * Sometime BIRT will define a column in Javascript with no other
     * code, calculations, etc. In these cases we want to extract
     * the column name from the script. If no column is found
     * the method will return a null value.
     * 
     * Example 1: row["CUSTOMERNAME"] will return CUSTOMERNAME
     * Example 2: row["SALARY"] * .15 will return a null value because
     * it is not just a simple column reference and contains additional
     * scripting. 
     * 
     * @param script
     */
    public String getColumnFromScript(String script) {

        if(script==null || script.isEmpty())
            return null;

        String column = null;
        String[] tokens = script.split("\"");

        //if the script is a column reference, the following checks
        // will pass:
        // 1) there will only be 3 tokens
        // 2) the first token will equal <code>row["</code>
        // 3) the last token will equal <code>"]</code>
        if(tokens==null || tokens.length !=3 ||
                tokens[0].equals("row[")==false ||
                tokens[2].equals("]") == false)
            return null;

        //we'll mark upper case, although it should never be lower.
        column = tokens[1].toUpperCase();

        return column;
    }
    
}
