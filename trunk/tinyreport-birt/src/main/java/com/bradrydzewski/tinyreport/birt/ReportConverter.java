package com.bradrydzewski.tinyreport.birt;

import com.bradrydzewski.tinyreport.model.DataConnection;
import com.bradrydzewski.tinyreport.model.DataQuery;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import com.bradrydzewski.tinyreport.model.ReportParameter;
import java.util.Map;
import org.eclipse.birt._2005.design.DataSetType;
import org.eclipse.birt._2005.design.DataSourceType;
import org.eclipse.birt._2005.design.Report;
import org.eclipse.birt._2005.design.ReportElementType;

/**
 *
 * @author Brad Rydzewski
 */
public class ReportConverter extends BaseFactory {

    public static ReportDefinition convert(Report report) {

        ReportDefinition reportDefinition = new ReportDefinition();

        //get report properties (ie title, description, author)
        Map<String,String> props =getPropertyValueMap(
                report.getPropertyOrExpressionOrXmlProperty());
        //set report parameters
        reportDefinition.setAuthor(props.get("author"));
        reportDefinition.setName(props.get("title"));
        reportDefinition.setCreatedBy(props.get("createdBy"));

        //DataConnections
        for (DataSourceType ds : report.getDataSources()
                .getOdaDataSourceOrScriptDataSource()) {

            DataConnection conn = DataConnectionFactory
                    .createDataConnection(ds, reportDefinition);
            if(conn!=null)
                reportDefinition.getDataConnections().put(conn.getName(), conn);
        }

        //DataQueries
        for (DataSetType ds : report.getDataSets()
                .getOdaDataSetOrScriptDataSetOrJointDataSet()) {

            DataQuery query = DataQueryFactory
                    .createDataConnection(ds, reportDefinition);
            if(query!=null)
                reportDefinition.getDataQueries().put(query.getName(), query);
        }

        //Parameters
        for (ReportElementType elem : report.getParameters()
                .getParameterGroupOrScalarParameterOrFilterParameter()) {

            ReportParameter param = ParameterFactory
                    .createParameter(elem, reportDefinition);

            reportDefinition.getReportParameters().put(param.getName(), param);
        }

        return reportDefinition;
    }
}
