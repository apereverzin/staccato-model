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
    Group createGroup(Group group) throws RepositoryException;

    Group updateGroup(Group group) throws RepositoryException;

    boolean deleteGroup(GroupKey groupKey) throws RepositoryException;

    Set<Group> getGroups() throws RepositoryException;

    Group getGroup(GroupKey groupKey) throws RepositoryException;

    GroupInformationMessages getGroupInformationMessages(GroupKey groupKey) throws RepositoryException;

    void updateGroupInformationMessages(GroupInformationMessages groupInformationMessages) throws RepositoryException;

    boolean addProfileToGroup(GroupKey groupKey, ProfileKey profileKey) throws RepositoryException;

    boolean removeProfileFromGroup(GroupKey groupKey, ProfileKey profileKey) throws RepositoryException;

    Set<ProfileKey> getGroupProfileKeys(GroupKey groupKey) throws RepositoryException;

    Set<ProfileKey> getFreeProfileKeys() throws RepositoryException;

    Profile createProfile(Profile profile) throws RepositoryException;

    Profile updateProfile(Profile profile) throws RepositoryException;

    boolean deleteProfile(ProfileKey profileKey) throws RepositoryException;

    Profile createMyProfile(Profile profile) throws RepositoryException;

    Profile getMyProfile() throws RepositoryException;

    Profile updateMyProfile(Profile profile) throws RepositoryException;

    Profile getProfile(ProfileKey profileKey) throws RepositoryException;

    ProfileInformationMessages getProfileInformationMessages(ProfileKey profileKey) throws RepositoryException;

    void updateProfileInformationMessages(ProfileInformationMessages profileInformationMessages) throws RepositoryException;
}
