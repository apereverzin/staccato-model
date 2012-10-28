package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.profile.Profile;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class RepositoryProfile extends AbstractRepositoryMessage {
    private Profile profile;

    private static final String PROFILE = "profile";

    public RepositoryProfile(Profile profile, String productVersion) {
        super(productVersion);
        this.profile = profile;
    }

    public RepositoryProfile(JSONObject jsonObject) {
        super(jsonObject);
        this.profile = new Profile((JSONObject) jsonObject.get(PROFILE));
    }

    public Profile getData() {
        return profile;
    }

    public int getMessageType() {
        return REPOSITORY_PROFILE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(MESSAGE_TYPE, Integer.toString(getMessageType()));
        jsonObject.put(PROFILE, profile.toJSON());
        return jsonObject;
    }
}
