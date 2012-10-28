package com.creek.staccato;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;

import com.creek.staccato.connector.mail.MailMessageConnector;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class AbstractIntegrationTest extends AbstractRepositoryTest {
    protected MailMessageConnector connector;
    protected MailMessageConnector connector1;
    protected MailMessageConnector connector2;
    protected MailMessageConnector connector3;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
		Properties props1 = new Properties();
		props1.load(new FileInputStream(
				"E:\\Work\\staccato-model\\src\\main\\res\\mail1.properties"));
		connector1 = new MailMessageConnector(props1);
		Properties props2 = new Properties();
		props2.load(new FileInputStream(
				"E:\\Work\\staccato-model\\src\\main\\res\\mail2.properties"));
		connector2 = new MailMessageConnector(props2);
		Properties props3 = new Properties();
		props3.load(new FileInputStream(
				"E:\\Work\\staccato-model\\src\\main\\res\\mail3.properties"));
		connector3 = new MailMessageConnector(props3);

		connector = connector3;
    }
}
