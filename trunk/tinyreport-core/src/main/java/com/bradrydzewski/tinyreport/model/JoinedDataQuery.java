package com.bradrydzewski.tinyreport.model;

import com.bradrydzewski.tinyreport.jdbc.DataSet;

/**
 * This class will take two DataQueries and join the DataSets, in memory,
 * on the specified columns.
 * @author Brad Rydzewski
 */
public class JoinedDataQuery {// extends DataQuery {

    private String dataQueryLeft;
    private String dataQueryRight;
    private String name;
    private String leftColumn;
    private String rightColumn;
    private JoinType joinType;

    public String getDataQueryLeft() {
        return dataQueryLeft;
    }

    public void setDataQueryLeft(String dataQueryLeft) {
        this.dataQueryLeft = dataQueryLeft;
    }

    public String getDataQueryRight() {
        return dataQueryRight;
    }

    public void setDataQueryRight(String dataQueryRight) {
        this.dataQueryRight = dataQueryRight;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public String getLeftColumn() {
        return leftColumn;
    }

    public void setLeftColumn(String leftColumn) {
        this.leftColumn = leftColumn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRightColumn() {
        return rightColumn;
    }

    public void setRightColumn(String rightColumn) {
        this.rightColumn = rightColumn;
    }

    public DataSet execute(DataQuery queryLeft, DataQuery queryRight) {
        return null;
    }
}
