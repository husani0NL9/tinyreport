package com.bradrydzewski.tinyreport.birt;

import com.bradrydzewski.tinyreport.model.DataConnection;
import com.bradrydzewski.tinyreport.model.DataQuery;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import org.eclipse.birt._2005.design.DataSetType;
import org.eclipse.birt._2005.design.DataSourceType;
import org.eclipse.birt._2005.design.Report;

/**
 *
 * @author Brad Rydzewski
 */
public class ReportConverter {

    public static ReportDefinition convert(Report report) {

        ReportDefinition reportDefinition = new ReportDefinition();

        //get report properties (ie title, description, author)
        for (Object props : report.getPropertyOrExpressionOrXmlProperty()) {
            //printProperties(o, "");
        }

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

        return reportDefinition;
    }
}
