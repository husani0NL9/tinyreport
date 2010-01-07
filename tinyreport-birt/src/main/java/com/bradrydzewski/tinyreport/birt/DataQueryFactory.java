package com.bradrydzewski.tinyreport.birt;

import com.bradrydzewski.tinyreport.model.DataColumn;
import com.bradrydzewski.tinyreport.model.DataQuery;
import com.bradrydzewski.tinyreport.model.DataType;
import com.bradrydzewski.tinyreport.model.JdbcQuery;
import com.bradrydzewski.tinyreport.model.ReportDefinition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.birt._2005.design.DataSetType;
import org.eclipse.birt._2005.design.DesignElement.Structure;
import org.eclipse.birt._2005.design.ExtendedDataSetType;
import org.eclipse.birt._2005.design.ListPropertyType;
import org.eclipse.birt._2005.design.PropertyValueType;
import org.eclipse.birt._2005.design.StructureType;

/**
 *
 * @author Brad
 */
public class DataQueryFactory extends BaseFactory {

    public static DataQuery createDataConnection(DataSetType ds,
            ReportDefinition reportDefinition) {

        if (ds instanceof ExtendedDataSetType == false) {

            System.out.println("Unable to map Birt DataSetType ["
                    + ds.getClass().toString() + "] to a tinyreport DataQuery");
            return null;
        }

        JdbcQuery query = new JdbcQuery();
        query.setName(ds.getName());

        Map<String, String> queryProps = getPropertyValueMap(
                ds.getPropertyOrExpressionOrXmlProperty());

        query.setSqlQuery(queryProps.get("queryText"));
        query.setDataConnection(queryProps.get("dataSource"));

        for (Object prop : ds.getPropertyOrExpressionOrXmlProperty()) {
            if (prop instanceof Structure) {

                Structure struct = (Structure) prop;
                String propName = struct.getName();
                if (propName.equals("cachedMetaData")) {
                    query.setColumns(mapColumns(struct));
                } else {
                    System.out.println("Unable to map Structure [" + propName
                            + "] with value to the "
                            + "JdbcQuery [" + query.getName() + "] object");
                }

            } else {
                System.out.println("Unable to map item ["
                        + prop.getClass().getName() + "] to a property in the "
                        + "JdbcQuery [" + query.getName() + "] object");
            }
        }

        return query;
    }


    /**
     * A List of DataColumns in BIRT is represeted as a Structure entity,
     * and is Comprised of a ListPropertyType containing one or many columns,
     * which are represented as StructureType entity:
     *
     * <pre>
     *  <structure name="cachedMetaData">
     *    <list-property name="resultSet">
     *      <structure>
     *        <property name="position">1</property>
     *        <property name="name">PRODUCTLINE</property>
     *        <property name="dataType">string</property>
     *      </structure>
     *    </list-property>
     *  </structure>
     * </pre>
     *
     * Given the above marshalled structure, this class will return a list
     * of DataColumn objects.
     *
     * @param struct Birt Structure object containing a list of columns.
     * @return List of DataColumn objects.
     */
    protected static List<DataColumn> mapColumns(Structure struct) {

        //Create the list of columns
        List<DataColumn> columns = new ArrayList<DataColumn>();

        //In Birt, a single column would have the following xpath
        //  Structure\ListPropertyType\StructureType
        List<StructureType> structTypes = ((ListPropertyType) struct.getPropertyOrExpressionOrXmlProperty().get(0)).getStructure();

        //For each column structure we create a DataColumn object
        for (StructureType structType : structTypes) {
            columns.add(mapColumn(structType));
        }

        return columns;
    }

