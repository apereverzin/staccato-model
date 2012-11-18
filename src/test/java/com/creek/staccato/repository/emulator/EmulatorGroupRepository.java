package com.creek.staccato.repository.emulator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.group.GroupInformationMessages;
import com.creek.staccato.domain.group.GroupRepository;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.profile.ProfileInformationMessages;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class EmulatorGroupRepository implements GroupRepository {
    private Map<GroupKey, Group> groups = new HashMap<GroupKey, Group>();
    private Map<ProfileKey, Profile> profiles = new HashMap<ProfileKey, Profile>();
    private Profile myProfile;
    
    public Group createGroup(Group group) throws RepositoryException {
        if(getGroup(group.getGroupKey()) != null) {
            throw new RepositoryException(group.getGroupKey() + " already exists");
        }
        groups.put(group.getGroupKey(), group);
        return group;
    }
    
    public Group updateGroup(Group group) throws RepositoryException {
        if(getGroup(group.getGroupKey()) == null) {
            throw new RepositoryException(group.getGroupKey() + " does not exist");
        }
        groups.put(group.getGroupKey(), group);
        return group;
    }
    
    public boolean deleteGroup(GroupKey groupKey) throws RepositoryException {
        if(getGroup(groupKey) == null) {
            return false;
        }
        groups.remove(groupKey);
        return true;
    }
    
    public Set<Group> getGroups() throws RepositoryException {
        return (Set<Group>)groups.values();
    }
    
    public Group getGroup(GroupKey groupKey) throws RepositoryException {
        return groups.get(groupKey);
    }
    
    public GroupInformationMessages getGroupInformationMessages(GroupKey groupKey) throws RepositoryException {
        return null;
    }
    
    public boolean addProfileToGroup(GroupKey groupKey, ProfileKey profileKey) throws RepositoryException {
        Group group = getGroup(groupKey);
        if(group == null) {
            throw new RepositoryException(groupKey + " does not exist");
        }
        return group.getProfileKeys().add(profileKey);
    }
    
    public boolean removeProfileFromGroup(GroupKey groupKey, ProfileKey profileKey) throws RepositoryException {
        Group group = getGroup(groupKey);
        if(group == null) {
            throw new RepositoryException(groupKey + " does not exist");
        }
        return group.getProfileKeys().remove(profileKey);
    }
    
    public Set<ProfileKey> getGroupProfileKeys(GroupKey groupKey) throws RepositoryException {
        Group group = getGroup(groupKey);
        if(group == null) {
            throw new RepositoryException(groupKey + " does not exist");
        }
        return group.getProfileKeys();
    }
    
    public Profile createProfile(Profile profile) throws RepositoryException {
        return profile;
    }

    public Profile updateProfile(Profile profile) throws RepositoryException {
        return profile;
    }

    public boolean deleteProfile(ProfileKey profileKey) throws RepositoryException {
        return true;
    }
    
    public Profile createMyProfile(Profile profile) throws RepositoryException {
        myProfile = profile;
        return myProfile;
    }
    
    public Profile updateMyProfile(Profile profile) throws RepositoryException {
        myProfile = profile;
        return myProfile;
    }
    
    public Profile getMyProfile() throws RepositoryException {
        return myProfile;
    }
    
    public Profile getProfile(ProfileKey profileKey) throws RepositoryException {
        return profiles.get(profileKey);
    }

    public ProfileInformationMessages getProfileInformationMessages(ProfileKey profileKey) throws RepositoryException {
        return null;
    }

    public Set<ProfileKey> getFreeProfileKeys() throws RepositoryException {
        return Collections.EMPTY_SET;
    }

	@Override
	public void updateGroupInformationMessages(
			GroupInformationMessages groupInformationMessages)
			throws RepositoryException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProfileInformationMessages(
			ProfileInformationMessages profileInformationMessages)
			throws RepositoryException {
		// TODO Auto-generated method stub
		
	}
}
