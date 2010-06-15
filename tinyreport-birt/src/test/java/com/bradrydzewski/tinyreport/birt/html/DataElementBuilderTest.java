package com.bradrydzewski.tinyreport.birt.html;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brad
 */
public class DataElementBuilderTest {

    public DataElementBuilderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetElement() {
    }

    @Test
    public void testGetColumnFromScript() {
        DataElementBuilder builder = new DataElementBuilder();
        String col = builder.getColumnFromScript("row[\"TESTCOLUMN\"]");
        assertTrue("Extract TESTCOLUMN from row['TESTCOLUMN']",col.equals("TESTCOLUMN"));

        col = builder.getColumnFromScript("row[\"TESTCOLUMN\"] + 1");
        assertNull("Stript has column reference AND code",col);

        col = builder.getColumnFromScript("\"HELLO \" + row[\"TESTCOLUMN\"]");
        assertNull("Stript has column reference AND code",col);
        
        col = builder.getColumnFromScript(null);
        assertNull("Cannot convert null script to column",col);

        col = builder.getColumnFromScript("");
        assertNull("Cannot convert empty script to column",col);

        col = builder.getColumnFromScript("10 * 10");
        assertNull("Script has no column reference",col);
    }

}