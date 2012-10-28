package com.creek.staccato.domain.profile;

import java.util.SortedSet;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.Transformable;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class ProfileInformationMessages implements Transformable {
    private ProfileKey profileKey;
    private SortedSet<Long> informationMessageKeys = new TreeSet<Long>();
    
    private static final String PROFILE_KEY = "profileKey";
    private static final String INFORMATION_MESSAGES = "informationMessages";

    public ProfileInformationMessages(ProfileKey profileKey) {
        if(profileKey == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.profileKey = profileKey;
    }
    
    public ProfileInformationMessages(JSONObject jsonObject) {
        if(jsonObject == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.profileKey = new ProfileKey((JSONObject)jsonObject.get(PROFILE_KEY));
        JSONArray informationMessagesArray = (JSONArray)jsonObject.get(INFORMATION_MESSAGES);
        for(Object informationMessageObject: informationMessagesArray) {
            informationMessageKeys.add((Long)informationMessageObject);
        }
    }

    public ProfileKey getProfileKey() {
        return profileKey;
    }

    public SortedSet<Long> getInformationMessageKeys() {
        return informationMessageKeys;
    }
    
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject profileMessagesObject = new JSONObject();
        profileMessagesObject.put(PROFILE_KEY, getProfileKey().toJSON());
        JSONArray informationMessagesArray = new JSONArray();
        for(Long informationMessage: informationMessageKeys) {
            informationMessagesArray.add(informationMessage);
        }
        profileMessagesObject.put(INFORMATION_MESSAGES, informationMessagesArray);
        return profileMessagesObject;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((profileKey == null) ? 0 : profileKey.hashCode());
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
        ProfileInformationMessages other = (ProfileInformationMessages) obj;
        if (profileKey == null) {
            if (other.profileKey != null)
                return false;
        } else if (!profileKey.equals(other.profileKey))
            return false;
        return true;
    }
}
