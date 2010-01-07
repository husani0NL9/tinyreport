package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;
import java.util.ArrayList;
import java.util.List;
import org.apache.ecs.Document;
import org.apache.ecs.html.Body;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Title;
import org.apache.ecs.xhtml.script;
import org.apache.ecs.xhtml.style;

/**
 * Represents an HTML document, with head, script, style, and body tags. 
 * Contains one or many child elements, as specified in the ReportDefinition
 * @author Brad Rydzewski
 */
public class Page {

    private List<Style> styles = new ArrayList<Style>();
    private List<Element> childElements = new ArrayList<Element>();

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

    public Page addStyle(Style style) {
        if (getStyles() == null) {
            setStyles(new ArrayList<Style>());
        }
        getStyles().add(style);
        return this;
    }

    public List<Element> getChildElements() {
        return childElements;
    }

    public void setChildElements(List<Element> childElements) {
        this.childElements = childElements;
    }

    public void addChildElements(Element... elements) {
        if (getChildElements() == null) {
            setChildElements(new ArrayList<Element>());
        }
        for (Element element : elements) {
            getChildElements().add(element);
        }
    }

    public Page addChildElement(Element element) {
        if (getChildElements() == null) {
            setChildElements(new ArrayList<Element>());
        }
        getChildElements().add(element);
        return this;
    }

    public void build(ReportBuilderArgs args) {

        //build the html document, add head, body, title, etc
        Document doc = args.getDocument();
        doc.setBody(new Body());
        doc.setHead(new Head());
        doc.setTitle(new Title(args.getReportDefinition().getName()));

        //Turn on "Pretty Print" feature for nice HTML code
        doc.getHtml().setPrettyPrint(true);
        doc.getHead().setPrettyPrint(true);
        doc.getBody().setPrettyPrint(true);
        doc.getTitle().setPrettyPrint(true);
        
        //Add the style tag
        style styleElem = new style();
        script scriptElem = new script();
        styleElem.setPrettyPrint(true);
        scriptElem.setPrettyPrint(true);
        doc.getHead().addElementToRegistry("style",styleElem);
        doc.getHead().addElementToRegistry("script",scriptElem);

        //generate string of css
        StringBuilder styleStringBuidler = new StringBuilder();

        for(Style style : styles) {
            styleStringBuidler
                    .append(" .").append(style.getName()).append("{")
                    .append(style.getValue()).append("} ");
        }

        //Set the styles inner tag value to the generated css string
        styleElem.addElement(styleStringBuidler.toString());

        //Render child elements
        for (Element element : getChildElements()) {
            element.build(doc.getBody(), args);
        }






//        StringBuilder document = new StringBuilder();
//
//        //if we are rendering as div, we don't create the html / head tags
//        if (!isRenderAsDiv()) {
//            document.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
//            document.append("<html>");
//            document.append("<head>");
//        }
//
//        if (getStyles() != null) {
//            document.append("<style> ");
//            for (Style style : getStyles()) {
//                document.append(".");
//                document.append(style.getName());
//                document.append(" {");
//                document.append(style.toCSS());
//                document.append(" } ");
//            }
//            document.append("</style>");
//        }
//
//
//        //render scripts that need to be imported, using <script/>
//        String[] scriptFiles = new String[args.getScriptFiles().size()];
//        args.getScriptFiles().toArray(scriptFiles);
//        for (int i = 0; i < scriptFiles.length; i++) {
//            document.append(" <script src='");
//            document.append(scriptFiles[i]);
//            document.append("'></script>");
//        }
//        //render modules (good visualization) that need to be imported, using google.load
//        String[] modules = new String[args.getGoogleModules().size()];
//        args.getGoogleModules().toArray(modules);
//
//        //if there are moduels to load, we'll load them and add js init()
//        if (modules.length > 0) {
//            document.append(" <script>\n");
//            document.append("  google.setOnLoadCallback(init);\n");
//            document.append("  google.load('visualization', '1', {'packages':[");
//            for (int i = 0; i < modules.length; i++) {
//                document.append("'").append(modules[i]).append("'");
//                if (i < modules.length - 1) {
//                    document.append(",");
//                }
//            }
//            document.append("]});\n");
//        } else {
//            document.append("<script>");
//        }
//
//        document.append(" function init(){ ");
//        document.append(args.getPreScript());
//        document.append(" } ");
//        document.append("</script>");
//
//        if (!isRenderAsDiv()) {
//            document.append("</head>");
//            document.append("<body>");
//        } else {
//            document.append("<div id='g2-report-body'>");
//        }
//
//        document.append(args.getHtml());
//
//        if (!isRenderAsDiv()) {
//            document.append("</body>");
//        } else {
//            document.append("</div>");
//        }
//
//        document.append("<script>");
//        document.append(args.getPostScript());
//        document.append("</script>");
//
//        if (!isRenderAsDiv()) {
//            document.append("</html>");
//        }
    }
}
