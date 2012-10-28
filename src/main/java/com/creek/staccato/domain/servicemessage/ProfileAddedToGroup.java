package com.creek.staccato.domain.servicemessage;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.AbstractSingleGroupMessage;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.Profile;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class ProfileAddedToGroup extends AbstractSingleGroupMessage {
    private final Profile profile;

    private static final String PROFILE = "profile";

    public ProfileAddedToGroup(Profile profile, GroupKey groupTo, MessageKey messageKey, String productVersion) {
        super(groupTo, messageKey, productVersion);
        this.profile = profile;
    }

    public ProfileAddedToGroup(JSONObject jsonObject) {
        super(jsonObject);
        this.profile = new Profile((JSONObject) jsonObject.get(PROFILE));
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(PROFILE, profile.toJSON());
        return jsonObject;
    }

    public int getMessageType() {
        return PROFILE_CREATED;
    }
}
