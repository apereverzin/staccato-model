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
public class GroupDeleted extends AbstractMultipleGroupMessage {
    private final GroupKey groupKey;

    private static final String GROUP_KEY = "groupKey";

    public GroupDeleted(GroupKey groupKey, Set<GroupKey> groupsTo, Set<ProfileKey> profilesTo, MessageKey messageKey, String productVersion) {
        super(groupsTo, profilesTo, messageKey, productVersion);
        this.groupKey = groupKey;
    }

    public GroupDeleted(JSONObject jsonObject) {
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
        return GROUP_DELETED;
    }
}
