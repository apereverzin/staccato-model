package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.profile.ProfileInformationMessages;
import com.creek.staccato.domain.repositorymessage.RepositoryProfile;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class RepositoryProfileInformationMessagesTest extends AbstractRepositoryTest {
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
    public void testTransformInformationMessage() throws ParseException {
		RepositoryProfileInformationMessages message = new RepositoryProfileInformationMessages(profileInformationMessages, AbstractRepository.VERSION);
        
        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		System.out.println(s);
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		RepositoryProfileInformationMessages messageRes = new RepositoryProfileInformationMessages(value);
		assertEquals(message.getData(), messageRes.getData());
		assertEquals(message.getProductVersion(),
				messageRes.getProductVersion());
    }
}
