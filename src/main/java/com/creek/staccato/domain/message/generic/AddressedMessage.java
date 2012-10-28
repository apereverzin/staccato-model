package com.creek.staccato.domain.message.generic;

import com.creek.staccato.domain.message.MessageKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface AddressedMessage extends GenericMessage {
    MessageKey getMessageKey();
}