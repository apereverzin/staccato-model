package com.creek.staccato.domain.group;

import java.util.Set;

import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.profile.ProfileInformationMessages;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public interface GroupRepository {
    public Group createGroup(Group group) throws RepositoryException;

    public Group updateGroup(Group group) throws RepositoryException;

    public boolean deleteGroup(GroupKey groupKey) throws RepositoryException;

    public Set<Group> getGroups() throws RepositoryException;

    public Group getGroup(GroupKey groupKey) throws RepositoryException;

    public GroupInformationMessages getGroupInformationMessages(GroupKey groupKey) throws RepositoryException;

    public boolean addProfileToGroup(GroupKey groupKey, ProfileKey profileKey) throws RepositoryException;

    public boolean removeProfileFromGroup(GroupKey groupKey, ProfileKey profileKey) throws RepositoryException;

    public Set<ProfileKey> getGroupProfileKeys(GroupKey groupKey) throws RepositoryException;

    public Set<ProfileKey> getFreeProfileKeys() throws RepositoryException;

    public Profile createProfile(Profile profile) throws RepositoryException;

    public Profile updateProfile(Profile profile) throws RepositoryException;

    public boolean deleteProfile(ProfileKey profileKey) throws RepositoryException;

    public Profile createMyProfile(Profile profile) throws RepositoryException;

    public Profile getMyProfile() throws RepositoryException;

    public Profile updateMyProfile(Profile profile) throws RepositoryException;

    public Profile getProfile(ProfileKey profileKey) throws RepositoryException;

    public ProfileInformationMessages getProfileInformationMessages(ProfileKey profileKey) throws RepositoryException;
}
