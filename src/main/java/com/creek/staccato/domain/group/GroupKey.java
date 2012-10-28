package com.creek.staccato.domain.group;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.Transformable;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class GroupKey implements Transformable {
    private String name;
    private ProfileKey founderKey;

    private static final String NAME = "name";
    private static final String FOUNDER_KEY = "founderKey";

    public GroupKey(String name, ProfileKey founderKey) {
        if (name == null || founderKey == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.name = name;
        this.founderKey = founderKey;
    }

    public GroupKey(JSONObject jsonObject) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        String name = (String) jsonObject.get(NAME);
        if (name == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.name = name;
        this.founderKey = new ProfileKey((JSONObject) jsonObject.get(FOUNDER_KEY));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileKey getFounderKey() {
        return founderKey;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((founderKey == null) ? 0 : founderKey.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        GroupKey other = (GroupKey) obj;
        if (founderKey == null) {
            if (other.founderKey != null) {
                return false;
            }
        } else if (!founderKey.equals(other.founderKey)) {
            return false;
        }
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return getClass().getName() + " [founderKey=" + founderKey + ", name=" + name + "]";
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject groupKeyObject = new JSONObject();
        JSONObject founderObject = getFounderKey().toJSON();
        groupKeyObject.put(FOUNDER_KEY, founderObject);
        groupKeyObject.put(NAME, getName());
        return groupKeyObject;
    }
}
