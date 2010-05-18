package com.bradrydzewski.tinyreport.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Defines a column of data that will appear in a tabular data set.
 * @author Brad Rydzewski
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DataColumn {

    /**
     * The name of the array list.
     * @serial
     */
    @XmlValue
    private String name;
    /**
     * The order of the column in a tabular data set.
     * @serial
     */
    @XmlAttribute
    private int order;
    /**
     * The type of data.
     * @serial
     */
    @XmlAttribute
    private DataType type;

    /**
     * Constructs an empty data column with no specified data order
     * or data type.
     */
    public DataColumn() {
    }

    /**
     * Constructs a data column with the specified column name, order
     * and data type.
     * @param nm name of the data column
     * @param o order of the column in a tabular data set
     * @param t type of the data (ie String, int, float, etc)
     */
    public DataColumn(final String nm, final int o, final DataType t) {
        this.setName(nm);
        this.setOrder(o);
        this.setType(t);
    }

    /**
     * Gets the column name.
     * @return column name
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the column name.
     * @param nm column name
     */
    public final void setName(final String nm) {
        this.name = nm;
    }

    /**
     * Gets an Order of the column in a tabular dataset.
     * @return data column order
     */
    public final int getOrder() {
        return order;
    }

    /**
     * Sets the order of a column in a tabular dataset.
     * @param o data column order
     */
    public final void setOrder(final int o) {
        this.order = o;
    }

    /**
     * Gets the data type of the column.
     * @return data type
     */
    public final DataType getType() {
        return type;
    }

    /**
     * Sets the type of data in the column.
     * @param t data type
     */
    public final void setType(final DataType t) {
        this.type = t;
    }
}
