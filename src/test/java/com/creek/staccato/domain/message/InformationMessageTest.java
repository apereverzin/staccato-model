package com.creek.staccato.domain.message;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.InformationMessage;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class InformationMessageTest extends AbstractRepositoryTest {
    @Test
    public void testTransformInformationMessage() throws ParseException {
        long timestamp = System.currentTimeMillis();
        Set<GroupKey> groupsTo = new HashSet<GroupKey>();
        groupsTo.add(group2.getGroupKey());
        Set<ProfileKey> profilesTo = new HashSet<ProfileKey>();
        profilesTo.add(profile1.getProfileKey());
        MessageKey messageKey = new MessageKey(profile11.getProfileKey(), timestamp);
        InformationMessage message = new InformationMessage("title", "message text text", groupsTo, profilesTo, messageKey, AbstractRepository.VERSION);
        message.getGroupsTo().add(groupKey1);
        message.getGroupsTo().add(groupKey2);

        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		System.out.println(s);
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		InformationMessage messageRes = new InformationMessage(value);
		assertEquals(message.getMessageKey(), messageRes.getMessageKey());
		assertEquals(message.getText(), messageRes.getText());
		assertEquals(message.getGroupsTo().size(), messageRes.getGroupsTo()
				.size());
		assertTrue(message.getGroupsTo().containsAll(messageRes.getGroupsTo()));
		assertEquals(message.getProfilesTo().size(), messageRes.getProfilesTo()
				.size());
		assertTrue(message.getProfilesTo().containsAll(
				messageRes.getProfilesTo()));
		assertEquals(message.getGroupsTo(), messageRes.getGroupsTo());
		assertEquals(message.getProductVersion(),
				messageRes.getProductVersion());
    }
}
