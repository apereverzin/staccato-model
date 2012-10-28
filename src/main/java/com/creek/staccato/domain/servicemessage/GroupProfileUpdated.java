package com.creek.staccato.domain.servicemessage;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.AbstractAddressedMessage;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.Profile;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class GroupProfileUpdated extends AbstractAddressedMessage {
    private Profile profile;
    private GroupKey groupKey;

    private static final String PROFILE = "profile";
    private static final String GROUP_KEY = "groupKey";

    public GroupProfileUpdated(Profile profile, GroupKey groupKey, MessageKey messageKey, String productVersion) {
        super(messageKey, productVersion);
        if (profile == null || groupKey == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }

        this.profile = profile;
        this.groupKey = groupKey;
    }

    public GroupProfileUpdated(JSONObject jsonObject) {
        super(jsonObject);
        this.groupKey = new GroupKey((JSONObject) jsonObject.get(GROUP_KEY));
        this.profile = new Profile((JSONObject) jsonObject.get(PROFILE));
    }

    public Profile getProfile() {
        return profile;
    }

    public GroupKey getGroupKey() {
        return groupKey;
    }

    public int getMessageType() {
        return GROUP_PROFILE_UPDATED;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(PROFILE, profile.toJSON());
        jsonObject.put(GROUP_KEY, groupKey.toJSON());
        return jsonObject;
    }
}
