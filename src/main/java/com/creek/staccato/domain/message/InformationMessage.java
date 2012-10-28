package com.creek.staccato.domain.message;

import java.util.Set;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class InformationMessage extends AbstractMultipleGroupMessage {
    private String title;
    private String text;

    private static final String TITLE = "title";
    private static final String TEXT = "text";

    public InformationMessage(String title, String text, Set<GroupKey> groupsTo, Set<ProfileKey> profilesTo, MessageKey messageKey, String productVersion) {
        super(groupsTo, profilesTo, messageKey, productVersion);
        this.title = title;
        this.text = text;
    }

    public InformationMessage(JSONObject jsonObject) {
        super(jsonObject);
        this.title = (String) jsonObject.get(TITLE);
        this.text = (String) jsonObject.get(TEXT);
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject messageObject = super.toJSON();
        messageObject.put(TITLE, title);
        messageObject.put(TEXT, text);
        return messageObject;
    }

    public int getMessageType() {
        return INFORMATION_MESSAGE;
    }
}
