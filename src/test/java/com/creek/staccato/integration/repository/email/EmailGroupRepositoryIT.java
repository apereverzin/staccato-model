package com.creek.staccato.integration.repository.email;

import org.junit.Test;

import com.creek.staccato.AbstractIntegrationTest;
import com.creek.staccato.domain.group.Group;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.group.GroupRepository;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.repositorymessage.RepositoryException;
import com.creek.staccato.repository.email.EmailGroupRepository;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class EmailGroupRepositoryIT extends AbstractIntegrationTest {
	@Test
	public void testCreateUpdateDeleteProfile() throws RepositoryException {
		GroupRepository groupRepository = new EmailGroupRepository(connector);
		int profileId = (int) System.currentTimeMillis() / 1000000;
		if (profileId < 0) {
			profileId = -profileId;
		}
		Profile profileCreated = groupRepository
				.createProfile(createProfile(profileId));
		assertNotNull(profileCreated);
		assertEquals(profileCreated,
				groupRepository.getProfile(createProfileKey(profileId)));
		profileCreated.setFirstName("newFirstName");
		profileCreated.setLastName("newLastName");
		profileCreated.setNickName("newNickName");
		profileCreated.setMobilePhone("12-34-11");
		profileCreated.setCountryCode("newCountryCode");
		profileCreated.setComment("newComment");
		assertNotNull(groupRepository.updateProfile(profileCreated));
		Profile profileUpdated = groupRepository
				.getProfile(createProfileKey(profileId));
		assertEquals(profileCreated, profileUpdated);
		assertEquals(profileCreated.getFirstName(),
				profileUpdated.getFirstName());
		assertEquals(profileCreated.getLastName(), profileUpdated.getLastName());
		assertEquals(profileCreated.getNickName(), profileUpdated.getNickName());
		assertEquals(profileCreated.getMobilePhone(),
				profileUpdated.getMobilePhone());
		assertEquals(profileCreated.getCountryCode(),
				profileUpdated.getCountryCode());
		assertEquals(profileCreated.getComment(), profileUpdated.getComment());
		assertTrue(groupRepository
				.deleteProfile(profileCreated.getProfileKey()));
		assertNull(groupRepository.getProfile(createProfileKey(profileId)));
	}

	@Test
	public void testCreateUpdateDeleteGroup() throws RepositoryException {
		GroupRepository groupRepository = new EmailGroupRepository(connector);
		int profileId = (int) System.currentTimeMillis() / 1000000;
		if (profileId < 0) {
			profileId = -profileId;
		}
		Profile profile = createProfile(profileId);
		GroupKey groupKey = new GroupKey("Group111", profile.getProfileKey());
		Group group = new Group(groupKey);
		group.setDescription("description");
		group.setExcludeVotes(2);
		group.setIncludeVotes(3);
		Group groupCreated = groupRepository.createGroup(group);
		assertNotNull(groupCreated);
		assertEquals(groupCreated, groupRepository.getGroup(groupKey));
		groupCreated.setDescription("newDescription");
		groupCreated.setExcludeVotes(4);
		groupCreated.setIncludeVotes(1);
		assertNotNull(groupRepository.updateGroup(groupCreated));
		Group groupUpdated = groupRepository.getGroup(groupKey);
		assertEquals(groupCreated, groupUpdated);
		assertEquals(groupCreated.getDescription(),
				groupUpdated.getDescription());
		assertEquals(groupCreated.getIncludeVotes(),
				groupUpdated.getIncludeVotes());
		assertEquals(groupCreated.getExcludeVotes(),
				groupUpdated.getExcludeVotes());
		assertTrue(groupRepository.deleteGroup(groupKey));
		assertNull(groupRepository.getGroup(groupKey));
	}

	@Test
	public void testGetProfiles() throws RepositoryException {
		GroupRepository groupRepository = new EmailGroupRepository(connector);
		assertNotNull(groupRepository.getProfile(createProfileKey(25)));
		assertNotNull(groupRepository.getProfile(createProfileKey(47)));
		assertNotNull(groupRepository.getProfile(createProfileKey(117)));
		assertNotNull(groupRepository.getProfile(createProfileKey(243)));
		assertNotNull(groupRepository.getProfile(createProfileKey(311)));
		assertNotNull(groupRepository.getProfile(createProfileKey(402)));
	}

	@Test
	public void testAddProfileToGroup() throws RepositoryException {
		GroupRepository groupRepository = new EmailGroupRepository(connector);
		groupRepository.createGroup(group1);
		assertFalse(groupRepository.getGroupProfileKeys(group1.getGroupKey())
				.contains(profile1.getProfileKey()));
		groupRepository.addProfileToGroup(group1.getGroupKey(),
				profile1.getProfileKey());
		assertTrue(groupRepository.getGroupProfileKeys(group1.getGroupKey())
				.contains(profile1.getProfileKey()));
		groupRepository.removeProfileFromGroup(group1.getGroupKey(),
				profile1.getProfileKey());
		assertFalse(groupRepository.getGroupProfileKeys(group1.getGroupKey())
				.contains(profile1.getProfileKey()));
	}

	@Test
	public void testCreateMyProfile() throws RepositoryException {
		GroupRepository groupRepository = new EmailGroupRepository(connector);
		if (groupRepository.createMyProfile(myProfile) == null) {
			groupRepository.updateMyProfile(myProfile);
		}
		Profile profile = groupRepository.getMyProfile();
		assertEquals(profile, myProfile);
		assertEquals(profile.getFirstName(), myProfile.getFirstName());
		assertEquals(profile.getLastName(), myProfile.getLastName());
		assertEquals(profile.getNickName(), myProfile.getNickName());
		assertEquals(profile.getMobilePhone(), myProfile.getMobilePhone());
		assertEquals(profile.getComment(), myProfile.getComment());

		myProfile.setFirstName("Myfirstname1");
		myProfile.setLastName("MylastName1");
		myProfile.setNickName("MyNick1");
		myProfile.setCountryCode("UK1");
		myProfile.setMobilePhone("44123221");
		myProfile.setComment("my comment1");
		groupRepository.updateMyProfile(myProfile);
		profile = groupRepository.getMyProfile();
		assertEquals(profile, myProfile);
		assertEquals(profile.getFirstName(), myProfile.getFirstName());
		assertEquals(profile.getLastName(), myProfile.getLastName());
		assertEquals(profile.getNickName(), myProfile.getNickName());
		assertEquals(profile.getMobilePhone(), myProfile.getMobilePhone());
		assertEquals(profile.getComment(), myProfile.getComment());
	}
}
