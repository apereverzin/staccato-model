package com.creek.staccato.repository.email;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.creek.staccato.connector.mail.ConnectorException;
import com.creek.staccato.connector.mail.MailMessageConnector;
import com.creek.staccato.domain.message.generic.Transformable;
import com.creek.staccato.domain.repositorymessage.RepositoryException;
import com.creek.staccato.domain.repositorymessage.RepositoryMessage;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public abstract class AbstractHierarchyRepository<T extends RepositoryMessage> extends AbstractRepository {
    public AbstractHierarchyRepository(MailMessageConnector connector, boolean init) throws RepositoryException {
        super(connector, init);
    }

    protected Folder getFolderForData(Folder parentFolder, int dataCode, int maxLevel, int initialBase) throws RepositoryException {
        try {
            initIfNecessary();
            int divisor = initialBase;
            // get the initial possible folder name for the data
            int currentFolderSuffixInt = getPositiveRemainder(dataCode, divisor);
            String folderName = Integer.toString(currentFolderSuffixInt);
            if (parentFolder.list().length == 0) {
                return null;
            }
            // build the folder name for the data until the folder is found
            int levelCount = 0;
            Folder folder = null;
            while (folder == null && levelCount < maxLevel) {
                folder = connector.getFolder(parentFolder, folderName);
                // is folder found?
                if (folder == null) {
                    // no; double the divisor
                    divisor *= 2;
                    // and add suffix to the folder name
                    currentFolderSuffixInt = getPositiveRemainder(dataCode, divisor);
                    folderName += "-" + currentFolderSuffixInt;
                    levelCount++;
                } else {
                    return folder;
                }
            }
            return null;
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    protected void saveData(T repositoryData, Transformable key, String parentFolderName, int maxLevel, int maxFolderSize, int divisor) throws RepositoryException {
        try {
            initIfNecessary();
            // get the initial possible folder name for the data
            int currentFolderSuffixInt = getPositiveRemainder(getCode(key), divisor);
            String folderName = Integer.toString(currentFolderSuffixInt);
            if (connector.getFolder(MESSAGES_DB_FOLDER_NAME, parentFolderName) == null || connector.getFolder(MESSAGES_DB_FOLDER_NAME, parentFolderName).list().length == 0) {
                initFolders();
            }
            // build the folder name for the data until the folder is found
            Folder dataFolder = connector.getFolder(MESSAGES_DB_FOLDER_NAME, parentFolderName);
            Folder folder = getFolderForData(dataFolder, getCode(key), maxLevel, divisor);
            // message already exists?
            if (connector.getMessageBySubject(folder, key.toJSON().toString()) != null) {
                // yes; do nothing
                return;
            }

            // is there too many data messages in it?
            if (folder.getMessageCount() >= maxFolderSize) {
                currentFolderSuffixInt = getPositiveRemainder(getCode(key), divisor);
                // yes; split the folder
                connector.renameFolder(dataFolder, folder, folderName + "-" + currentFolderSuffixInt);
                Folder newFolder = connector.createFolder(dataFolder, folderName + "-" + (currentFolderSuffixInt + divisor), false);
                folder = connector.getFolder(dataFolder, folderName + "-" + currentFolderSuffixInt);
                folder.open(Folder.READ_WRITE);

                // sorting messages between two folders
                divisor *= 2;
                Message[] msgs = folder.getMessages();
                for (int i = 0; i < msgs.length; i++) {
                    if (getPositiveRemainder(getCode(key), divisor) == currentFolderSuffixInt) {
                        // leave in the old folder
                    } else {
                        folder.copyMessages(new Message[] { msgs[i] }, newFolder);
                        msgs[i].setFlag(Flags.Flag.DELETED, true);
                    }
                }
                folder.close(true);
                folderName += "-" + currentFolderSuffixInt;
            }
            folder.open(Folder.READ_WRITE);
            connector.putRepositoryMessageToFolderWithUniqueSubject(folder, repositoryData, key.toJSON().toString());
        } catch (MessagingException ex) {
            throw new RepositoryException(ex);
        } catch (ConnectorException ex) {
            throw new RepositoryException(ex);
        }
    }

    protected int getPositiveRemainder(int hashCode, int divisor) {
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return hashCode % divisor;
    }

    private int getCode(Transformable key) {
        return key.hashCode();
    }
}
