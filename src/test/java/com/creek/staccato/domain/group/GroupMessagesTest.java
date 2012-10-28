package com.creek.staccato.domain.group;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.util.JSONTransformer;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class GroupMessagesTest extends AbstractRepositoryTest {
	private GroupMessages groupMessages;
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		groupMessages = new GroupMessages(groupKey1);
		groupMessages.getInformationMessageKeys().add(messageKey1);
		groupMessages.getInformationMessageKeys().add(messageKey2);
	}
	
    @Test
    public void testTransformGroupMessages() throws ParseException {
        JSONObject jsonGroupMessages = groupMessages.toJSON();
        String s = jsonGroupMessages.toString();

        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		GroupMessages groupMessagesRes = new GroupMessages(value);
		assertEquals(groupMessages.getGroupKey(),
				groupMessagesRes.getGroupKey());
		assertEquals(2, groupMessagesRes.getInformationMessageKeys().size());
        assertTrue(groupMessagesRes.getInformationMessageKeys().contains(messageKey1));
        assertTrue(groupMessagesRes.getInformationMessageKeys().contains(messageKey2));
    }
}
