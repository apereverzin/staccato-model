package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.InformationMessage;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class RepositoryInformationMessage extends AbstractRepositoryMessage {
    private InformationMessage informationMessage;

    private static final String INFORMATION_MESSAGE = "information_message";

    public RepositoryInformationMessage(InformationMessage informationMessage, String productVersion) {
        super(productVersion);
        this.informationMessage = informationMessage;
    }

    public RepositoryInformationMessage(JSONObject jsonObject) {
        super(jsonObject);
        this.informationMessage = new InformationMessage((JSONObject) jsonObject.get(INFORMATION_MESSAGE));
    }

    public InformationMessage getData() {
        return informationMessage;
    }

    public int getMessageType() {
        return REPOSITORY_INFORMATION_MESSAGE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(MESSAGE_TYPE, Integer.toString(getMessageType()));
        jsonObject.put(INFORMATION_MESSAGE, informationMessage.toJSON());
        return jsonObject;
    }
}
