package com.creek.staccato.connector.mail.stress;

import org.junit.Test;

import com.creek.staccato.AbstractIntegrationTest;
import com.creek.staccato.domain.repositorymessage.RepositoryException;
import com.creek.staccato.repository.email.EmailGroupRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class StressMailMessageConnectorTest extends AbstractIntegrationTest {
    @Test
    public void testOneThousandProfiles() throws RepositoryException {
		EmailGroupRepository repository = new EmailGroupRepository(connector);
		int count = 0;
		while (count < 1000) {
			repository.createProfile(createProfile(count));
			System.out.println("i: " + count);
			count++;
		}
    }
}
