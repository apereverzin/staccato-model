package com.creek.staccato.domain.message;

import java.util.Set;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.Request;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractRequest extends AbstractMultipleProfileMessage implements Request {
    private String requestText;
    
    private static final String REQUEST_TEXT = "requestText";

    public AbstractRequest(String requestText,
            Set<ProfileKey> profilesTo, MessageKey messageKey,
            String productVersion) {
        super(profilesTo, messageKey, productVersion);
        this.requestText = requestText;
    }

    public AbstractRequest(JSONObject jsonObject) {
        super(jsonObject);
        this.requestText = (String)jsonObject.get(REQUEST_TEXT);
    }
    
    public String getRequestText() {
        return requestText;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(REQUEST_TEXT, requestText);
        return jsonObject;
    }
}
