package com.creek.staccato.domain.group;

import java.util.HashSet;
import java.util.Set;

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
public class Group implements Transformable {
    private GroupKey groupKey;
    private String description;
    private int includeVotes;
    private int excludeVotes;
    private Set<ProfileKey> ownerKeys = new HashSet<ProfileKey>();
    private Set<ProfileKey> profileKeys = new HashSet<ProfileKey>();
    private Set<GroupProfileFieldDefinition> groupProfileFieldDefinitions = new HashSet<GroupProfileFieldDefinition>();
    
    private static final String GROUP_KEY = "groupKey";
    private static final String DESCRIPTION = "description";
    private static final String INCLUDE_VOTES = "includeVotes";
    private static final String EXCLUDE_VOTES = "excludeVotes";
    private static final String OWNER_KEYS = "ownerKeys";
    private static final String PROFILE_KEYS = "profileKeys";
    private static final String GROUP_PROFILE_FIELD_DEFINITIONS = "groupProfileFieldDefinitions";

    public Group(GroupKey groupKey) {
        if(groupKey == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.groupKey = groupKey;
    }
    
    public Group(JSONObject jsonObject) {
        if(jsonObject == null) {
            throw new IllegalArgumentException("No parameter should be null");
        }
        this.groupKey = new GroupKey((JSONObject)jsonObject.get(GROUP_KEY));
        this.description = (String)jsonObject.get(DESCRIPTION);
        this.includeVotes = Integer.parseInt((String)jsonObject.get(INCLUDE_VOTES));
        this.excludeVotes = Integer.parseInt((String)jsonObject.get(EXCLUDE_VOTES));
        JSONArray ownerKeysArray = (JSONArray)jsonObject.get(OWNER_KEYS);
        for(Object ownerKeyObject: ownerKeysArray) {
            ownerKeys.add(new ProfileKey((JSONObject)ownerKeyObject));
        }
        JSONArray profileKeysArray = (JSONArray)jsonObject.get(PROFILE_KEYS);
        for(Object profileKeyObject: profileKeysArray) {
            profileKeys.add(new ProfileKey((JSONObject)profileKeyObject));
        }
        JSONArray groupProfileFieldDefinitionsArray = (JSONArray)jsonObject.get(GROUP_PROFILE_FIELD_DEFINITIONS);
        for(Object groupProfileFieldDefinitionObject: groupProfileFieldDefinitionsArray) {
            groupProfileFieldDefinitions.add(new GroupProfileFieldDefinition((JSONObject)groupProfileFieldDefinitionObject));
        }
    }

    public GroupKey getGroupKey() {
        return groupKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProfileKey> getProfileKeys() {
        return profileKeys;
    }

    public int getIncludeVotes() {
        return includeVotes;
    }

    public void setIncludeVotes(int includeVotes) {
        this.includeVotes = includeVotes;
    }

    public int getExcludeVotes() {
        return excludeVotes;
    }

    public void setExcludeVotes(int excludeVotes) {
        this.excludeVotes = excludeVotes;
    }
    
    public Set<GroupProfileFieldDefinition> getGroupProfileFieldDefinitions() {
        return groupProfileFieldDefinitions;
    }

    public Set<ProfileKey> getOwnerKeys() {
        return ownerKeys;
    }
    
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject groupObject = new JSONObject();
        groupObject.put(GROUP_KEY, getGroupKey().toJSON());
        groupObject.put(DESCRIPTION, getDescription());
        groupObject.put(INCLUDE_VOTES, Integer.toString(getIncludeVotes()));
        groupObject.put(EXCLUDE_VOTES, Integer.toString(getExcludeVotes()));
        JSONArray profileKeysArray = new JSONArray();
        for(ProfileKey profileKey: profileKeys) {
            profileKeysArray.add(profileKey.toJSON());
        }
        groupObject.put(PROFILE_KEYS, profileKeysArray);
        JSONArray ownerKeysArray = new JSONArray();
        for(ProfileKey profileKey: ownerKeys) {
            ownerKeysArray.add(profileKey.toJSON());
        }
        groupObject.put(OWNER_KEYS, ownerKeysArray);
        JSONArray groupFieldsArray = new JSONArray();
        for(GroupProfileFieldDefinition groupProfileFieldDefinition: groupProfileFieldDefinitions) {
            groupFieldsArray.add(groupProfileFieldDefinition.toJSON());
        }
        groupObject.put(GROUP_PROFILE_FIELD_DEFINITIONS, groupFieldsArray);
        return groupObject;
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
        Group other = (Group) obj;
        if (groupKey == null) {
            if (other.groupKey != null)
                return false;
        } else if (!groupKey.equals(other.groupKey))
            return false;
        return true;
    }
}
