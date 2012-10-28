package com.creek.staccato.domain.servicemessage;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.servicemessage.ProfileDeleted;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class ProfileDeletedTest extends AbstractRepositoryTest {
    @Test
    public void testTransformProfileDeleted() {
        long timestamp = System.currentTimeMillis();
        Set<GroupKey> groupsTo = new HashSet<GroupKey>();
        groupsTo.add(group2.getGroupKey());
        Set<ProfileKey> profilesTo = new HashSet<ProfileKey>();
        profilesTo.add(profile1.getProfileKey());
        MessageKey messageKey = new MessageKey(profile11.getProfileKey(), timestamp);
        ProfileDeleted message = new ProfileDeleted(profile1.getProfileKey(), groupsTo, profilesTo, messageKey, AbstractRepository.VERSION);
        message.getGroupsTo().add(groupKey1);
        message.getGroupsTo().add(groupKey2);

        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
        JSONParser parser = new JSONParser();
        try {
            JSONTransformer transformer = new JSONTransformer();
            System.out.println(s);
            parser.parse(s, transformer);

            JSONObject value = (JSONObject) transformer.getResult();

            ProfileDeleted messageRes = new ProfileDeleted(value);
            assertEquals(message.getProfileKey(), messageRes.getProfileKey());
            assertEquals(message.getGroupsTo().size(), messageRes.getGroupsTo().size());
            assertTrue(message.getGroupsTo().containsAll(messageRes.getGroupsTo()));
            assertEquals(message.getProfilesTo().size(), messageRes.getProfilesTo().size());
            assertTrue(message.getProfilesTo().containsAll(messageRes.getProfilesTo()));
            assertEquals(message.getMessageKey(), messageRes.getMessageKey());
            assertEquals(message.getGroupsTo(), messageRes.getGroupsTo());
            assertEquals(message.getProductVersion(), messageRes.getProductVersion());
        } catch (ParseException ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
