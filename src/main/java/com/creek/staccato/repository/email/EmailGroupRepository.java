package com.creek.staccato.repository.email;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.mail.Folder;
import javax.mail.MessagingException;

import com.creek.staccato.connector.mail.ConnectorException;
import com.creek.staccato.connector.mail.MailMessageConnector;
import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.group.GroupRepository;
import com.creek.staccato.domain.message.generic.GenericMessage;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.profile.ProfileMessages;
import com.creek.staccato.domain.repositorymessage.RepositoryException;
import com.creek.staccato.domain.repositorymessage.RepositoryGroup;
import com.creek.staccato.domain.repositorymessage.RepositoryMessage;
import com.creek.staccato.domain.repositorymessage.RepositoryProfile;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class EmailGroupRepository extends AbstractHierarchyRepository<RepositoryProfile> implements GroupRepository {
    private static final int MAX_PROFILES_FOLDER_SIZE = 20;
    private static final int MAX_PROFILES_LEVEL = 4;

    private static final String MY_PROFILE_SUBJECT = "MYPROFILE";
    private static final String FREE_SUBJECT = "FREE";

    public EmailGroupRepository(MailMessageConnector connector) throws RepositoryException {
        this(connector, true);
    }

    public EmailGroupRepository(MailMessageConnector connector, boolean init) throws RepositoryException {
        super(connector, init);
    }

    public Group createGroup(Group group) throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            // group already exists?
            if (connector.getMessageBySubject(groupsFolder, group.getGroupKey().toJSON().toString()) != null) {
                // yes; do nothing
                return group;
            }

            groupsFolder.open(Folder.READ_WRITE);
            connector.putRepositoryMessageToFolderWithUniqueSubject(groupsFolder, new RepositoryGroup(group, VERSION), group.getGroupKey().toJSON().toString());
            return group;
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public Group updateGroup(Group group) throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            groupsFolder.open(Folder.READ_WRITE);
            connector.removeMessage(groupsFolder, group.getGroupKey().toJSON().toString());
            RepositoryMessage repositoryProfile = new RepositoryGroup(group, VERSION);
            connector.putRepositoryMessageToFolderWithUniqueSubject(groupsFolder, repositoryProfile, group.getGroupKey().toJSON().toString());
            return group;
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public boolean deleteGroup(GroupKey groupKey) throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            groupsFolder.open(Folder.READ_WRITE);
            return connector.removeMessage(groupsFolder, groupKey.toJSON().toString());
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public Set<Group> getGroups() throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            GenericMessage[] repositoryGroups = connector.getAllMessages(groupsFolder);
            Set<Group> groups = new HashSet<Group>();
            for (int i = 0; i < repositoryGroups.length; i++) {
                groups.add(((RepositoryGroup)repositoryGroups[i]).getData());
            }
            return groups;
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public Group getGroup(GroupKey groupKey) throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            RepositoryGroup repositoryGroup = (RepositoryGroup) connector.getMessageBySubject(groupsFolder, groupKey.toJSON().toString());
            if (repositoryGroup == null) {
                return null;
            }
            return repositoryGroup.getData();
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public boolean addProfileToGroup(GroupKey groupKey, ProfileKey profileKey) throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            RepositoryGroup repositoryGroup = (RepositoryGroup) connector.getMessageBySubject(groupsFolder, groupKey.toJSON().toString());
            if (repositoryGroup == null) {
                return false;
            }
            Group group = repositoryGroup.getData();
            if (!group.getProfileKeys().contains(profileKey)) {
                group.getProfileKeys().add(profileKey);
                updateGroup(group);
            }
            return true;
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public boolean removeProfileFromGroup(GroupKey groupKey, ProfileKey profileKey) throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            RepositoryGroup repositoryGroup = (RepositoryGroup) connector.getMessageBySubject(groupsFolder, groupKey.toJSON().toString());
            if (repositoryGroup == null) {
                return false;
            }
            Group group = repositoryGroup.getData();
            if (group.getProfileKeys().contains(profileKey)) {
                group.getProfileKeys().remove(profileKey);
                updateGroup(group);
            }
            return true;
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public Set<ProfileKey> getGroupProfileKeys(GroupKey groupKey) throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            RepositoryGroup repositoryGroup = (RepositoryGroup) connector.getMessageBySubject(groupsFolder, groupKey.toJSON().toString());
            if (repositoryGroup == null) {
                return (Set<ProfileKey>) Collections.EMPTY_SET;
            }
            Group group = repositoryGroup.getData();
            return group.getProfileKeys();
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public Set<ProfileKey> getFreeProfileKeys() throws RepositoryException {
        try {
            Folder groupsFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME);
            RepositoryGroup repositoryGroup = (RepositoryGroup) connector.getMessageBySubject(groupsFolder, FREE_SUBJECT);
            if (repositoryGroup == null) {
                return (Set<ProfileKey>) Collections.EMPTY_SET;
            }
            Group group = repositoryGroup.getData();
            return group.getProfileKeys();
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public Profile createMyProfile(Profile profile) throws RepositoryException {
        try {
            initIfNecessary();
            Profile myProfile = getMyProfile();
            if (myProfile != null) {
                return null;
            }
            Folder myFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, PERSONAL_FOLDER_NAME);
            myFolder.open(Folder.READ_WRITE);
            RepositoryProfile repositoryProfile = new RepositoryProfile(profile, VERSION);
            connector.putRepositoryMessageToFolderWithUniqueSubject(myFolder, repositoryProfile, MY_PROFILE_SUBJECT);
            return profile;
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public Profile updateMyProfile(Profile profile) throws RepositoryException {
        try {
            initIfNecessary();
            Profile myProfile = getMyProfile();
            if (myProfile == null) {
                return null;
            }
            Folder myFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, PERSONAL_FOLDER_NAME);
            myFolder.open(Folder.READ_WRITE);
            RepositoryProfile repositoryProfile = new RepositoryProfile(profile, VERSION);
            connector.putRepositoryMessageToFolderWithUniqueSubject(myFolder, repositoryProfile, MY_PROFILE_SUBJECT);
            return profile;
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public Profile getMyProfile() throws RepositoryException {
        try {
            Folder myFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, PERSONAL_FOLDER_NAME);
            RepositoryProfile repositoryProfile = (RepositoryProfile) connector.getMessageBySubject(myFolder, MY_PROFILE_SUBJECT);
            if (repositoryProfile == null) {
                return null;
            }
            return repositoryProfile.getData();
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    /**
     * Add profile to email storage.
     * 
     * @param profile
     *            - profile to add
     * @return - added profile
     * @throws RepositoryException
     *             if error occurs
     */
    @Override
    public Profile createProfile(Profile profile) throws RepositoryException {
        saveData(new RepositoryProfile(profile, VERSION), profile.getProfileKey(), PROFILES_FOLDER_NAME, MAX_PROFILES_LEVEL, MAX_PROFILES_FOLDER_SIZE, INITIAL_PROFILES_BASE);
        ProfileMessages profileMessages = new ProfileMessages(profile.getProfileKey());
        return profile;
    }

    public Profile updateProfile(Profile profile) throws RepositoryException {
        try {
            initIfNecessary();
            Folder profilesFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, PROFILES_FOLDER_NAME);
            Folder folder = getFolderForData(profilesFolder, getProfileCode(profile.getProfileKey()), MAX_PROFILES_LEVEL, INITIAL_PROFILES_BASE);
            if (folder == null) {
                return null;
            }
            folder.open(Folder.READ_WRITE);
            connector.removeMessage(folder, profile.getProfileKey().toJSON().toString());
            RepositoryMessage repositoryProfile = new RepositoryProfile(profile, VERSION);
            connector.putRepositoryMessageToFolderWithUniqueSubject(folder, repositoryProfile, profile.getProfileKey().toJSON().toString());
            return profile;
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public boolean deleteProfile(ProfileKey profileKey) throws RepositoryException {
        try {
            initIfNecessary();
            Folder profilesFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, PROFILES_FOLDER_NAME);
            Folder folder = getFolderForData(profilesFolder, getProfileCode(profileKey), MAX_PROFILES_LEVEL, INITIAL_PROFILES_BASE);
            if (folder == null) {
                return true;
            }
            folder.open(Folder.READ_WRITE);
            return connector.removeMessage(folder, profileKey.toJSON().toString());
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    public Profile getProfile(ProfileKey profileKey) throws RepositoryException {
        try {
            initIfNecessary();
            Folder profilesFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, PROFILES_FOLDER_NAME);
            Folder folder = getFolderForData(profilesFolder, getProfileCode(profileKey), MAX_PROFILES_LEVEL, INITIAL_PROFILES_BASE);
            if (folder == null) {
                return null;
            }
            RepositoryProfile repositoryProfile = (RepositoryProfile) connector.getMessageBySubject(folder, profileKey.toJSON().toString());
            if (repositoryProfile == null) {
                return null;
            }
            return repositoryProfile.getData();
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    private int getProfileCode(ProfileKey profileKey) {
        return profileKey.hashCode();
    }
}
