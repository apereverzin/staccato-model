package com.creek.staccato.domain.message;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.message.GroupMembershipInvitationRequest;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class GroupMembershipInvitationRequestTest extends AbstractRepositoryTest {
    @Test
    public void testTransformGroupMembershipInvitationRequestTest() throws ParseException {
        long timestamp = System.currentTimeMillis();
        MessageKey messageKey = new MessageKey(profile11.getProfileKey(),
                timestamp);
        Set<ProfileKey> profilesTo = new HashSet<ProfileKey>();
        profilesTo.add(profile1.getProfileKey());
        GroupMembershipInvitationRequest message = new GroupMembershipInvitationRequest(
                groupKey1, profilesTo, "text", messageKey, AbstractRepository.VERSION);

        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		System.out.println(s);
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		GroupMembershipInvitationRequest messageRes = new GroupMembershipInvitationRequest(
				value);
		assertEquals(message.getGroupKey(), messageRes.getGroupKey());
		assertEquals(message.getProfilesTo().size(), messageRes.getProfilesTo()
				.size());
		assertTrue(message.getProfilesTo().containsAll(
				messageRes.getProfilesTo()));
		assertEquals(message.getRequestText(), messageRes.getRequestText());
		assertEquals(message.getMessageKey(), messageRes.getMessageKey());
		assertEquals(message.getProductVersion(),
				messageRes.getProductVersion());
    }
}
