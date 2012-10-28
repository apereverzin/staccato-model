package com.creek.staccato.domain.profile;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.Transformable;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class ProfileKey implements Transformable {
    private String emailAddress;
    private static final String EMAIL_ADDRESS = "emailAddress";

    public ProfileKey(String emailAddress) {
        if (emailAddress == null) {
            throw new IllegalArgumentException("No argument should be null");
        }
        this.emailAddress = emailAddress;
    }

    public ProfileKey(JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("No argument should be null");
        }
        String emailAddress = (String) jsonObject.get(EMAIL_ADDRESS);
        if (emailAddress == null) {
            throw new IllegalArgumentException("No argument should be null");
        }
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ProfileKey other = (ProfileKey) obj;
        return emailAddress.equalsIgnoreCase(other.emailAddress);
    }

    @Override
    public String toString() {
        return getClass().getName() + " [emailAddress=" + emailAddress + "]";
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject profileKeyObject = new JSONObject();
        profileKeyObject.put(EMAIL_ADDRESS, getEmailAddress());
        return profileKeyObject;
    }
}
