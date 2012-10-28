package com.creek.staccato.domain.message;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.generic.MultipleGroupMessage;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractMultipleGroupMessage extends AbstractMultipleProfileMessage implements MultipleGroupMessage {
    private Set<GroupKey> groupsTo = new HashSet<GroupKey>();

    private static final String GROUPS_TO = "groupsTo";

    public AbstractMultipleGroupMessage(Set<GroupKey> groupsTo, Set<ProfileKey> profilesTo, MessageKey messageKey, String productVersion) {
        super(profilesTo, messageKey, productVersion);
        this.groupsTo = groupsTo;
    }

    public AbstractMultipleGroupMessage(JSONObject jsonObject) {
        super(jsonObject);
        JSONArray groupsToArray = (JSONArray) jsonObject.get(GROUPS_TO);
        for (Object groupKeyObject : groupsToArray) {
            groupsTo.add(new GroupKey((JSONObject) groupKeyObject));
        }
    }

    public Set<GroupKey> getGroupsTo() {
        return groupsTo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        JSONArray groupsToArray = new JSONArray();
        for (GroupKey groupKey : groupsTo) {
            groupsToArray.add(groupKey.toJSON());
        }
        jsonObject.put(GROUPS_TO, groupsToArray);
        return jsonObject;
    }
}
