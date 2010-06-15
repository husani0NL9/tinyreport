package com.bradrydzewski.tinyreport.birt.html;

import com.bradrydzewski.tinyreport.birt.xpath.XMLDataObject;
import com.bradrydzewski.tinyreport.html.Image;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brad
 */
public class ImageBuilderTest {

    private static final String EMBEDDED_IMAGE_XML_SNIPPET = new StringBuilder()
            .append("<image id='15'>")
            .append("  <property name='borderBottomColor'>#808080</property>")
            .append("  <property name='borderBottomStyle'>solid</property>")
            .append("  <property name='borderBottomWidth'>thin</property>")
            .append("  <property name='source'>embed</property>")
            .append("  <property name='imageName'>C:\\Documents and Settings\\pclenahan\\My Documents\\ClassicModels\\logos\\Classic-Models-Full-M.jpg</property>")
            .append("</image>")
            .toString();

    private static final String IMAGE_NAME = "C:\\Documents and Settings\\pclenahan\\My Documents\\ClassicModels\\logos\\Classic-Models-Full-M.jpg";
    private static final String IMAGE_NODE_NAME = "image";
    private XMLDataObject embeddedImageDataObject = null;
    private XMLDataObject linkedImageDataObject = null;
    private ImageBuilder imageBuilder = null;

    @Before
    public void before() throws Exception {
        embeddedImageDataObject = XMLDataObject.createFromString(
                EMBEDDED_IMAGE_XML_SNIPPET).getXMLDataObject(IMAGE_NODE_NAME);
        imageBuilder = new ImageBuilder();
    }

    @Test
    public void testGetElement() {
        Image image = (Image) imageBuilder.getElement(embeddedImageDataObject);
        assertTrue(image.getMimeType().equals(ImageBuilder.JPEG));
        assertTrue(image.getName().equals(IMAGE_NAME));
        assertNull(image.getSrc());
    }

    @Test
    public void testGetMimeType() {
        assertTrue(ImageBuilder.getMimeType("C:/test.jpg").equals(ImageBuilder.JPEG));
        assertTrue(ImageBuilder.getMimeType("C:/test.png").equals(ImageBuilder.PNG));
        assertTrue(ImageBuilder.getMimeType("C:/test.gif").equals(ImageBuilder.GIF));
        try {
            ImageBuilder.getMimeType("c:/test.bmp");
            fail();
        } catch (RuntimeException ex) {

        }
    }

    @Test
    public void testAssertJpeg() {
        assertTrue(ImageBuilder.assertJpeg("c:/testImage.jpeg"));
        assertTrue(ImageBuilder.assertJpeg("c:/testImage.jpg"));
        assertTrue(ImageBuilder.assertJpeg("c:/testImage.jpEg"));
        assertFalse(ImageBuilder.assertJpeg("c:/testImage.png"));
    }

    @Test
    public void testAssertGif() {
        assertTrue(ImageBuilder.assertGif("c:/testImage.gif"));
        assertTrue(ImageBuilder.assertGif("c:/testImage.GIF"));
        assertFalse(ImageBuilder.assertGif("c:/testImage.png"));
    }

    @Test
    public void testAssertPng() {
        assertTrue(ImageBuilder.assertPng("c:/testImage.png"));
        assertTrue(ImageBuilder.assertPng("c:/testImage.PnG"));
        assertFalse(ImageBuilder.assertPng("c:/testImage.jpg"));
    }
}