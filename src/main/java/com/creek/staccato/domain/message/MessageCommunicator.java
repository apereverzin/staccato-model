package com.creek.staccato.domain.message;

import java.util.Set;

import com.creek.staccato.connector.mail.MailMessageConnector.Result;
import com.creek.staccato.domain.message.generic.AddressedMessage;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface MessageCommunicator {
    Set<? extends AddressedMessage> receiveMessages() throws CommunicationException;
    Result sendMessage(AddressedMessage message) throws CommunicationException;
}