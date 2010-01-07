package com.bradrydzewski.tinyreport.html;

import com.bradrydzewski.tinyreport.ReportBuilderArgs;
import org.apache.ecs.StringElement;
import org.apache.ecs.xhtml.div;

/**
 *
 * @author Brad
 */
public class Text extends Element {

    private String value;
    private String script;
    private boolean dynamic = false;

    public Text() {
    }

    public Text(String value, boolean dynamic) {
        if(dynamic)
            this.script = value;
        else
            this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value, boolean dynamic) {
        this.value = value;
        this.dynamic = dynamic;
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

    @Override
    public void build(org.apache.ecs.Element parent, ReportBuilderArgs args) {

        //create the div to hold the text
        div div = new div();
        div.setPrettyPrint(true);

        //create inner html
        StringElement innerText = null;

        //set the style, if not null
        if(style!=null)
            div.setStyle(style.getValue());

        //if dynamic text, need to run script
        if(dynamic) {
            try {
                //add arguments to the script engine for script access
                //args.getScriptEngine().put("row", args.getCurrentDataRow());
                args.getScriptEngine().put("params", args.getReportDefinition().getReportParameters());
                args.getScriptEngine().put("reportDefinition", args.getReportDefinition());
                args.getScriptEngine().put("this", this);
                //run the script and get the value
                String innerHtml = args.getScriptEngine().eval(script).toString();
                innerText = new StringElement(innerHtml);

            } catch (javax.script.ScriptException ex) {
                //re-throw error
                throw new RuntimeException(ex);
            }
        } else {

            //set div's inner html
            innerText = new StringElement(value);
        }

        //ensure text has pretty print
        innerText.setPrettyPrint(true);
        //add text to the div
        div.addElement(innerText);

        //add div to parent
        parent.addElementToRegistry(div);
    }

}
