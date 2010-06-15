package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Text;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Brad Rydzewski
 */
public class TextBuilderTest {

    private static final String TEXT_XML_SNIPPET = new StringBuilder()
            .append("<text id='18'>")
            .append("  <property name='fontFamily'>sans-serif</property>")
            .append("  <property name='contentType'>html</property>")
            .append("  <text-property name='content'><![CDATA[701 Gateway Boulevard,<BR>San Francisco, CA 94107<BR><BR>]]></text-property>")
            .append("</text>").toString();
    private static final String LABEL_XML_SNIPPET = new StringBuilder()
            .append("<label id='18'>")
            .append("  <property name='fontFamily'>sans-serif</property>")
            .append("  <property name='fontSize'>20pt</property>")
            .append("  <property name='fontWeight'>bold</property>")
            .append("  <property name='color'>#000080</property>")
            .append("  <text-property name='text'>Classic Models, Inc.</text-property>")
            .append("</label>").toString();

    private XMLDataObject textDataObject = null;
    private XMLDataObject labelDataObject = null;
    private TextBuilder textBuilder = null;

    @Before
    public void before() throws Exception {
        textDataObject = XMLDataObject.createFromString(TEXT_XML_SNIPPET).getXMLDataObject("text");
        labelDataObject = XMLDataObject.createFromString(LABEL_XML_SNIPPET).getXMLDataObject("label");
        textBuilder = new TextBuilder();
    }

    @Test
    public void testGetElement_Text() {
        Text text = (Text)textBuilder.getElement(textDataObject);
        assertTrue("The TextBuilder should correctly parse the elements inner text","701 Gateway Boulevard,<BR>San Francisco, CA 94107<BR><BR>".equals(text.getValue()));
    }

    @Test
    public void testGetElement_Label() {
        Text text = (Text)textBuilder.getElement(labelDataObject);
        assertTrue("The TextBuilder should correctly parse the label element's inner text","Classic Models, Inc.".equals(text.getValue()));
    }

    /**
     * In Birt, the Label and Text xml elements are virtually the same.
     * The primary difference is that the elements "inner html" is
     * stored in text-element nodes with name attributes of "text"
     * and "content", respectively. This test will ensure we get
     * the correct xpath depending on the element type.
     */
    @Test
    public void testGetContentXPath() {
        String xpath = textBuilder.getContentXPath(TextBuilder.LABEL);
        assertTrue(xpath.equals(TextBuilder.LABEL_CONTENT_XPATH));
        xpath = textBuilder.getContentXPath(TextBuilder.TEXT);
        assertTrue(xpath.equals(TextBuilder.TEXT_CONTENT_XPATH));
    }
}