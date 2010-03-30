package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;
import com.bradrydzewski.tinyreport.model.DataColumn;
import com.bradrydzewski.tinyreport.model.DataType;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import org.apache.ecs.StringElement;
import org.apache.ecs.xhtml.div;

/**
 *
 * @author Brad Rydzewski
 */
public class DataElement extends Element {

    private String script;
    private String column;
    private DataColumn value;
    private boolean dynamic = false;
    private String numberFormat = "";
    private String dateFormat = "";

    public DataElement() {
    }

    public DataElement(DataColumn value) {
        this.value = value;
    }

    public DataElement(String script) {
        this.script = script;
        this.dynamic = true;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public DataColumn getValue() {
        return value;
    }

    public void setValue(DataColumn value) {
        this.value = value;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    @Override
    public void build(org.apache.ecs.Element parent, ReportBuilderArgs args) {

        //create the div to hold the text
        div div = new div();
        div.setPrettyPrint(true);

        //create element to hold text
        StringElement innerText = null;

        //set the style, if not null
        if (styleName != null) {
            div.setStyle(styleName);
        }

        //if dynamic text, need to run script
        if (dynamic) {
           innerText = new StringElement(getScriptedValue(args));
        //else just get the bound column value
        } else {
            innerText = new StringElement(getBoundValue(
                    args.getCurrentDataRow()[getValue().getOrder()]));
        }

        //ensure text has pretty print
        innerText.setPrettyPrint(true);
        //add text to the div
        div.addElement(innerText);

        //add div to parent
        parent.addElementToRegistry(div);
    }

    String getScriptedValue(ReportBuilderArgs args) {
        
        try {
            //add arguments to the script engine for script access
            //args.getScriptEngine().put("row", args.getCurrentDataRow());
            args.getScriptEngine().put("params", args.getParameters());
            args.getScriptEngine().put("reportDefinition", args.getReportDefinition());
            args.getScriptEngine().put("this", this);
            //run the script and get the value
            return args.getScriptEngine().eval(script).toString();

        } catch (javax.script.ScriptException ex) {
            //re-throw error
            throw new RuntimeException(ex);
        }
    }

    /**
     * Gets the bound value in String format. Also applies any required
     * date or number formatting.
     * @param boundValue Object bound to this DataElement
     * @return Formatted String value.
     */
    String getBoundValue(Object boundValue) {

        //check if date, then format
        if (assertDateCell() && dateFormat.isEmpty() == false) {
            DateFormat dateFormatter = new SimpleDateFormat(dateFormat);
            return dateFormatter.format(boundValue);

            //check if number, then format
        } else if (assertNumberCell() && numberFormat.isEmpty() == false) {
            NumberFormat numberFormatter = new DecimalFormat(numberFormat);
            return numberFormatter.format(boundValue);
        }

        //otherwise just return as a object toString()
        return boundValue.toString();
    }

    /**
     * Asserts a cell is in date format
     * @param value
     * @return
     */
    boolean assertDateCell() {
        if (getValue() == null) {
            return false;
        }
        return getValue().getType() == DataType.DATE;
    }

    /**
     * Asserts a cell is in Number format
     * @param value
     * @return
     */
    boolean assertNumberCell() {
        if (getValue() == null) {
            return false;
        }

        switch (getValue().getType()) {
            case DOUBLE:
            case FLOAT:
            case INTEGER:
                return true;
            default:
                return false;
        }
    }
}
