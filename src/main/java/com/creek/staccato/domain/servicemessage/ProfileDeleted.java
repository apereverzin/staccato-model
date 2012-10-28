package com.creek.staccato.domain.servicemessage;

import java.util.Set;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.AbstractMultipleGroupMessage;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class ProfileDeleted extends AbstractMultipleGroupMessage {
    private final ProfileKey profileKey;

    private static final String PROFILE_KEY = "profileKey";

    public ProfileDeleted(ProfileKey profileKey, Set<GroupKey> groupsTo, Set<ProfileKey> profilesTo, MessageKey messageKey, String productVersion) {
        super(groupsTo, profilesTo, messageKey, productVersion);
        this.profileKey = profileKey;
    }

    public ProfileDeleted(JSONObject jsonObject) {
        super(jsonObject);
        this.profileKey = new ProfileKey((JSONObject) jsonObject.get(PROFILE_KEY));
    }

    public ProfileKey getProfileKey() {
        return profileKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(PROFILE_KEY, profileKey.toJSON());
        return jsonObject;
    }

    public int getMessageType() {
        return PROFILE_DELETED;
    }
}
