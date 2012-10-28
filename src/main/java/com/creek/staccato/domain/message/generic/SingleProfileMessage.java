package com.creek.staccato.domain.message.generic;

import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface SingleProfileMessage extends AddressedMessage {
    public ProfileKey getProfileTo();
}
