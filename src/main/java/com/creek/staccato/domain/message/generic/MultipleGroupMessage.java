package com.creek.staccato.domain.message.generic;

import java.util.Set;

import com.creek.staccato.domain.group.GroupKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface MultipleGroupMessage extends MultipleProfileMessage {
    public Set<GroupKey> getGroupsTo();
}
