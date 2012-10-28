package com.creek.staccato.domain.servicemessage;

import java.util.Set;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.Group;
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
public class GroupCreated extends AbstractMultipleGroupMessage {
    private final Group group;

    private static final String GROUP = "group";

    public GroupCreated(Group group, Set<GroupKey> groupsTo, Set<ProfileKey> profilesTo, MessageKey messageKey, String productVersion) {
        super(groupsTo, profilesTo, messageKey, productVersion);
        this.group = group;
    }

    public GroupCreated(JSONObject jsonObject) {
        super(jsonObject);
        this.group = new Group((JSONObject) jsonObject.get(GROUP));
    }

    public Group getGroup() {
        return group;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(GROUP, group.toJSON());
        return jsonObject;
    }

    public int getMessageType() {
        return GROUP_CREATED;
    }
}
