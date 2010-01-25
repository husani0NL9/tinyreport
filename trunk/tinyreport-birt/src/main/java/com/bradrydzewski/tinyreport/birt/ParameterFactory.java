package com.bradrydzewski.tinyreport.birt;

import com.bradrydzewski.tinyreport.model.ReportDefinition;
import com.bradrydzewski.tinyreport.model.Parameter;
import com.bradrydzewski.tinyreport.model.ParameterDateTextBox;
import com.bradrydzewski.tinyreport.model.ParameterTextBox;
import java.util.Map;
import org.eclipse.birt._2005.design.ReportElementType;
import org.eclipse.birt._2005.design.ScalarParamType;

/**
 *
 * @author Brad
 */
public class ParameterFactory extends BaseFactory {

    public static Parameter createParameter(ReportElementType elem,
            ReportDefinition reportDefinition) {

        Parameter reportParameter = null;

        if(elem==null)
            return null;

        if(elem instanceof ScalarParamType) {
            ScalarParamType param = (ScalarParamType)elem;
            Map<String, String> props = getPropertyValueMap(
                    param.getPropertyOrExpressionOrXmlProperty());

            String controlType = props.get("controlType");
            String dataType = props.get("dataType");
            if(controlType.equals("text-box") && dataType.equals("date")) {
                reportParameter = createDateParameter(props);
            } else if (controlType.equals("text-box")) {
                reportParameter = createTextParameter(props);
            }
            
            if(props.get("isRequired")!=null)
                reportParameter.setRequired(props.get("isRequired").equals("true"));

            reportParameter.setPrompt(props.get("promptText"));
            reportParameter.setName(param.getName());

        }


        return reportParameter;
    }

    public static Parameter createTextParameter(Map<String, String> props) {
        ParameterTextBox textParam = new ParameterTextBox();
        return textParam;
    }

    public static Parameter createDateParameter(Map<String, String> props) {
        ParameterDateTextBox dateParam = new ParameterDateTextBox();

        return dateParam;
    }
}
