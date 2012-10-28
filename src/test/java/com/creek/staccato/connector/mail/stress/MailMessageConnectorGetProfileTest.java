package com.creek.staccato.connector.mail.stress;

import org.junit.Test;

import com.creek.staccato.AbstractIntegrationTest;
import com.creek.staccato.connector.mail.ConnectorException;
import com.creek.staccato.domain.profile.Profile;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.repositorymessage.RepositoryProfile;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class MailMessageConnectorGetProfileTest extends AbstractIntegrationTest {
	@Test
	public void testGetProfile1() throws ConnectorException {
		long tm = System.currentTimeMillis();
		ProfileKey profileKey1 = createProfileKey(150);
		Profile profile1 = createProfile(150);
		RepositoryProfile repositoryProfile1 = (RepositoryProfile) connector
				.getMessageBySubject("Groups2", profileKey1.toJSON().toString());
		assertEquals(profile1, repositoryProfile1.getData());
		System.out.println(System.currentTimeMillis() - tm);
	}

	@Test
	public void testGetProfile50() throws ConnectorException {
		long tm = System.currentTimeMillis();
		ProfileKey profileKey50 = createProfileKey(50);
		Profile profile50 = createProfile(50);
		RepositoryProfile repositoryProfile50 = (RepositoryProfile) connector
				.getMessageBySubject("Groups1", profileKey50.toJSON()
						.toString());
		assertEquals(profile50, repositoryProfile50.getData());
		System.out.println(System.currentTimeMillis() - tm);
	}

	@Test
	public void testGetProfile75() throws ConnectorException {
		long tm = System.currentTimeMillis();
		ProfileKey profileKey75 = createProfileKey(75);
		Profile profile75 = createProfile(75);
		RepositoryProfile repositoryProfile75 = (RepositoryProfile) connector
				.getMessageBySubject("Groups1", profileKey75.toJSON()
						.toString());
		assertEquals(profile75, repositoryProfile75.getData());
		System.out.println(System.currentTimeMillis() - tm);
	}

	@Test
	public void testGetProfile300() throws ConnectorException {
		long tm = System.currentTimeMillis();
		ProfileKey profileKey300 = createProfileKey(300);
		RepositoryProfile repositoryProfile300 = (RepositoryProfile) connector
				.getMessageBySubject("Groups1", profileKey300.toJSON()
						.toString());
		assertNull(repositoryProfile300);
		System.out.println(System.currentTimeMillis() - tm);
	}
}
