package com.bradrydzewski.tinyreport.birt;

import com.bradrydzewski.tinyreport.model.ReportDefinition;
import com.bradrydzewski.tinyreport.util.ReportSerializationUtil;
import org.junit.Test;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.eclipse.birt._2005.design.Report;
import static org.junit.Assert.*;

/**
 *
 * @author Brad
 */
public class ReportConverterTest {

    @Test
    public void testConvert() {

        File rptDesignFile = new File("src\\test\\resources\\TopSellingProducts.rptdesign");
        ReportDefinition reportDefinition = null;

        try {

            JAXBContext jc = JAXBContext.newInstance(Report.class);
            Unmarshaller um = jc.createUnmarshaller();
            Report report = (Report) um.unmarshal(rptDesignFile);
            reportDefinition = ReportConverter.convert(report);

        } catch(Exception ex) {
            ex.printStackTrace();
            assertTrue(false);
        }

        String reportDefStr = ReportSerializationUtil
                .toXMLString(reportDefinition);
        System.out.println(reportDefStr);

        assertTrue(true);
    }

}