package com.creek.staccato;

import org.junit.Before;

import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class AbstractAdressedMessageIntegrationTest extends AbstractIntegrationTest{
    protected ProfileKey profileKey1;
    protected ProfileKey profileKey2;
    protected Profile profile1;
    protected Profile profile2;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        profileKey1 = new ProfileKey("andrey.pereverzin@gmail.com");
        profileKey2 = new ProfileKey("andrey.pereverzin@yahoo.com");
        profile1 = new Profile(profileKey1);
        profile1.setFirstName("Myfirstname");
        profile1.setLastName("MylastName");
        profile1.setNickName("MyNick");
        profile1.setCountryCode("UK");
        profile1.setMobilePhone("4412322");
        profile1.setComment("my comment");
        profile2 = new Profile(profileKey2);
        profile2.setFirstName("Myfirstname");
        profile2.setLastName("MylastName");
        profile2.setNickName("MyNick");
        profile2.setCountryCode("UK");
        profile2.setMobilePhone("4412322");
        profile2.setComment("my comment");
    }
}
