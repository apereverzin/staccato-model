package com.creek.staccato.domain.message;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.MultipleProfileMessage;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
@SuppressWarnings("serial")
public abstract class AbstractMultipleProfileMessage extends
        AbstractAddressedMessage implements MultipleProfileMessage {
    private Set<ProfileKey> profilesTo = new HashSet<ProfileKey>();

    private static final String PROFILES_TO = "profilesTo";

    public AbstractMultipleProfileMessage(Set<ProfileKey> profilesTo,
            MessageKey messageKey, String productVersion) {
        super(messageKey, productVersion);
        this.profilesTo = profilesTo;
    }

    public AbstractMultipleProfileMessage(JSONObject jsonObject) {
        super(jsonObject);
        JSONArray profilesToArray = (JSONArray) jsonObject.get(PROFILES_TO);
        for (Object profileKeyObject : profilesToArray) {
            profilesTo.add(new ProfileKey((JSONObject) profileKeyObject));
        }
    }

    public Set<ProfileKey> getProfilesTo() {
        return profilesTo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        JSONArray profilesToArray = new JSONArray();
        for (ProfileKey profileKey : profilesTo) {
            profilesToArray.add(profileKey.toJSON());
        }
        jsonObject.put(PROFILES_TO, profilesToArray);
        return jsonObject;
    }
}
