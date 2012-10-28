package com.creek.staccato.repository.email;

import javax.mail.Folder;
import javax.mail.MessagingException;

import com.creek.staccato.connector.mail.ConnectorException;
import com.creek.staccato.connector.mail.MailMessageConnector;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public abstract class AbstractRepository {
    protected MailMessageConnector connector;

    public static final String INBOX_FOLDER_NAME = "Inbox";
    public static final String MESSAGES_DB_FOLDER_NAME = "MessagesDB";
    public static final String GROUPS_FOLDER_NAME = "Groups";
    public static final String GROUPS_INFORMATION_MESSAGES_FOLDER_NAME = "GroupsInformationMessages";
    public static final String NEW_INFORMATION_MESSAGES_FOLDER_NAME = "NewInformationMessages";
    public static final String PERSONAL_FOLDER_NAME = "Personal";
    public static final String SENT_FOLDER_NAME = "Sent";
    public static final String SENT_INFORMATION_MESSAGES_FOLDER_NAME = "SentInformationMessages";
    public static final String SENT_REQUESTS_FOLDER_NAME = "Requests";
    public static final String DRAFTS_FOLDER_NAME = "Drafts";
    public static final String DRAFT_INFORMATION_MESSAGES_FOLDER_NAME = "DraftInformationMessages";
    public static final String DRAFT_REQUESTS_FOLDER_NAME = "DraftRequests";
    public static final String PENDING_REQUESTS_FOLDER_NAME = "PendingRequests";
    public static final String PROFILES_FOLDER_NAME = "Profiles";

    protected static final int INITIAL_PROFILES_BASE = 8;
    protected static final int INITIAL_MESSAGES_BASE = 8;

    public static final String VERSION = "1.0";
    public static final String APPLICATION = "STACCATO";

    public AbstractRepository(MailMessageConnector connector, boolean init) throws RepositoryException {
        this.connector = connector;
        if(init) {
            initFolders();
        }
    }

    protected void initFolders() throws RepositoryException {
        try {
            // Create root db folder if does not exist
            Folder messagesDbFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME);
            if (messagesDbFolder == null) {
                messagesDbFolder = connector.createFolder(MESSAGES_DB_FOLDER_NAME);
            }

            createFolderIfDoesNotExist(connector, GROUPS_FOLDER_NAME);

            createFolderIfDoesNotExist(connector, PERSONAL_FOLDER_NAME);
            
            Folder profilesFolder = createFolderIfDoesNotExist(connector, PROFILES_FOLDER_NAME);
            // Create sub-folders for profiles;
            // each sub-folder will contain restricted
            // (MAX_PROFILES_FOLDER_SIZE) number of messages
            if (profilesFolder.list().length < INITIAL_PROFILES_BASE) {
                for (int i = 0; i < INITIAL_PROFILES_BASE; i++) {
                    connector.createFolder(profilesFolder, Integer.toString(i), false);
                }
            }

            Folder groupInformationMessagesFolder = createFolderIfDoesNotExist(connector, GROUPS_INFORMATION_MESSAGES_FOLDER_NAME);
            // Create sub-folders for groups information messages;
            // each sub-folder will contain restricted
            // (MAX_MESSAGES_FOLDER_SIZE) number of messages
            if (groupInformationMessagesFolder.list().length < INITIAL_MESSAGES_BASE) {
                for (int i = 0; i < INITIAL_MESSAGES_BASE; i++) {
                    connector.createFolder(groupInformationMessagesFolder, Integer.toString(i), false);
                }
            }

            createFolderIfDoesNotExist(connector, PENDING_REQUESTS_FOLDER_NAME);

            createFolderIfDoesNotExist(connector, NEW_INFORMATION_MESSAGES_FOLDER_NAME);

            Folder sentFolder = createFolderIfDoesNotExist(connector, SENT_FOLDER_NAME);
            connector.createFolder(sentFolder, SENT_INFORMATION_MESSAGES_FOLDER_NAME, false);
            connector.createFolder(sentFolder, SENT_REQUESTS_FOLDER_NAME, false);

            Folder draftsFolder = createFolderIfDoesNotExist(connector, DRAFTS_FOLDER_NAME);
            connector.createFolder(draftsFolder, DRAFT_INFORMATION_MESSAGES_FOLDER_NAME, false);
            connector.createFolder(draftsFolder, DRAFT_REQUESTS_FOLDER_NAME, false);
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }
    
    protected void initIfNecessary() throws ConnectorException, RepositoryException {
        if (anyFolderMissed()) {
            initFolders();
        }
    }
    
    private boolean anyFolderMissed() throws ConnectorException {
        return (connector.getFolder(MESSAGES_DB_FOLDER_NAME, GROUPS_FOLDER_NAME) == null || connector.getFolder(MESSAGES_DB_FOLDER_NAME, PROFILES_FOLDER_NAME) == null
                || connector.getFolder(MESSAGES_DB_FOLDER_NAME, PERSONAL_FOLDER_NAME) == null);
    }

    private Folder createFolderIfDoesNotExist(MailMessageConnector connector, String folderName) throws ConnectorException {
        Folder folder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, folderName);
        if (folder == null) {
            folder = connector.createFolder(MESSAGES_DB_FOLDER_NAME, folderName);
        }
        return folder;
    }
}
