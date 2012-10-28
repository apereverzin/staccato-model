package com.creek.staccato.domain.servicemessage;

import java.util.Set;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.AbstractMultipleGroupMessage;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class ProfileUpdated extends AbstractMultipleGroupMessage {
    private final Profile profile;

    private static final String PROFILE = "profile";

    public ProfileUpdated(Profile profile, Set<GroupKey> groupsTo, Set<ProfileKey> profilesTo, MessageKey messageKey, String productVersion) {
        super(groupsTo, profilesTo, messageKey, productVersion);
        this.profile = profile;
    }

    public ProfileUpdated(JSONObject jsonObject) {
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
        return PROFILE_UPDATED;
    }
}
