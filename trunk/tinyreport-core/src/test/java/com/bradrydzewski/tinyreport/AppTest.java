package com.bradrydzewski.tinyreport;

import java.util.Enumeration;
import org.apache.ecs.Doctype;
import org.apache.ecs.Document;
import org.apache.ecs.html.Body;
import org.apache.ecs.html.Head;
import org.apache.ecs.html.Html;
import org.apache.ecs.html.Script;
import org.apache.ecs.html.Style;
import org.apache.ecs.html.Title;
import org.apache.ecs.xhtml.div;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigourous Test :-)
     */
    @Test public void testApp()
    {

        //Create the html document
        Html html = new Html();
        html.setPrettyPrint(true);
        //Create the head tag
        Head head = new Head();
        //Add the style & script tags
        //Add the style tag
        Style styleElem = new Style();
        Script scriptElem = new Script();
        head.addElementToRegistry("style",styleElem);
        head.addElementToRegistry("script",scriptElem);

        Body b = new Body();
        b.addElement("adsfasdfas");
        b.addElement(new div("adsf"));

        //Add global styles
        styleElem.addElement(".1 {adsf:asdf;}");
        styleElem.addElement(".2 {adsf:asdf;}");
        styleElem.addElement(".3 {adsf:asdf;}");

        scriptElem.addElement("script1","poop1");
        scriptElem.addElement("script2","poop2");
        Enumeration e = scriptElem.keys();
        //while(e.hasMoreElements()) {
            //System.out.println(e.nextElement());
        //}

        Document doc = new Document();
        doc.setBody(b);
        doc.setHead(head);
        doc.setTitle(new Title("Test"));
        doc.setDoctype(new Doctype());
        //doc.setHtml(html);
        //System.out.println(doc);
        doc.output(System.out);
        System.out.println(doc.getHtml());
        //html.output(System.out);
        assertTrue( true );
    }
}
