package com.creek.staccato.repository.email;

import java.util.Set;

import com.creek.staccato.connector.mail.ConnectorException;
import com.creek.staccato.connector.mail.MailMessageConnector;
import com.creek.staccato.connector.mail.MailMessageConnector.Result;
import com.creek.staccato.domain.message.CommunicationException;
import com.creek.staccato.domain.message.MessageCommunicator;
import com.creek.staccato.domain.message.generic.AddressedMessage;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class MailMessageCommunicator implements MessageCommunicator {
    private MailMessageConnector connector;

    public MailMessageCommunicator(MailMessageConnector connector) throws RepositoryException {
        this.connector = connector;
    }
    
    public Result sendMessage(AddressedMessage message) throws CommunicationException {
        try {
            return connector.sendMessage(message);
        } catch (ConnectorException ex) {
            throw new CommunicationException(ex);
        }
    }

    public Set<? extends AddressedMessage> receiveMessages() throws CommunicationException {
        try {
            return connector.receiveMessages();
        } catch (ConnectorException ex) {
            throw new CommunicationException(ex);
        }
    }

    public Set<? extends AddressedMessage> viewNewInformationMessages() throws CommunicationException {
        try {
            return connector.receiveMessages();
        } catch (ConnectorException ex) {
            throw new CommunicationException(ex);
        }
    }

    public Set<? extends AddressedMessage> viewPendingRequests() throws CommunicationException {
        try {
            return connector.receiveMessages();
        } catch (ConnectorException ex) {
            throw new CommunicationException(ex);
        }
    }
}
