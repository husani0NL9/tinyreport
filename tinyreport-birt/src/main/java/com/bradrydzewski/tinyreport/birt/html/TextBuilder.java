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

    protected static final String LABEL = "label";
    protected static final String TEXT = "text";
    protected static final String LABEL_CONTENT_XPATH = "text-property[@name='text']";
    protected static final String TEXT_CONTENT_XPATH = "text-property[@name='content']";
    protected static final String STYLE_XPATH = "property[@name='style']";

    @Override
    public Element getElement(XMLDataObject xml) {

        Text text = new Text();
        String val = xml.getString(getContentXPath(xml.getName()));
        text.setValue(val, false);

        String styleName = xml.getString(STYLE_XPATH);
        if(styleName!=null && !styleName.isEmpty())
            text.setStyleName(styleName);

        String styleString = StyleFactory.getCssString(xml);
        if(styleString!=null)
            text.setStyleString(styleString);

        return text;
    }

    public String getContentXPath(String nodeName) {
        String xpath = null;
        if(LABEL.equals(nodeName)) {
            xpath = LABEL_CONTENT_XPATH;
        } else {
            xpath = TEXT_CONTENT_XPATH;
        }
        return xpath;
    }
}
