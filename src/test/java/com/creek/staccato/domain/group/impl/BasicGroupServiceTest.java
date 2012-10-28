package com.creek.staccato.domain.group.impl;

import org.junit.Test;

import com.creek.staccato.domain.BusinessException;
import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.group.GroupService;
import com.creek.staccato.domain.group.impl.GroupServiceImpl;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.repository.emulator.EmulatorGroupRepository;

import junit.framework.TestCase;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class BasicGroupServiceTest extends TestCase {
    private GroupService groupService;
    
    @Override
    public void setUp() {
        groupService = new GroupServiceImpl();
        groupService.setGroupRepository(new EmulatorGroupRepository());
    }
    
    @Test
    public void testUpdateGroup() throws BusinessException {
        ProfileKey profileKey = new ProfileKey("aa@bb.cc");
        GroupKey groupKey = new GroupKey("GROUP1", profileKey);
        Group group = new Group(groupKey);
        group.setDescription("Descr");
        Group group1 = groupService.createGroup(group);
        assertEquals(group1.getDescription(), group.getDescription());
        assertEquals(group1.getIncludeVotes(), group.getIncludeVotes());
        assertEquals(group1.getExcludeVotes(), group.getExcludeVotes());

        group.setDescription("Descr1");
        group.setIncludeVotes(7);
        group.setExcludeVotes(8);
        Group group2 = groupService.updateGroup(group);
        assertEquals(group2.getDescription(), group.getDescription());
        assertEquals(group2.getIncludeVotes(), group.getIncludeVotes());
        assertEquals(group2.getExcludeVotes(), group.getExcludeVotes());

        assertTrue(groupService.deleteGroup(groupKey));
    }
}
