package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;

/**
 *
 * @author brad
 */
public abstract class ElementBuilder {

    public abstract Element getElement(XMLDataObject xml);
}
