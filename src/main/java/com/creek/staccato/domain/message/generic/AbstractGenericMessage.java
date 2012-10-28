package com.creek.staccato.domain.message.generic;

import org.json.simple.JSONObject;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractGenericMessage implements GenericMessage {
    private String productVersion;
    
    public static final String MESSAGE_TYPE = "messageType";
    private static final String PRODUCT_VERSION = "productVersion";

    public AbstractGenericMessage(String productVersion) {
        this.productVersion = productVersion;
    }
    
    public AbstractGenericMessage(JSONObject jsonObject) {
        this.productVersion = (String)jsonObject.get("productVersion");
    }
    
    public String getProductVersion() {
        return productVersion;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MESSAGE_TYPE, Integer.toString(getMessageType()));
        jsonObject.put(PRODUCT_VERSION, productVersion);
        return jsonObject;
    }
}
