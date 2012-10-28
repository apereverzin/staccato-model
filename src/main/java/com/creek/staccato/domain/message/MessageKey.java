package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.Transformable;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class MessageKey implements Transformable {
    private ProfileKey sender;
    private long timestamp;

    private static final String SENDER = "sender";
    private static final String TIMESTAMP = "timestamp";

    public MessageKey(ProfileKey sender) {
        if (sender == null) {
            throw new IllegalArgumentException("No argument should be null");
        }
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
    }

    public MessageKey(ProfileKey sender, long timestamp) {
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public MessageKey(JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("No argument should be null");
        }
        ProfileKey sender = new ProfileKey((JSONObject) jsonObject.get(SENDER));
        if (sender == null) {
            throw new IllegalArgumentException("No argument should be null");
        }
        long timestamp = Long.parseLong((String) jsonObject.get(TIMESTAMP));
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public ProfileKey getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sender == null) ? 0 : sender.hashCode());
        result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
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
        MessageKey other = (MessageKey) obj;
        if (sender == null) {
            if (other.sender != null) {
                return false;
            }
        } else if (!sender.equals(other.sender)) {
            return false;
        }
        if (timestamp != other.timestamp) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + " [sender=" + sender + ", timestamp=" + timestamp + "]";
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject messageKeyObject = new JSONObject();
        messageKeyObject.put(SENDER, getSender().toJSON());
        messageKeyObject.put(TIMESTAMP, Long.toString(getTimestamp()));
        return messageKeyObject;
    }
}
