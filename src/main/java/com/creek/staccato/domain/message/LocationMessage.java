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
public class LocationMessage extends AbstractMultipleGroupMessage {
    private String location;

    private static final String LOCATION = "location";

    public LocationMessage(String location, Set<GroupKey> groupsTo, Set<ProfileKey> profilesTo, MessageKey messageKey, String productVersion) {
        super(groupsTo, profilesTo, messageKey, productVersion);
        this.location = location;
    }

    public LocationMessage(JSONObject jsonObject) {
        super(jsonObject);
        this.location = (String) jsonObject.get(LOCATION);
    }

    public String getLocation() {
        return location;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(LOCATION, location);
        return jsonObject;
    }

    public int getMessageType() {
        return LOCATION_MESSAGE;
    }
}
