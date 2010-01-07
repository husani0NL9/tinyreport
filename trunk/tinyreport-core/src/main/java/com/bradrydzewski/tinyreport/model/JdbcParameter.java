package com.bradrydzewski.tinyreport.model;

/**
 * Parameter used to bind a variable to a jdbc query, using a PreparedStatement.
 * These will typically be linked to a report paramter, allowing a user
 * to provide input to a report's data set.
 * @author Brad Rydzewski
 */
public class JdbcParameter {

    /**
     * Syntax used to format a string.
     * @Serial
     */
    private String format;
    /**
     * Position of the jdbc parameter in the sql query.
     * @Serial
     */
    private int position;
    /**
     * Data type of the parameter.
     * @Serial
     */
    private DataType type;
    /**
     * Report parameter bound to the jdbc parameter.
     * @Serial
     */
    private ReportParameter reportParameter;
    /**
     * Report parameter name.
     * @Serial
     */
    private String name;

    public JdbcParameter() {
    }

    public JdbcParameter(int position, DataType type, ReportParameter reportParameter) {
        this.position = position;
        this.type = type;
        this.reportParameter = reportParameter;
    }

    /**
     * Gets the report parameter (input parameter) bound to this
     * jdbc report paramter.
     * @return report paramter bound to this jdbc parameter.
     */
    public final ReportParameter getReportParameter() {
        return reportParameter;
    }

    /**
     * Sets the report parameter (input parameter) bound to this
     * jdbc report paramter. The report parameter will supply the jdbc
     * parameter with the input value provided by the end user. This
     * value will be bound the query via using standard
     * jdbc prepared statements.
     * @param param report parameter bound to this jdbc parameter.
     */
    public final void setReportParameter(final ReportParameter param) {
        this.reportParameter = param;
    }

    /**
     * Gets the value of the jdbc parameter, based on the value provided by
     * the end user.
     * @return parameter value.
     */
    public final Object getValue() {
        return reportParameter.getValue();
    }

    /**
     * Gets the syntax used to format a string, for use with the
     * java.util.Formatter class.
     * @return format syntax.
     */
    public final String getFormat() {
        return format;
    }

    /**
     * Sets the syntax used to format a string, for use with the
     * java.util.Formatter class. See
     * http://java.sun.com/j2se/1.5.0/docs/api/java/util/Formatter.html
     * for examples of using the formatter.
     * @param f syntax used to format a string.
     */
    public final void setFormat(final String f) {
        this.format = f;
    }

    /**
     * Gets the position of the jdbc parameter in the jdbc sql statement.
     * @return jdbc parameter position.
     */
    public final int getPosition() {
        return position;
    }

    /**
     * Sets the position of the jdbc parameter in the jdbc sql statement.
     * @param pos jdbc parameter position.
     */
    public final void setPosition(final int pos) {
        this.position = pos;
    }

    /**
     * Gets the data type of the parameter being bound to the jdbc sql
     * statement.
     * @return parameter data type.
     */
    public final DataType getType() {
        return type;
    }

    /**
     * Sets the data type of the parameter being bound to the jdbc sql
     * statement.
     * @param t data type.
     */
    public final void setType(final DataType t) {
        this.type = t;
    }

    /**
     * Gets the name of the report parameter.
     * @return name of report parameter.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the report parameter.
     * @param name name of report parameter.
     */
    public void setName(String name) {
        this.name = name;
    }
}
