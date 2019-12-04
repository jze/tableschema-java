package io.frictionlessdata.tableschema.field;

import io.frictionlessdata.tableschema.exception.ConstraintsException;
import io.frictionlessdata.tableschema.exception.InvalidCastException;
import io.frictionlessdata.tableschema.exception.TypeInferringException;
import org.json.JSONObject;

import java.util.*;

public class BooleanField extends Field<Boolean> {
    List<String> trueValues = Arrays.asList("yes", "y", "true", "t", "1");
    List<String> falseValues = Arrays.asList("no", "n", "false", "f", "0");


    BooleanField() {
        super();
    }

    public BooleanField(String name) {
        super(name, FIELD_TYPE_BOOLEAN);
    }

    public BooleanField(String name, String format, String title, String description, Map constraints, Map options){
        super(name, FIELD_TYPE_BOOLEAN, format, title, description, constraints, options);
    }

    public BooleanField(JSONObject field) {
        super(field);
        type = FIELD_TYPE_BOOLEAN;
    }

    public void setTrueValues(List<String> newValues) {
        trueValues = newValues;
    }

    public void setFalseValues(List<String> newValues) {
        falseValues = newValues;
    }

    @Override
    public Boolean parseValue(String value, String format, Map<String, Object> options)
            throws InvalidCastException, ConstraintsException {
        if (null != options) {
            if (options.containsKey("trueValues")) {
                trueValues = new ArrayList<>((Collection) options.get("trueValues"));
            }
            if (options.containsKey("falseValues")) {
                falseValues = new ArrayList<>((Collection) options.get("falseValues"));
            }
        }

        if (trueValues.contains(value.toLowerCase())){
            return true;

        }else if (falseValues.contains(value.toLowerCase())){
            return false;

        }else{
            throw new TypeInferringException();
        }
    }
}