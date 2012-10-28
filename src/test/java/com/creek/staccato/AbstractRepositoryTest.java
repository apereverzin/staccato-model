package com.creek.staccato;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;

import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.GroupMembershipInvitationRequest;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;

import junit.framework.TestCase;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class AbstractRepositoryTest extends TestCase {
    protected Profile profile1;
    protected Profile profile2;
    protected Profile profile11;
    protected Profile profile12;
    protected Profile profile21;
    protected Profile profile22;
    protected Profile myProfile;
    protected GroupKey groupKey1;
    protected Group group1;
    protected GroupKey groupKey2;
    protected Group group2;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        profile1 = createProfile(1);
        profile2 = createProfile(2);
        profile11 = createProfile(11);
        profile12 = createProfile(21);
        profile21 = createProfile(12);
        profile22 = createProfile(22);

        groupKey1 = new GroupKey("Group1", profile1.getProfileKey());
        group1 = new Group(groupKey1);
        group1.setDescription("desc");
        group1.setExcludeVotes(5);
        group1.setIncludeVotes(3);

        groupKey2 = new GroupKey("Group2", profile2.getProfileKey());
        group2 = new Group(groupKey2);
        group2.setDescription("desc2-2");
        group2.setExcludeVotes(5);
        group2.setIncludeVotes(3);

        myProfile = new Profile(new ProfileKey("my@email.com"));
        myProfile.setFirstName("Myfirstname");
        myProfile.setLastName("MylastName");
        myProfile.setNickName("MyNick");
        myProfile.setCountryCode("UK");
        myProfile.setMobilePhone("4412322");
        myProfile.setComment("my comment");
    }

    protected Profile createProfile(int ind) {
        ProfileKey profileKey = createProfileKey(ind);
        Profile profile = new Profile(profileKey);
        profile.setFirstName("fname" + ind);
        profile.setLastName("lname" + ind);
        profile.setNickName("nname" + ind);
        profile.setCountryCode("UK");
        profile.setMobilePhone("4412322");
        profile.setComment("comment" + ind);
        return profile;
    }

    protected Profile createProfile(String emailAddress) {
        ProfileKey profileKey = createProfileKey(emailAddress);
        Profile profile = new Profile(profileKey);
        profile.setFirstName("fname");
        profile.setLastName("lname");
        profile.setNickName("nname");
        profile.setCountryCode("UK");
        profile.setMobilePhone("4412322");
        profile.setComment("comment");
        return profile;
    }

    protected ProfileKey createProfileKey(int ind) {
        return new ProfileKey("aa" + ind + "@bb.cc");
    }

    protected ProfileKey createProfileKey(String emailAddress) {
        return new ProfileKey(emailAddress);
    }

    protected GroupMembershipInvitationRequest createGroupMembershipInvitationRequest() {
        Set<ProfileKey> profilesTo = new HashSet<ProfileKey>();
        profilesTo.add(profile1.getProfileKey());
        GroupMembershipInvitationRequest req = new GroupMembershipInvitationRequest(
                groupKey1, profilesTo, "Invitation", new MessageKey(profile1
                        .getProfileKey()), "1.0");
        return req;
    }
}
