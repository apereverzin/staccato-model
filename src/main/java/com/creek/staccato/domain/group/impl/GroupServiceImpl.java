package com.creek.staccato.domain.group.impl;

import java.util.HashSet;
import java.util.Set;

import com.creek.staccato.domain.BusinessException;
import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.group.GroupProfileFieldDefinition;
import com.creek.staccato.domain.group.GroupProfileFieldValue;
import com.creek.staccato.domain.group.GroupRepository;
import com.creek.staccato.domain.group.GroupService;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

//import com.creek.staccato.repository.emulator.EmulatorGroupRepository;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository /* = new EmulatorGroupRepository() */;

    public Group createGroup(Group group) throws BusinessException {
        try {
            return groupRepository.createGroup(group);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Group updateGroup(Group group) throws BusinessException {
        try {
            return groupRepository.updateGroup(group);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public boolean deleteGroup(GroupKey groupKey) throws BusinessException {
        try {
            return groupRepository.deleteGroup(groupKey);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Set<Group> getGroups() throws BusinessException {
        try {
            return groupRepository.getGroups();
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Group getGroup(GroupKey groupKey) throws BusinessException {
        try {
            return groupRepository.getGroup(groupKey);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public boolean addProfileToGroup(Group group, Profile profile) throws BusinessException {
        try {
            Set<String> noValueDefinitions = getUndefinedMandatoryGroupFields(group, profile);

            if (noValueDefinitions != null) {
                StringBuilder sb = new StringBuilder("Empty fields not allowed: ");
                for (String fieldName : noValueDefinitions) {
                    sb.append(fieldName).append(" ");
                }
                throw new BusinessException(sb.toString());
            }
            return groupRepository.addProfileToGroup(group.getGroupKey(), profile.getProfileKey());
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public boolean removeProfileFromGroup(GroupKey groupKey, Profile profile) throws BusinessException {
        try {
            return groupRepository.removeProfileFromGroup(groupKey, profile.getProfileKey());
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Set<ProfileKey> getGroupProfileKeys(GroupKey groupKey) throws BusinessException {
        try {
            return groupRepository.getGroupProfileKeys(groupKey);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Profile createMyProfile(Profile profile) throws BusinessException {
        try {
            return groupRepository.createMyProfile(profile);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Profile updateMyProfile(Profile profile) throws BusinessException {
        try {
            return groupRepository.updateMyProfile(profile);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Profile getMyProfile() throws BusinessException {
        try {
            return groupRepository.getMyProfile();
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Profile getProfile(ProfileKey profileKey) throws BusinessException {
        try {
            return groupRepository.getProfile(profileKey);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public Set<ProfileKey> getFreeProfileKeys() throws BusinessException {
        try {
            return groupRepository.getFreeProfileKeys();
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    private Set<String> getUndefinedMandatoryGroupFields(Group group, Profile profile) {
        Set<GroupProfileFieldDefinition> definitions = new HashSet<GroupProfileFieldDefinition>();
        for (GroupProfileFieldValue val : profile.getGroupFields()) {
            definitions.add(val.getDefinition());
        }

        Set<String> noValueDefinitions = null;
        for (GroupProfileFieldDefinition fd : group.getGroupProfileFieldDefinitions()) {
            if (fd.isMandatory()) {
                if (definitions.contains(fd)) {
                    continue;
                }
                if (noValueDefinitions == null) {
                    noValueDefinitions = new HashSet<String>();
                }
                noValueDefinitions.add(fd.getName());
            }
        }
        return noValueDefinitions;
    }

    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
