package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.AbstractGenericMessage;
import com.creek.staccato.domain.message.generic.AddressedMessage;


/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractAddressedMessage extends AbstractGenericMessage implements AddressedMessage {
    private MessageKey messageKey;
    
    private static final String MESSAGE_KEY = "messageKey";

    public AbstractAddressedMessage(MessageKey messageKey, String productVersion) {
        super(productVersion);
        this.messageKey = messageKey;
    }

    public AbstractAddressedMessage(JSONObject jsonObject) {
        super(jsonObject);
        this.messageKey = new MessageKey((JSONObject)jsonObject.get(MESSAGE_KEY));
    }

    public MessageKey getMessageKey() {
        return messageKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(MESSAGE_TYPE, Integer.toString(getMessageType()));
        jsonObject.put(MESSAGE_KEY, messageKey.toJSON());
        return jsonObject;
    }
}
