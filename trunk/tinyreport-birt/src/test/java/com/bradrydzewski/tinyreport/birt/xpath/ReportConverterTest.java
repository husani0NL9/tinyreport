package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.model.ReportDefinition;
import com.bradrydzewski.tinyreport.util.ReportSerializationUtil;
import java.io.File;
import java.io.FileInputStream;
import org.junit.Test;

/**
 *
 * @author Brad
 */
public class ReportConverterTest {

    public ReportConverterTest() {
    }

    @Test
    public void testConvert_InputStream() {

        try {
            File rptdesign = new File("src\\test\\resources\\TopSellingProducts.rptdesign");
            ReportDefinition rd = ReportConverter.convert(new FileInputStream(rptdesign));

            String reportDefStr = ReportSerializationUtil.toXMLString(rd);
            System.out.println(reportDefStr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
