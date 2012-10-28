package com.creek.staccato.domain.message.generic;

import com.creek.staccato.domain.group.GroupKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface SingleGroupMessage extends AddressedMessage {
    public GroupKey getGroupTo();
}
