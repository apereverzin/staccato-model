package com.creek.staccato.integration.repository.email;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.creek.staccato.AbstractAdressedMessageIntegrationTest;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.CommunicationException;
import com.creek.staccato.domain.message.GroupMembershipInvitationRequest;
import com.creek.staccato.domain.message.InformationMessage;
import com.creek.staccato.domain.message.MessageCommunicator;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.message.generic.AddressedMessage;
import com.creek.staccato.domain.message.generic.Request;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.repositorymessage.RepositoryException;
import com.creek.staccato.repository.email.AbstractRepository;
import com.creek.staccato.repository.email.MailMessageCommunicator;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class EmailMessageRepositoryIT extends
        AbstractAdressedMessageIntegrationTest {
    @Test
    public void tes1tSendReceiveInformationMessage() {
        try {
            MessageCommunicator communicator1 = new MailMessageCommunicator(
                    connector1);
            MessageCommunicator communicator2 = new MailMessageCommunicator(
                    connector2);
            MailNode node1 = new MailNode(communicator1, profileKey1);
            MailNode node2 = new MailNode(communicator2, profileKey2);
            
            MailNode nodeFrom = node1;
            MailNode nodeTo = node2;
            
            long timestamp = System.currentTimeMillis();

            MessageKey messageKey = new MessageKey(nodeFrom.getProfileKey(), timestamp);
            Set<GroupKey> groupsTo = new HashSet<GroupKey>();
            Set<ProfileKey> profilesTo = new HashSet<ProfileKey>();
            profilesTo.add(nodeTo.getProfileKey());
            InformationMessage message = new InformationMessage("title", "text" + timestamp,
                    groupsTo, profilesTo, messageKey, AbstractRepository.VERSION);
            nodeFrom.getMessageCommunicator().sendMessage(message);
            
            try {
                Thread.sleep(10000);
            } catch(InterruptedException ex) {
                //
            }
            
            Set<? extends AddressedMessage> messages = nodeTo.getMessageCommunicator().receiveMessages();
            assertEquals(1, messages.size());
        } catch (CommunicationException ex) {
            ex.printStackTrace();
            fail();
        } catch (RepositoryException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSendReceiveAddressedMesages() {
        try {
            MessageCommunicator communicator1 = new MailMessageCommunicator(connector1);
            MessageCommunicator communicator2 = new MailMessageCommunicator(connector2);
            MailNode node1 = new MailNode(communicator1, profileKey1);
            MailNode node2 = new MailNode(communicator2, profileKey2);
            
            MailNode nodeFrom = node2;
            MailNode nodeTo = node1;
            
            long timestamp = System.currentTimeMillis();

            MessageKey messageKey = new MessageKey(nodeFrom.getProfileKey(), timestamp);
            Set<GroupKey> groupsTo = new HashSet<GroupKey>();
            Set<ProfileKey> profilesTo = new HashSet<ProfileKey>();
            profilesTo.add(nodeTo.getProfileKey());
            InformationMessage message = new InformationMessage("title", "text" + timestamp,
                    groupsTo, profilesTo, messageKey, AbstractRepository.VERSION);
            Request request = new GroupMembershipInvitationRequest(groupKey1, profilesTo, "text" + timestamp, messageKey, 
                    AbstractRepository.VERSION);
            nodeFrom.getMessageCommunicator().sendMessage(message);
            nodeFrom.getMessageCommunicator().sendMessage(request);
            
            try {
                Thread.sleep(10000);
            } catch(InterruptedException ex) {
                //
            }
            
            Set<? extends AddressedMessage> messages = nodeTo.getMessageCommunicator().receiveMessages();
            assertEquals(2, messages.size());
        } catch (CommunicationException ex) {
            ex.printStackTrace();
            fail();
        } catch (RepositoryException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    private class MailNode {
        private MessageCommunicator messageCommunicator;
        private ProfileKey profileKey;

        public MailNode(MessageCommunicator messageCommunicator,
                ProfileKey profileKey) {
            this.messageCommunicator = messageCommunicator;
            this.profileKey = profileKey;
        }

        public MessageCommunicator getMessageCommunicator() {
            return messageCommunicator;
        }

        public ProfileKey getProfileKey() {
            return profileKey;
        }
    }
}
