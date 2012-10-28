package com.creek.staccato.domain.group;

import java.util.SortedSet;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.message.generic.Transformable;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class GroupMessages implements Transformable {
    private GroupKey groupKey;
    private SortedSet<MessageKey> informationMessageKeys = new TreeSet<MessageKey>();
    
    private static final String GROUP_KEY = "groupKey";
    private static final String INFORMATION_MESSAGES = "informationMessages";

    public GroupMessages(GroupKey groupKey) {
        if(groupKey == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.groupKey = groupKey;
    }
    
    public GroupMessages(JSONObject jsonObject) {
        if(jsonObject == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.groupKey = new GroupKey((JSONObject)jsonObject.get(GROUP_KEY));
        JSONArray informationMessagesArray = (JSONArray)jsonObject.get(INFORMATION_MESSAGES);
        for(Object informationMessageObject: informationMessagesArray) {
            informationMessageKeys.add(new MessageKey((JSONObject)informationMessageObject));
        }
    }

    public GroupKey getGroupKey() {
        return groupKey;
    }

    public SortedSet<MessageKey> getInformationMessageKeys() {
        return informationMessageKeys;
    }
    
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject groupMessagesObject = new JSONObject();
        groupMessagesObject.put(GROUP_KEY, getGroupKey().toJSON());
        JSONArray informationMessagesArray = new JSONArray();
        for(MessageKey informationMessageKey: informationMessageKeys) {
            informationMessagesArray.add(informationMessageKey.toJSON());
        }
        groupMessagesObject.put(INFORMATION_MESSAGES, informationMessagesArray);
        return groupMessagesObject;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((groupKey == null) ? 0 : groupKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GroupMessages other = (GroupMessages) obj;
        if (groupKey == null) {
            if (other.groupKey != null)
                return false;
        } else if (!groupKey.equals(other.groupKey))
            return false;
        return true;
    }
}
