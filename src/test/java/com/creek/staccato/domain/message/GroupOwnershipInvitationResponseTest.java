package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.message.GroupOwnershipInvitationResponse;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class GroupOwnershipInvitationResponseTest extends AbstractRepositoryTest {
    @Test
    public void testTransformGroupOwnerInvitationResponseTest() throws ParseException {
        long timestamp = System.currentTimeMillis();
        MessageKey messageKey = new MessageKey(profile11.getProfileKey(), timestamp);
        MessageKey messageKey0 = new MessageKey(profile1.getProfileKey(), timestamp);
        GroupOwnershipInvitationResponse message = new GroupOwnershipInvitationResponse(messageKey0, "text", true, messageKey, AbstractRepository.VERSION);
        
        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		System.out.println(s);
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		GroupOwnershipInvitationResponse messageRes = new GroupOwnershipInvitationResponse(
				value);
		assertEquals(message.getRequestKey(), messageRes.getRequestKey());
		assertEquals(message.getResponseText(), messageRes.getResponseText());
		assertEquals(message.isPositive(), messageRes.isPositive());
		assertEquals(message.getMessageKey(), messageRes.getMessageKey());
		assertEquals(message.getProductVersion(),
				messageRes.getProductVersion());
    }
}
