package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.repositorymessage.RepositoryGroup;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class RepositoryGroupTest extends AbstractRepositoryTest {
    @Test
    public void testTransformInformationMessage() {
        RepositoryGroup message = new RepositoryGroup(group1, AbstractRepository.VERSION);
        
        JSONObject jsonGroup = message.toJSON();
        String s = jsonGroup.toString();
        JSONParser parser = new JSONParser();
        try {
            JSONTransformer transformer = new JSONTransformer();
            System.out.println(s);
            parser.parse(s, transformer);
            
            JSONObject value = (JSONObject)transformer.getResult();
            
            RepositoryGroup messageRes = new RepositoryGroup(value);
            assertEquals(message.getData(), messageRes.getData());
            assertEquals(message.getProductVersion(), messageRes.getProductVersion());
        } catch(ParseException ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
