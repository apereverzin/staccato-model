package com.creek.staccato.domain.profile;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.message.GroupMembershipInvitationResponse;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class ProfileTest extends AbstractRepositoryTest {
    @Test
    public void testTransformGroupMembershipInvitationResponseTest() throws ParseException {
        long timestamp = System.currentTimeMillis();
        MessageKey messageKey = new MessageKey(profile11.getProfileKey(), timestamp);
        MessageKey messageKey0 = new MessageKey(profile1.getProfileKey(), timestamp);
        GroupMembershipInvitationResponse message = new GroupMembershipInvitationResponse(messageKey0, "text", true, messageKey, AbstractRepository.VERSION);
        
        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		System.out.println(s);
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		GroupMembershipInvitationResponse messageRes = new GroupMembershipInvitationResponse(
				value);
		assertEquals(message.getRequestKey(), messageRes.getRequestKey());
		assertEquals(message.getResponseText(), messageRes.getResponseText());
		assertEquals(message.isPositive(), messageRes.isPositive());
		assertEquals(message.getMessageKey(), messageRes.getMessageKey());
		assertEquals(message.getProductVersion(),
				messageRes.getProductVersion());
    }
}
