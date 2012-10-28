package com.creek.staccato.domain.group;

import com.creek.staccato.domain.profile.Profile;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public abstract class GroupProfileFieldValue {
    private GroupProfileFieldDefinition definition;
    private Profile profile;

    public GroupProfileFieldDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(GroupProfileFieldDefinition definition) {
        this.definition = definition;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
