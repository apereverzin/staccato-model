package com.creek.staccato.domain.servicemessage;

import java.util.Collection;
import java.util.HashSet;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.servicemessage.ProfileAddedToGroup;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class ProfileCreatedTest extends AbstractRepositoryTest {
    @Test
    public void testTransformInformationMessage() throws ParseException {
        long timestamp = System.currentTimeMillis();
        Collection<GroupKey> groupsTo = new HashSet<GroupKey>();
        groupsTo.add(group2.getGroupKey());
        Collection<ProfileKey> profilesTo = new HashSet<ProfileKey>();
        profilesTo.add(profile1.getProfileKey());
        MessageKey messageKey = new MessageKey(profile11.getProfileKey(), timestamp);
        ProfileAddedToGroup message = new ProfileAddedToGroup(profile1, groupKey1, messageKey, AbstractRepository.VERSION);

        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
		JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		System.out.println(s);
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		ProfileAddedToGroup messageRes = new ProfileAddedToGroup(value);
		assertEquals(message.getProfile(), messageRes.getProfile());
		assertEquals(message.getGroupTo(), messageRes.getGroupTo());
		assertEquals(message.getMessageKey(), messageRes.getMessageKey());
		assertEquals(message.getProductVersion(),
				messageRes.getProductVersion());
    }
}
