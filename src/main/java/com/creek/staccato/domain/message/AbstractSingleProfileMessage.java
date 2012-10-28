package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.SingleProfileMessage;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractSingleProfileMessage extends AbstractAddressedMessage implements SingleProfileMessage {
    private ProfileKey profileTo;

    private static final String PROFILE_TO = "profileTo";

    public AbstractSingleProfileMessage(ProfileKey profileTo, MessageKey messageKey, String productVersion) {
        super(messageKey, productVersion);
        this.profileTo = profileTo;
    }

    public AbstractSingleProfileMessage(JSONObject jsonObject) {
        super(jsonObject);
        this.profileTo = new ProfileKey((JSONObject) jsonObject.get(PROFILE_TO));
    }

    public ProfileKey getProfileTo() {
        return profileTo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(PROFILE_TO, profileTo.toJSON());
        return jsonObject;
    }
}
