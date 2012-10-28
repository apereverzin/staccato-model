package com.creek.staccato.domain.group;

import java.util.Comparator;
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
public class GroupInformationMessages implements Transformable {
    private GroupKey groupKey;
    private SortedSet<MessageKey> informationMessageKeys = new TreeSet<MessageKey>(new MessageKeysComparator());
    
    private static final String GROUP_KEY = "groupKey";
    private static final String INFORMATION_MESSAGES = "informationMessages";

    public GroupInformationMessages(GroupKey groupKey) {
        if(groupKey == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.groupKey = groupKey;
    }
    
    public GroupInformationMessages(JSONObject jsonObject) {
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
        GroupInformationMessages other = (GroupInformationMessages) obj;
        if (groupKey == null) {
            if (other.groupKey != null)
                return false;
        } else if (!groupKey.equals(other.groupKey))
            return false;
        return true;
    }
    
    class MessageKeysComparator implements Comparator<MessageKey> {
    	public int compare(MessageKey key1, MessageKey key2) {
    		if(key1.getTimestamp() > key2.getTimestamp()) {
    			return -1;
    		} else if(key1.getTimestamp() < key2.getTimestamp()) {
    			return 1;
    		} else {
    			return key1.getSender().getEmailAddress().compareTo(key2.getSender().getEmailAddress());
    		}
    	}
    }
}
