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
    public void testCreateParentFolder() {
        try {
            assertNotNull(connector.createFolder("Folder1"));
        } catch(ConnectorException ex) {
            fail();
        }
    }
    
    @Test
    public void testCreateFolder() {
        try {
            connector.createFolder(GROUPS_FOLDER_NAME, "Groups1");
            connector.createFolder(GROUPS_FOLDER_NAME, "Groups2");
            connector.createFolder(GROUPS_FOLDER_NAME, "Groups3");
        } catch(ConnectorException ex) {
            fail();
        }
    }
    
    @Test
    public void testDeleteFolder() {
        try {
            connector.deleteFolder(GROUPS_FOLDER_NAME, "Groups1");
            connector.deleteFolder(GROUPS_FOLDER_NAME, "Groups2");
            connector.deleteFolder(GROUPS_FOLDER_NAME, "Groups3");
        } catch(ConnectorException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetChildFolders() {
        try {
            String[] folderNames = connector.getChildFolders(GROUPS_FOLDER_NAME);
            for(int i = 0; i < folderNames.length; i++) {
                System.out.println(folderNames[i]);
            }
        } catch(ConnectorException ex) {
            fail();
        }
    }
    
    @Test
    public void testSendGroupMembershipInvitationRequest() {
        try {
            String[] folderNames = connector.getChildFolders(GROUPS_FOLDER_NAME);
            for(int i = 0; i < folderNames.length; i++) {
                System.out.println(folderNames[i]);
            }
        } catch(ConnectorException ex) {
            fail();
        }
    }
}
