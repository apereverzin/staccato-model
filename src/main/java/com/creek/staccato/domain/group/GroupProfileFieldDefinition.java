package com.creek.staccato.domain.group;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.Transformable;
import com.creek.staccato.domain.util.Util;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class GroupProfileFieldDefinition implements Transformable {
    public enum FieldType {
        STRING, NUMERIC, DATE
    };

    private GroupKey groupKey;
    private String name;
    private FieldType type;
    private boolean mandatory;

    private static final String GROUP_KEY = "groupKey";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String MANDATORY = "mandatory";
    private static final String STRING_TYPE = "STRING_TYPE";
    private static final String NUMERIC_TYPE = "NUMERIC_TYPE";
    private static final String DATE_TYPE = "DATE_TYPE";

    public GroupProfileFieldDefinition(JSONObject jsonObject) {
        this.groupKey = new GroupKey((JSONObject) jsonObject.get(GROUP_KEY));
        this.name = (String) jsonObject.get(NAME);
        this.mandatory = Util.getBoolean((String) jsonObject.get(MANDATORY));
        String typeString = (String) jsonObject.get(TYPE);
        if (STRING_TYPE.equals(typeString)) {
            type = FieldType.STRING;
        } else if (NUMERIC_TYPE.equals(typeString)) {
            type = FieldType.NUMERIC;
        } else if (DATE_TYPE.equals(typeString)) {
            type = FieldType.DATE;
        }
    }

    public GroupKey getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(GroupKey groupKey) {
        this.groupKey = groupKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(GROUP_KEY, getGroupKey().toJSON());
        jsonObject.put(NAME, getName());
        String typeString = STRING_TYPE;
        if (type == FieldType.NUMERIC) {
            typeString = NUMERIC_TYPE;
        } else if (type == FieldType.DATE) {
            typeString = DATE_TYPE;
        }
        jsonObject.put(TYPE, typeString);
        jsonObject.put(MANDATORY, Boolean.toString(mandatory));
        return jsonObject;
    }
}
