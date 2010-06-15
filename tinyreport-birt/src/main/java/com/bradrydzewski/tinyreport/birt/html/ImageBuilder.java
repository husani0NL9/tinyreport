package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Element;
import com.bradrydzewski.tinyreport.html.Image;

/**
 *
 * @author Brad Rydzewski
 */
public class ImageBuilder extends ElementBuilder {

    protected static final String JPEG = "jpeg";
    protected static final String JPG = "jpg";
    protected static final String PNG = "png";
    protected static final String GIF = "gif";

    @Override
    public Element getElement(XMLDataObject xml) {
        Image image = new Image();
        image.setName(xml.getString("property[@name='imageName']"));
        image.setEmbedded(true);

        if(image.isEmbedded())
            image.setMimeType(getMimeType(image.getName()));

        return image;
    }

    protected static String getMimeType(String fileName) {

        String mimeType = null;

        if(assertJpeg(fileName))
            mimeType = JPEG;
        else if(assertGif(fileName))
            mimeType = GIF;
        else if(assertPng(fileName))
            mimeType = PNG;
        else throw new RuntimeException(
                "Unknown mimetype encountered for "+fileName);

        return mimeType;
    }

    protected static boolean assertJpeg(String fileName) {
        return fileName.toLowerCase().endsWith(JPG) ||
               fileName.toLowerCase().endsWith(JPEG);
    }

    protected static boolean assertPng(String fileName) {
        return fileName.toLowerCase().endsWith(PNG);
    }

    protected static boolean assertGif(String fileName) {
        return fileName.toLowerCase().endsWith(GIF);
    }
}
