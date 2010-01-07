package com.bradrydzewski.tinyreport.filter;

import com.bradrydzewski.tinyreport.jdbc.DataSet;
import com.bradrydzewski.tinyreport.model.DataColumn;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Defines a strategy for filtering a dataset
 * @author Brad Rydzewski
 */
public abstract class Filter {
    
    private DataColumn dataColumn;

    public DataColumn getDataColumn() {
        return dataColumn;
    }

    public void setDataColumn(DataColumn dataColumn) {
        this.dataColumn = dataColumn;
    }
    
    public abstract void init(DataSet dataSet);
    public abstract boolean isMatch(Object[] row);
    
    /**
     * Get list of cell values, including nulls.
     * should be less memory intensive than Cloning, because
     * we don't need ALL columns, just a few
     * @param dataSet
     * @return
     */
    List getCellValues(DataSet dataSet) {
        List cells = new ArrayList();
        int cellIndex = this.getDataColumn().getOrder();
        for (Object row : dataSet.getRows()) {
            Object cell = ((Object[]) row)[cellIndex];
            cells.add(cell);
        }
        
        return cells;
    }

    /**
     * Sorts a list of cells (List<Object>) in ascending order
     * @param cells List of cells
     */
    void sortCellValuesAsc(List cells) {
        Collections.sort(cells, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Comparable a = (Comparable) o1;
                Comparable b = (Comparable) o2;

                if (a == null && b == null) {
                    return 0;
                } else if (a == null) {
                    return -1;
                } else if (b == null) {
                    return 1;
                } else {
                    return a.compareTo(b); //descending
                }
            }
        });        
    }
    
    /**
     * Sorts a list of cells (List<Object>) in ascending order
     * @param cells List of cells
     */
    void sortCellValuesDesc(List cells) {
        Collections.sort(cells, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Comparable a = (Comparable) o1;
                Comparable b = (Comparable) o2;

                if (a == null && b == null) {
                    return 0;
                } else if (a == null) {
                    return -1;
                } else if (b == null) {
                    return 1;
                } else {
                    return b.compareTo(a); //descending
                }
            }
        });        
    }    
}
