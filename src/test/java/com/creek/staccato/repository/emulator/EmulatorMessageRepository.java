package com.creek.staccato.repository.emulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.creek.staccato.domain.message.InformationMessage;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.message.MessageRepository;
import com.creek.staccato.domain.message.generic.AddressedMessage;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class EmulatorMessageRepository implements MessageRepository {
    private Map<MessageKey, ? super AddressedMessage> messages = new HashMap<MessageKey, AddressedMessage>();
    
    public <T extends AddressedMessage> T createMessage(T message) throws RepositoryException {
        if(messages.get(message.getMessageKey()) != null) {
            throw new RepositoryException(message.getMessageKey() + " already exists");
        }
        messages.put(message.getMessageKey(), message);
        return message;
    }
    
    public <T extends AddressedMessage> T updateMessage(T message) throws RepositoryException {
        if(messages.get(message.getMessageKey()) == null) {
            throw new RepositoryException(message.getMessageKey() + " does not exist");
        }
        messages.put(message.getMessageKey(), message);
        return message;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends AddressedMessage> T  getMessage(MessageKey messageKey) {
        return (T) messages.get(messageKey);
    }

    public boolean deleteMessage(MessageKey messageKey) {
        return true;
    }

    public boolean deleteMessages(Set<MessageKey> messageKeys) {
        return true;
    }
    
    public void saveInformationMessage(InformationMessage message) throws RepositoryException {
        
    }
    
    public void saveInformationMessages(Set<InformationMessage> messages) throws RepositoryException {
        
    }
}
