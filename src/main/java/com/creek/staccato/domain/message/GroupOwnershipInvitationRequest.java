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
public class GroupOwnershipInvitationRequest extends AbstractRequest {
    private GroupKey groupKey;

    private static final String GROUP_KEY = "groupKey";

    public GroupOwnershipInvitationRequest(GroupKey groupKey, Set<ProfileKey> profilesTo, String requestText, MessageKey messageKey, String productVersion) {
        super(requestText, profilesTo, messageKey, productVersion);
        this.groupKey = groupKey;
    }

    public GroupOwnershipInvitationRequest(JSONObject jsonObject) {
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
        return GROUP_OWNERSHIP_INVITATION_REQUEST;
    }
}
