package com.creek.staccato.domain.group;

import java.util.Set;

import com.creek.staccato.domain.BusinessException;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public interface GroupService {
    Group createGroup(Group group) throws BusinessException;

    Group updateGroup(Group group) throws BusinessException;

    boolean deleteGroup(GroupKey groupKey) throws BusinessException;

    Set<Group> getGroups() throws BusinessException;
    
    Group getGroup(GroupKey groupKey) throws BusinessException;

    boolean addProfileToGroup(Group group, Profile profile) throws BusinessException;

    boolean removeProfileFromGroup(GroupKey groupKey, Profile profile) throws BusinessException;

    Set<ProfileKey> getGroupProfileKeys(GroupKey groupKey) throws BusinessException;

    Set<ProfileKey> getFreeProfileKeys() throws BusinessException;

    Profile createMyProfile(Profile profile) throws BusinessException;

    Profile updateMyProfile(Profile profile) throws BusinessException;

    Profile getMyProfile() throws BusinessException;

    Profile getProfile(ProfileKey profileKey) throws BusinessException;

    void setGroupRepository(GroupRepository groupRepository);
}
