package com.bradrydzewski.tinyreport;

import com.bradrydzewski.tinyreport.jdbc.DataSet;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import java.util.HashMap;
import java.util.Map;
import org.apache.ecs.Document;

/**
 *
 * @author Brad Rydzewski
 */
public class ReportBuilder {

    public Document build(ReportDefinition reportDefinition) {

        return build(reportDefinition, new HashMap<String, DataSet>());
    }

    protected Document build(ReportDefinition reportDefinition,
            Map<String, DataSet> results) {

        ReportBuilderArgs args = new ReportBuilderArgs(reportDefinition,
            results, null /*script engine*/, new Document());

        reportDefinition.getPage().build(args);

        return args.getDocument();
    }
}
