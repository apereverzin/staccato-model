package com.creek.staccato.repository.email;

import java.util.Set;

import com.creek.staccato.connector.mail.MailMessageConnector;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.message.MessageRepository;
import com.creek.staccato.domain.message.generic.AddressedMessage;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class EmailMessageRepository extends AbstractRepository implements MessageRepository {

    public EmailMessageRepository(MailMessageConnector connector) throws RepositoryException {
        this(connector, true);
    }
    
    public EmailMessageRepository(MailMessageConnector connector, boolean init) throws RepositoryException {
        super(connector, init);
    }
    
    @Override
    public <T extends AddressedMessage> T createMessage(T message) {
        return message;
    }
    
    @Override
    public <T extends AddressedMessage> T updateMessage(T message) {
        return message;
    }
    
    @Override
    public <T extends AddressedMessage> T getMessage(MessageKey messageKey) {
        return null;
    }
    
    @Override
    public boolean deleteMessage(MessageKey messageKey) {
        return true;
    }
    
    @Override
    public boolean deleteMessages(Set<MessageKey> messageKeys) {
        return true;
    }
}
