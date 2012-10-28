package com.creek.staccato.domain.group;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.util.JSONTransformer;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class GroupInformationMessagesTest extends AbstractRepositoryTest {
	private GroupInformationMessages groupInformationMessages;
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		groupInformationMessages = new GroupInformationMessages(groupKey1);
		groupInformationMessages.getInformationMessageKeys().add(messageKey1);
		groupInformationMessages.getInformationMessageKeys().add(messageKey2);
	}
	
    @Test
    public void testTransformGroupMessages() throws ParseException {
        JSONObject jsonGroupInformationMessages = groupInformationMessages.toJSON();
        String s = jsonGroupInformationMessages.toString();

        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		GroupInformationMessages groupMessagesRes = new GroupInformationMessages(value);
		assertEquals(groupInformationMessages.getGroupKey(),
				groupMessagesRes.getGroupKey());
		assertEquals(2, groupMessagesRes.getInformationMessageKeys().size());
        assertTrue(groupMessagesRes.getInformationMessageKeys().contains(messageKey1));
        assertTrue(groupMessagesRes.getInformationMessageKeys().contains(messageKey2));
    }
}
