package com.creek.staccato.domain.message;

import java.util.Set;

import com.creek.staccato.domain.message.generic.AddressedMessage;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface MessageRepository {
    <T extends AddressedMessage> T createMessage(T message) throws RepositoryException;
    <T extends AddressedMessage> T updateMessage(T message) throws RepositoryException;
    <T extends AddressedMessage> T getMessage(MessageKey messageKey) throws RepositoryException;

    boolean deleteMessage(MessageKey messageKey) throws RepositoryException;
    boolean deleteMessages(Set<MessageKey> messageKeys) throws RepositoryException;
}
