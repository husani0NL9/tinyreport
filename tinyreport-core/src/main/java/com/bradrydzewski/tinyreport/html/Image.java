package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;
import org.apache.ecs.xhtml.img;

/**
 * Represents an Image element, corresponding to the {@code <image>} tag
 * @author Brad Rydzewski
 */
public class Image extends Element {

    private String src;
    private String alt;
    private boolean embedded = false;
    private String mimeType;

    public Image() {
    }

    public Image(String src) {
        this.src = src;
    }

    public Image(String src, String style) {
        this.src = src;
        this.setStyleName(style);
    }

    public Image(String src, String mimeType, String style) {
        this.src = src;
        this.mimeType = mimeType;
        this.setStyleName(style);
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return (alt == null) ? "" : alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isEmbedded() {
        return embedded;
    }

    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }


    @Override
    public void build(org.apache.ecs.Element parent, ReportBuilderArgs args) {

        //create the image element
        img img = new img();
        img.setPrettyPrint(true);
        
        if(alt!=null)
            img.setAlt(alt);

        //add the style
        if(styleName!=null)
            img.setClass(styleName);

        //if an embedded base64 image, add as source including mimetype
        if(embedded)
            img.setSrc("data:image/" + getMimeType() + ";base64," + src);
        //else add the url
        else
            img.setSrc(src);

        //add the image to the parent directory
        parent.addElementToRegistry(img);
    }
}
