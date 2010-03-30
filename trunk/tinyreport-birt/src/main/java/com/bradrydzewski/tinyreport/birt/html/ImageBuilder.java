package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;
import com.bradrydzewski.tinyreport.html.Image;

/**
 *
 * @author Brad Rydzewski
 */
public class ImageBuilder extends ElementBuilder {

    @Override
    public Element getElement(XMLDataObject xml) {
        Image image = new Image();
        image.setName(xml.getString("property[@name='imageName']"));
        image.setEmbedded(true);
        image.setMimeType("jpg");
        return image;
    }

}
