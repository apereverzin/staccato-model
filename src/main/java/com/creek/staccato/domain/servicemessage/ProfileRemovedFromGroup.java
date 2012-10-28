package com.creek.staccato.domain.servicemessage;

import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.message.generic.MultipleGroupMessage;
import com.creek.staccato.domain.profile.Profile;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface ProfileRemovedFromGroup extends MultipleGroupMessage {
    public Group getGroup();
    public Profile getProfile();
}
