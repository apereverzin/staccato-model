package com.creek.staccato.domain.group;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.util.JSONTransformer;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class GroupTest extends AbstractRepositoryTest {
    @Test
    public void testTransformGroup() {
        JSONObject jsonGroup = group1.toJSON();
        String s = jsonGroup.toString();

        JSONParser parser = new JSONParser();
        try {
            JSONTransformer transformer = new JSONTransformer();
            System.out.println(s);
            parser.parse(s, transformer);
            
            JSONObject value = (JSONObject)transformer.getResult();
            
            Group groupRes = new Group(value);
            assertEquals(group1.getGroupKey(), groupRes.getGroupKey());
            assertEquals(group1.getIncludeVotes(), groupRes.getIncludeVotes());
            assertEquals(group1.getExcludeVotes(), groupRes.getExcludeVotes());
            assertEquals(group1.getDescription(), groupRes.getDescription());
            ProfileKey founderKey1 = groupRes.getGroupKey().getFounderKey();
            assertEquals(profile1.getProfileKey(), founderKey1);
        } catch(ParseException ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
