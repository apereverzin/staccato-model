package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.repositorymessage.RepositoryProfile;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class RepositoryProfileTest extends AbstractRepositoryTest {
    @Test
    public void testTransformInformationMessage() throws ParseException {
        RepositoryProfile message = new RepositoryProfile(profile1, AbstractRepository.VERSION);
        
        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
        JSONParser parser = new JSONParser();
		JSONTransformer transformer = new JSONTransformer();
		System.out.println(s);
		parser.parse(s, transformer);

		JSONObject value = (JSONObject) transformer.getResult();

		RepositoryProfile messageRes = new RepositoryProfile(value);
		assertEquals(message.getData(), messageRes.getData());
		assertEquals(message.getProductVersion(),
				messageRes.getProductVersion());
    }
}
