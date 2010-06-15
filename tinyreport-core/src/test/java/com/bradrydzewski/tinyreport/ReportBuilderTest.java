package com.bradrydzewski.tinyreport;

import com.bradrydzewski.tinyreport.html.DataElement;
import com.bradrydzewski.tinyreport.html.DataGroup;
import com.bradrydzewski.tinyreport.html.Grid;
import com.bradrydzewski.tinyreport.html.GridCell;
import com.bradrydzewski.tinyreport.html.GridRow;
import com.bradrydzewski.tinyreport.html.Image;
import com.bradrydzewski.tinyreport.html.Page;
import com.bradrydzewski.tinyreport.html.Table;
import com.bradrydzewski.tinyreport.html.Text;
import com.bradrydzewski.tinyreport.jdbc.Column;
import com.bradrydzewski.tinyreport.jdbc.DataSet;
import com.bradrydzewski.tinyreport.model.DataColumn;
import com.bradrydzewski.tinyreport.model.DataType;
import com.bradrydzewski.tinyreport.model.JdbcQuery;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ecs.Document;
import org.junit.Test;

/**
 *
 * @author Brad
 */
public class ReportBuilderTest {

    @Test
    public void testBuild() {

        ReportDefinition rd = new ReportDefinition();
        rd.setName("Test Report");
        rd.setPage(new Page());
        rd.getPage().addChildElement(new Image("http://www.foo.com/image.jpg"));
        Grid g = new Grid();
        g.addRow().addCells(
                new GridCell(new Image("http://www.foo.com/image1.jpg")),
                new GridCell(new Image("http://www.foo.com/image2.jpg")));
        g.addRow().addCells(
                new GridCell(new Image("http://www.foo.com/image3.jpg")),
                new GridCell(new Image("http://www.foo.com/image4.jpg")));
        rd.getPage().addChildElement(g);

        
        DataColumn dc1 = new DataColumn("id",0,DataType.INTEGER);
        DataColumn dc2 = new DataColumn("name",1,DataType.STRING);
        DataColumn dc3 = new DataColumn("hourly rate",2,DataType.INTEGER);
        DataColumn dc4 = new DataColumn("state",4,DataType.STRING);


        List rows = new ArrayList();
        rows.add(new Object[]{1,"Mike",50,"PA"});
        rows.add(new Object[]{2,"John",75,"CT"});
        rows.add(new Object[]{3,"Bill",99,"PA"});
        rows.add(new Object[]{4,"Ron",99,"AZ"});
        List columns = new ArrayList();
        columns.add(new Column("id",0,java.sql.Types.INTEGER));
        columns.add(new Column("name",1,java.sql.Types.VARCHAR));
        columns.add(new Column("hourly rate",2,java.sql.Types.INTEGER));
        columns.add(new Column("state",3,java.sql.Types.VARCHAR));
        DataSet ds = new DataSet(columns,rows);
        Map dsTbl = new HashMap<String, DataSet>();
        dsTbl.put("ds1", ds);

        Table table = new Table();
        JdbcQuery dq = new JdbcQuery();
        dq.getColumns().add(dc1);
        dq.getColumns().add(dc2);
        dq.getColumns().add(dc3);
        dq.getColumns().add(dc4);
        dq.setName("ds1");
        table.setDataQuery(dq.getName());
        table.getHeaderRows().add(new GridRow().addCells(new GridCell(new Text("Id",false)), new GridCell(new Text("Name",false))) );
        table.getRows().add(new GridRow().addCells(new GridCell(new DataElement(dc1.getName(),dc1.getType())), new GridCell(new DataElement(dc2.getName(),dc2.getType()))));
        rd.getPage().addChildElement(table);

        DataGroup dg = new DataGroup();
        dg.setDataColumn(dc4);
        dg.getHeaderRows().add(new GridRow().addCells(new GridCell(new DataElement(dc4.getName(),dc4.getType()))));
        dg.setOrder(1);
        table.getDataGroups().add(dg);

        ReportBuilder builder = new ReportBuilder();
        Document doc =  builder.build(rd, dsTbl);

        doc.output(System.out);


    }

}