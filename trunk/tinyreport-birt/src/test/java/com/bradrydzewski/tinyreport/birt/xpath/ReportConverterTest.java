package com.bradrydzewski.tinyreport.birt.xpath;

import com.bradrydzewski.tinyreport.model.ReportDefinition;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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
            File rptdesign = new File("src/test/resources/TopNPercent.rptdesign"); //TopSellingProducts.rptdesign
            ReportDefinition rd = ReportConverter.convert(new FileInputStream(rptdesign));

            //String reportDefStr = ReportSerializationUtil.toXMLString(rd);
            //System.out.println(reportDefStr);


            JAXBContext context = JAXBContext.newInstance(ReportDefinition.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            StringWriter sw = new StringWriter();
            marshaller.marshal(rd, sw); //save to StringWriter, you can then call sw.toString() to get java.lang.String
            System.out.println(sw.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
