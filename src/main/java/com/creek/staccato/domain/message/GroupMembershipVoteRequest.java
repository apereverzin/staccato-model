package com.creek.staccato.domain.message;

import java.util.Set;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class GroupMembershipVoteRequest extends AbstractRequest {
    private GroupKey groupKey;
    private Profile profile;

    private static final String GROUP_KEY = "groupKey";
    private static final String PROFILE = "profile";

    public GroupMembershipVoteRequest(GroupKey groupKey, Profile profile,
            Set<ProfileKey> profilesTo, String requestText,
            MessageKey messageKey, String productVersion) {
        super(requestText, profilesTo, messageKey, productVersion);
        if (groupKey == null || profile == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.groupKey = groupKey;
        this.profile = profile;
    }

    public GroupMembershipVoteRequest(JSONObject jsonObject) {
        super(jsonObject);
        this.groupKey = new GroupKey((JSONObject) jsonObject.get(GROUP_KEY));
        this.profile = new Profile((JSONObject) jsonObject.get(PROFILE));
    }

    public GroupKey getGroupKey() {
        return groupKey;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(GROUP_KEY, groupKey.toJSON());
        jsonObject.put(PROFILE, profile.toJSON());
        return jsonObject;
    }

    public int getMessageType() {
        return GROUP_MEMBERSHIP_VOTE_REQUEST;
    }
}
