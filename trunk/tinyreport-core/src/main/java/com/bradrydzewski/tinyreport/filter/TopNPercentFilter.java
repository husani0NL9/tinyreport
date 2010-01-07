package com.bradrydzewski.tinyreport.filter;

import com.bradrydzewski.tinyreport.jdbc.DataSet;
import com.bradrydzewski.tinyreport.model.DataColumn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filters a dataset to return the Top N Percent of data rows,
 * based on the provided column
 * @author Brad Rydzewski
 */
public class TopNPercentFilter extends Filter {

    private float nPercent = .25f;
    private Map nValues = null;

    public TopNPercentFilter() {
        
    }

    public TopNPercentFilter(float nPercent, DataColumn dc) {
        setNPercent(nPercent);
        setDataColumn(dc);
    }    
    
    public float getNPercent() {
        return nPercent;
    }

    public void setNPercent(float nPercent) {
        this.nPercent = nPercent;
    }
    
    
    
    @Override
    public void init(DataSet dataSet) {

        //normally, this method wouldn't be used...
        // but for the top N, top N%, bottom N, and bottom N%, we need to...
        
        //get list of cell values
        List cells = super.getCellValues(dataSet);
        
        //sort the collection descending
        super.sortCellValuesDesc(cells);
        
        //get cell counts, include nulls
        float cellCount = cells.size();
        float topPercent = cellCount * nPercent;
        
        //now get the top n% of values and put in a hashmap
        nValues = new HashMap();
        for(int i=0; i<topPercent; i++) {
            Object cell = cells.get(i);
            nValues.put(cell, cell);
        }
    }

    /**
     * Checks to see if a cell (in a row) is one of the top N % values
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
