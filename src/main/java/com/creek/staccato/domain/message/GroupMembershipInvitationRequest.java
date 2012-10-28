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
public class GroupMembershipInvitationRequest extends AbstractRequest {
    private GroupKey groupKey;

    private static final String GROUP_KEY = "groupKey";

    public GroupMembershipInvitationRequest(GroupKey groupKey,
            Set<ProfileKey> profilesTo, String requestText,
            MessageKey messageKey, String productVersion) {
        super(requestText, profilesTo, messageKey, productVersion);
        if (groupKey == null) {
            throw new IllegalArgumentException("No argument should be null");
        }
        this.groupKey = groupKey;
    }

    public GroupMembershipInvitationRequest(JSONObject jsonObject) {
        super(jsonObject);
        this.groupKey = new GroupKey((JSONObject) jsonObject.get(GROUP_KEY));
    }

    public GroupKey getGroupKey() {
        return groupKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(GROUP_KEY, groupKey.toJSON());
        return jsonObject;
    }

    public int getMessageType() {
        return GROUP_MEMBERSHIP_INVITATION_REQUEST;
    }
}
