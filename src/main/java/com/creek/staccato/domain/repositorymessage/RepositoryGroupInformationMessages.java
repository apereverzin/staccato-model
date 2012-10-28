package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupInformationMessages;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class RepositoryGroupInformationMessages extends AbstractRepositoryMessage {
    private GroupInformationMessages groupInformationMessages;

    private static final String GROUP_INFORMATION_MESSAGES = "groupInformationMessages";

    public RepositoryGroupInformationMessages(GroupInformationMessages groupInformationMessages, String productVersion) {
        super(productVersion);
        this.groupInformationMessages = groupInformationMessages;
    }

    public RepositoryGroupInformationMessages(JSONObject jsonObject) {
        super(jsonObject);
        this.groupInformationMessages = new GroupInformationMessages((JSONObject) jsonObject.get(GROUP_INFORMATION_MESSAGES));
    }

    public GroupInformationMessages getData() {
        return groupInformationMessages;
    }

    public int getMessageType() {
        return REPOSITORY_GROUP_INFORMATION_MESSAGES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(MESSAGE_TYPE, Integer.toString(getMessageType()));
        jsonObject.put(GROUP_INFORMATION_MESSAGES, groupInformationMessages.toJSON());
        return jsonObject;
    }
}
