package com.creek.staccato.domain.profile;

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
public class ProfileInformationMessagesTest extends AbstractRepositoryTest {
	private ProfileInformationMessages profileInformationMessages;
	private long timestamp1 = System.currentTimeMillis();
	private long timestamp2 = System.currentTimeMillis() + 10000;
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		profileInformationMessages = new ProfileInformationMessages(profile1.getProfileKey());
		profileInformationMessages.getInformationMessageKeys().add(timestamp1);
		profileInformationMessages.getInformationMessageKeys().add(timestamp2);
	}
	
    @Test
    public void testTransformGroupMessages() throws ParseException {
        JSONObject jsonProfileMessages = profileInformationMessages.toJSON();
        String s = jsonProfileMessages.toString();

        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		ProfileInformationMessages profileMessagesRes = new ProfileInformationMessages(value);
		assertEquals(profileInformationMessages.getProfileKey(),
				profileMessagesRes.getProfileKey());
		assertEquals(2, profileMessagesRes.getInformationMessageKeys().size());
        assertTrue(profileMessagesRes.getInformationMessageKeys().contains(timestamp1));
        assertTrue(profileMessagesRes.getInformationMessageKeys().contains(timestamp2));
    }
}
