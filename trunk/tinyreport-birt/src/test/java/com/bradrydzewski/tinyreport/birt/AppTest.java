package com.bradrydzewski.tinyreport.birt;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import junit.framework.TestCase;
import org.eclipse.birt._2005.design.CellType;
import org.eclipse.birt._2005.design.ColumnType;
import org.eclipse.birt._2005.design.DataSetType;
import org.eclipse.birt._2005.design.DataSourceType;
import org.eclipse.birt._2005.design.DesignElement;
import org.eclipse.birt._2005.design.EncryptedPropertyType;
import org.eclipse.birt._2005.design.ExpressionPropertyType;
import org.eclipse.birt._2005.design.ExtendedDataSourceType;
import org.eclipse.birt._2005.design.GridItemType;
import org.eclipse.birt._2005.design.ListPropertyType;
import org.eclipse.birt._2005.design.PropertyValueType;
import org.eclipse.birt._2005.design.Report;
import org.eclipse.birt._2005.design.RowType;
import org.eclipse.birt._2005.design.StructureType;
import org.eclipse.birt._2005.design.StylesType.Style;
import org.eclipse.birt._2005.design.TextPropertyType;
import org.eclipse.birt._2005.design.XmlPropertyType;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {

    /**
     * Rigourous Test :-)
     */
    public void testApp() {



        File rptdesign = new File("src/test/resources/TopSellingProducts.rptdesign");
        //rptdesign = new File("src\\test\\resources\\ProductCatalog.rptdesign");

        try {

            JAXBContext jc = JAXBContext.newInstance(Report.class);
            Unmarshaller um = jc.createUnmarshaller();
            Report report = (Report) um.unmarshal(rptdesign);
            System.out.println("id: " + report.getId());
            System.out.println("author: " + report.getAuthor());
            System.out.println("version: " + report.getVersion());
            System.out.println("created by: " + report.getCreatedBy());

            //get properties
            System.out.println("\n\nReport Properties\n----------------------");
            for (Object o : report.getPropertyOrExpressionOrXmlProperty()) {
                printProperties(o, "");
            }


            //get data sources
            System.out.println("\n\nData Sources\n----------------------");
            for (DataSourceType type : report.getDataSources().getOdaDataSourceOrScriptDataSource()) {
                System.out.println("id: " + type.getId() + " | " + type.getClass().toString());
                System.out.println("name: " + type.getName());
                System.out.println("extends: " + type.getExtends());
                for (Object o : type.getPropertyOrExpressionOrXmlProperty()) {
                    printProperties(o, "");
                }
                if (type instanceof ExtendedDataSourceType) {
                    //((ExtendedDataSourceType)type).
                }
                System.out.println("");
            }


            //get data sets
            System.out.println("\n\nData Sets\n----------------------");
            for (DataSetType type : report.getDataSets().getOdaDataSetOrScriptDataSetOrJointDataSet()) {
                System.out.println("id: " + type.getId() + " | "+type.getClass().getName());
                System.out.println("name: " + type.getName());
                System.out.println("extends: " + type.getExtends());
                for (Object o : type.getPropertyOrExpressionOrXmlProperty()) {
                    printProperties(o, "");
                }
                System.out.println("");
            }


            //get styles
            System.out.println("\n\nStyles\n----------------------");
            for (Style style : report.getStyles().getStyle()) {
                System.out.println("id: " + style.getId());
                System.out.println("name: " + style.getName());
                System.out.println("extends: " + style.getExtends());
                for (Object o : style.getPropertyOrExpressionOrXmlProperty()) {
                    printProperties(o, "");
                }
                System.out.println("");
            }

            //get styles
            System.out.println("\n\nBody Items\n----------------------");
            for (Object obj : report.getBody().getChartOrDataOrExtendedItem()) {

                printGuiItems(obj, "");
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        assertTrue(true);
    }

    public void printGuiItems(Object o, String indent) {

        String key = null;
        String value = null;

        if (o instanceof GridItemType) {
            key = ((GridItemType) o).getName();
            System.out.println(indent + "key: " + key + " {" + o.getClass().toString() + "}");
            System.out.println(indent + " props:");
            for (Object prop : ((GridItemType) o).getPropertyOrExpressionOrXmlProperty()) {
                printProperties(prop, indent.toString().concat("  "));
            }

            for (DesignElement elem : ((GridItemType) o).getRowAndColumn()) {

                printDesignElement(elem, indent.toString().concat(" "));
            }
        } else {
            System.out.println(indent+"unknown type: "+o.getClass().toString());
        }
    }

    public void printDesignElement(DesignElement elem, String indent) {
        if (elem instanceof ColumnType) {
            System.out.println(indent + "Column:");

        } else if (elem instanceof RowType) {
            System.out.println(indent + "Row:");
            for (CellType ct : ((RowType) elem).getCell()) {
                this.printDesignElement(ct, indent.toString().concat("  "));
            }
        } else if (elem instanceof CellType) {
            System.out.println(indent + "Cell:");
            for (DesignElement de : ((CellType) elem).getBrowserControlOrDataOrChart()) {
                this.printDesignElement(de, indent.toString().concat("  "));
            }
        } else {
            System.out.println(indent+"unknown type: "+elem.getClass().toString());
        }

        System.out.println(indent + " Props:");
        for (Object child : elem.getPropertyOrExpressionOrXmlProperty()) {
            this.printProperties(child, indent.toString().concat("   "));
        }
    }

    public void printListPropertyType(ListPropertyType o, String indent) {
        System.out.println(indent + "list: " + o.getName());
        for (StructureType struct : o.getStructure()) {
            printStructureType(struct, indent.toString().concat(" "));
        }

    }

    public void printStructureType(StructureType o, String indent) {
        System.out.println(indent + "struct: " + o.getName());
        for (Object child : o.getPropertyOrExpressionOrXmlProperty()) {
            printProperties(child, indent.toString().concat(" "));
        }
    }

    public void printProperties(Object o, String indent) {


        String key = null;
        String value = null;

        if (o instanceof EncryptedPropertyType) {
            key = ((EncryptedPropertyType) o).getName();
            value = ((EncryptedPropertyType) o).getEncryptionID() + " encrypted {";
            value += ((EncryptedPropertyType) o).getValue() + "}";
        } else if (o instanceof PropertyValueType) {
            key = ((PropertyValueType) o).getName();
            value = ((PropertyValueType) o).getValue();
        } else if (o instanceof TextPropertyType) {
            key = ((TextPropertyType) o).getName();
            value = ((TextPropertyType) o).getValue();
        } else if (o instanceof XmlPropertyType) {
            key = ((XmlPropertyType) o).getName();
            value = ((XmlPropertyType) o).getValue().replaceAll("\n", " ");
        } else if (o instanceof ExpressionPropertyType) {
            key = ((ExpressionPropertyType) o).getName();
            value = ((ExpressionPropertyType) o).getValue().replaceAll("\n", " ");
        }

        if (key != null) {
            System.out.println(indent + key + ((value == null) ? "" : ": " + value + "  {" + o.getClass().toString() + "}"));
            return;
        }

        if (o instanceof ListPropertyType) {
            printListPropertyType(((ListPropertyType) o), indent);
        } else if (o instanceof StructureType) {
            this.printStructureType(((StructureType) o), indent);
        } else {
            System.out.println(indent + "unknown object: " + o.getClass().toString());
        }

    }
}
