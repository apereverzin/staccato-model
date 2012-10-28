package com.creek.staccato.domain.profile;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupProfileFieldValue;
import com.creek.staccato.domain.message.generic.Transformable;
import com.creek.staccato.domain.util.Util;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class Profile implements Transformable {
    private ProfileKey profileKey;
    private String firstName;
    private String lastName;
    private String nickName;
    private String comment;
    private String mobilePhone;
    private String countryCode;
    private boolean isInternal;
    private Set<GroupProfileFieldValue> groupFields = new HashSet<GroupProfileFieldValue>();

    private static final String PROFILE_KEY = "profileKey";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String NICK_NAME = "nickName";
    private static final String COMMENT = "comment";
    private static final String MOBILE_PHONE = "mobilePhone";
    private static final String COUNTRY_CODE = "countryCode";
    private static final String IS_INTERNAL = "isInternal";

    public Profile(ProfileKey profileKey) {
        if (profileKey == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.profileKey = profileKey;
    }

    public Profile(JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.profileKey = new ProfileKey((JSONObject) jsonObject.get(PROFILE_KEY));
        this.firstName = (String) jsonObject.get(FIRST_NAME);
        this.lastName = (String) jsonObject.get(LAST_NAME);
        this.nickName = (String) jsonObject.get(NICK_NAME);
        this.countryCode = (String) jsonObject.get(COUNTRY_CODE);
        this.comment = (String) jsonObject.get(COMMENT);
        this.mobilePhone = (String) jsonObject.get(MOBILE_PHONE);
        this.isInternal = Util.getBoolean((String) jsonObject.get(IS_INTERNAL));
    }

    public ProfileKey getProfileKey() {
        return profileKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }

    public Set<GroupProfileFieldValue> getGroupFields() {
        return groupFields;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(PROFILE_KEY, profileKey.toJSON());
        jsonObject.put(FIRST_NAME, firstName);
        jsonObject.put(LAST_NAME, lastName);
        jsonObject.put(NICK_NAME, nickName);
        jsonObject.put(COUNTRY_CODE, countryCode);
        jsonObject.put(COMMENT, comment);
        jsonObject.put(MOBILE_PHONE, mobilePhone);
        jsonObject.put(IS_INTERNAL, Boolean.toString(isInternal));
        return jsonObject;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((profileKey == null) ? 0 : profileKey.hashCode());
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
        Profile other = (Profile) obj;
        if (profileKey == null) {
            if (other.profileKey != null)
                return false;
        } else if (!profileKey.equals(other.profileKey))
            return false;
        return true;
    }
}
