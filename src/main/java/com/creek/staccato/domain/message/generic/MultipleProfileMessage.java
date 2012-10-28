package com.creek.staccato.domain.message.generic;

import java.util.Set;

import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface MultipleProfileMessage extends AddressedMessage {
    public Set<ProfileKey> getProfilesTo();
}
