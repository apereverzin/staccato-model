package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.YesNoResponse;
import com.creek.staccato.domain.util.Util;


/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractYesNoResponse extends AbstractResponse implements YesNoResponse {
    private boolean response;

    private static final String IS_POSITIVE = "isPositive";

    public AbstractYesNoResponse(MessageKey requestKey, String responseText, boolean response, MessageKey messageKey, String productVersion) {
        super(requestKey, responseText, messageKey, productVersion);
        this.response = response;
    }

    public AbstractYesNoResponse(JSONObject jsonObject) {
        super(jsonObject);
        this.response = Util.getBoolean((String) jsonObject.get(IS_POSITIVE));
    }

    public boolean isPositive() {
        return response;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(IS_POSITIVE, Boolean.toString(response));
        return jsonObject;
    }
}