    /**
     * A DataColumn in Birt is represented as a StructureType entity, and
     * is comprised of PropertyValueTypes that represent the column's
     * attributes:
     *
     * <pre>
     *  <structure>
     *     <property name="position">1</property>
     *     <property name="name">PRODUCTLINE</property>
     *     <property name="dataType">string</property>
     *   </structure>
     * </pre>
     *
     * Given the above marshalled structure, this class will return a
     * a single DataColumn objects.
     *
     * @param structType
     * @return DataColumn based on the Birt column StructureType
     */
    protected static DataColumn mapColumn(StructureType structType) {

        DataColumn col = new DataColumn();

        for (Object prop : structType.getPropertyOrExpressionOrXmlProperty()) {
            String propName = ((PropertyValueType) prop).getName();
            String propValue = ((PropertyValueType) prop).getValue();

            if (prop instanceof PropertyValueType) {
                if (propName.equals("position")) {
                    col.setOrder(Integer.valueOf(propValue));
                } else if (propName.equals("name")) {
                    col.setName(propValue);
                } else if (propName.equals("dataType")) {
                    col.setType(DataType.valueOf(propValue.toUpperCase()));
                }
            }
        }

        return col;
    }

    protected void mapFilters(ListPropertyType list) {
    }

    protected void mapFilter(Structure struct) {
    }

    protected void mapParameters(ListPropertyType list) {
    }

    protected void mapParameter(Structure struct) {
    }
//id: 7 | org.eclipse.birt._2005.design.ExtendedDataSetType
//name: Total Sales By Product
//extends: null
//list: filter
// struct: null
//  operator: gt  {class org.eclipse.birt._2005.design.PropertyValueType}
//  expr: row["PRODUCTNAME"]  {class org.eclipse.birt._2005.design.ExpressionPropertyType}
//  unknown object: class org.eclipse.birt._2005.design.SimplePropertyListType
//list: parameters
// struct: null
//  name: param_1  {class org.eclipse.birt._2005.design.PropertyValueType}
//  dataType: string  {class org.eclipse.birt._2005.design.PropertyValueType}
//  position: 1  {class org.eclipse.birt._2005.design.PropertyValueType}
//  defaultValue: Test  {class org.eclipse.birt._2005.design.ExpressionPropertyType}
//  isInput: true  {class org.eclipse.birt._2005.design.PropertyValueType}
//  isOutput: false  {class org.eclipse.birt._2005.design.PropertyValueType}
//struct: cachedMetaData
// list: resultSet
//  struct: null
//   position: 1  {class org.eclipse.birt._2005.design.PropertyValueType}
//   name: PRODUCTNAME  {class org.eclipse.birt._2005.design.PropertyValueType}
//   dataType: string  {class org.eclipse.birt._2005.design.PropertyValueType}
//  struct: null
//   position: 2  {class org.eclipse.birt._2005.design.PropertyValueType}
//   name: TOTALREVENUE  {class org.eclipse.birt._2005.design.PropertyValueType}
//   dataType: float  {class org.eclipse.birt._2005.design.PropertyValueType}
//dataSource: Classic Models  {class org.eclipse.birt._2005.design.PropertyValueType}
//list: resultSet
// struct: null
//  position: 1  {class org.eclipse.birt._2005.design.PropertyValueType}
//  name: PRODUCTNAME  {class org.eclipse.birt._2005.design.PropertyValueType}
//  nativeName: PRODUCTNAME  {class org.eclipse.birt._2005.design.PropertyValueType}
//  dataType: string  {class org.eclipse.birt._2005.design.PropertyValueType}
// struct: null
//  position: 2  {class org.eclipse.birt._2005.design.PropertyValueType}
//  name: TOTALREVENUE  {class org.eclipse.birt._2005.design.PropertyValueType}
//  nativeName: TOTALREVENUE  {class org.eclipse.birt._2005.design.PropertyValueType}
//  dataType: float  {class org.eclipse.birt._2005.design.PropertyValueType}
//queryText: SELECT PRODUCTNAME,        SUM( PRICEEACH*QUANTITYORDERED ) AS "TOTALREVENUE" FROM   ORDERDETAILS, PRODUCTS WHERE  ORDERDETAILS.PRODUCTCODE = PRODUCTS.PRODUCTCODE GROUP BY PRODUCTS.PRODUCTNAME  {class org.eclipse.birt._2005.design.DesignElement$XmlProperty}
}
