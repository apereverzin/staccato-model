package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.Response;


/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractResponse extends AbstractSingleProfileMessage implements Response {
    private MessageKey requestKey;
    private String responseText;

    private static final String REQUEST_KEY = "requestKey";
    private static final String RESPONSE_TEXT = "responseText";

    public AbstractResponse(MessageKey requestKey, String responseText, MessageKey messageKey, String productVersion) {
        super(requestKey.getSender(), messageKey, productVersion);
        if (requestKey == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.requestKey = requestKey;
        this.responseText = responseText;
    }

    public AbstractResponse(JSONObject jsonObject) {
        super(jsonObject);
        this.requestKey = new MessageKey((JSONObject) jsonObject.get(REQUEST_KEY));
        this.responseText = (String) jsonObject.get(RESPONSE_TEXT);
    }

    public MessageKey getRequestKey() {
        return requestKey;
    }

    public String getResponseText() {
        return responseText;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(REQUEST_KEY, requestKey.toJSON());
        jsonObject.put(RESPONSE_TEXT, responseText);
        return jsonObject;
    }
}
