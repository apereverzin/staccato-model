package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.profile.ProfileInformationMessages;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class RepositoryProfileInformationMessages extends AbstractRepositoryMessage {
    private ProfileInformationMessages profileInformationMessages;

    private static final String PROFILE_INFORMATION_MESSAGES = "profileInformationMessages";

    public RepositoryProfileInformationMessages(ProfileInformationMessages profileInformationMessages, String productVersion) {
        super(productVersion);
        this.profileInformationMessages = profileInformationMessages;
    }

    public RepositoryProfileInformationMessages(JSONObject jsonObject) {
        super(jsonObject);
        this.profileInformationMessages = new ProfileInformationMessages((JSONObject) jsonObject.get(PROFILE_INFORMATION_MESSAGES));
    }

    public ProfileInformationMessages getData() {
        return profileInformationMessages;
    }

    public int getMessageType() {
        return REPOSITORY_PROFILE_INFORMATION_MESSAGES;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(MESSAGE_TYPE, Integer.toString(getMessageType()));
        jsonObject.put(PROFILE_INFORMATION_MESSAGES, profileInformationMessages.toJSON());
        return jsonObject;
    }
}
