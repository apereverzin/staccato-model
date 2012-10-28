package com.creek.staccato.connector.mail;

import org.junit.Test;

import com.creek.staccato.AbstractIntegrationTest;
import com.creek.staccato.connector.mail.ConnectorException;

import static com.creek.staccato.repository.email.EmailGroupRepository.*;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class MailMessageConnectorTest extends AbstractIntegrationTest {
    @Test
    public void testCreateParentFolder() throws ConnectorException {
            assertNotNull(connector.createFolder("Folder1"));
    }
    
    @Test
    public void testCreateFolder() throws ConnectorException {
            connector.createFolder(GROUPS_FOLDER_NAME, "Groups1");
            connector.createFolder(GROUPS_FOLDER_NAME, "Groups2");
            connector.createFolder(GROUPS_FOLDER_NAME, "Groups3");
    }
    
    @Test
    public void testDeleteFolder() throws ConnectorException {
            connector.deleteFolder(GROUPS_FOLDER_NAME, "Groups1");
            connector.deleteFolder(GROUPS_FOLDER_NAME, "Groups2");
            connector.deleteFolder(GROUPS_FOLDER_NAME, "Groups3");
    }
    
    @Test
    public void testGetChildFolders() throws ConnectorException {
		String[] folderNames = connector.getChildFolders(GROUPS_FOLDER_NAME);
		for (int i = 0; i < folderNames.length; i++) {
			System.out.println(folderNames[i]);
		}
    }
    
    @Test
    public void testSendGroupMembershipInvitationRequest() throws ConnectorException {
		String[] folderNames = connector.getChildFolders(GROUPS_FOLDER_NAME);
		for (int i = 0; i < folderNames.length; i++) {
			System.out.println(folderNames[i]);
		}
    }
}
