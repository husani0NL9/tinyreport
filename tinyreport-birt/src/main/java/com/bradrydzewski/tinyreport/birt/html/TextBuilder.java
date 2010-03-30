package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.StyleFactory;
import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;
import com.bradrydzewski.tinyreport.html.Text;

/**
 *
 * @author brad
 */
public class TextBuilder extends ElementBuilder {

    @Override
    public Element getElement(XMLDataObject xml) {

        Text text = new Text();
        String val = xml.getString("text-property[@name='text']");

        String styleName = xml.getString("property[@name='style']");
        if(styleName!=null && !styleName.isEmpty())
            text.setStyleName(styleName);

        String styleString = StyleFactory.getCssString(xml);
        if(styleString!=null)
            text.setStyleString(styleString);

        if(xml.getName().equals("label")) {
            text.setValue(val, false);
        }
        return text;
    }

}
