package com.bradrydzewski.tinyreport.filter;

import com.bradrydzewski.tinyreport.jdbc.DataSet;
import com.bradrydzewski.tinyreport.model.DataColumn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filters a dataset to return the Top N number of data rows,
 * based on the provided column
 * @author Brad Rydzewski
 */
public class TopNFilter extends Filter {

    private int nNumber = 25;
    private Map nValues = null;

    public TopNFilter() {
        
    }

    public TopNFilter(int nNumber, DataColumn dc) {
        setN(nNumber);
        setDataColumn(dc);
    }

    public float getN() {
        return nNumber;
    }

    public void setN(int nNumber) {
        this.nNumber = nNumber;
    }
    
    
    
    @Override
    public void init(DataSet dataSet) {

        //normally, this method wouldn't be used...
        // but for the top N, top N%, bottom N, and bottom N%, we need to...
        
        //get list of cell values
        List cells = super.getCellValues(dataSet);
        
        //sort the collection descending
        super.sortCellValuesDesc(cells);
                
        //now get the top n% of values and put in a hashmap
        nValues = new HashMap();
        for(int i=0; i<=getN(); i++) {
            Object cell = cells.get(i);
            nValues.put(cell, cell);
        }
    }

    /**
     * Checks to see if a cell (in a row) is one of the top N number of values
     * compared to all other cells for that data column.
     * @param row
     * @return
     */
    @Override
    public boolean isMatch(Object[] row) {
        Object cell = ((Object[]) row)[this.getDataColumn().getOrder()];
        return nValues.containsKey(cell);
    }

    
    

}
