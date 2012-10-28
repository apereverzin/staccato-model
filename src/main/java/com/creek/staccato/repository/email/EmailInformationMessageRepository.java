package com.creek.staccato.repository.email;

import com.creek.staccato.connector.mail.MailMessageConnector;
import com.creek.staccato.domain.message.InformationMessage;
import com.creek.staccato.domain.message.InformationMessageRepository;
import com.creek.staccato.domain.repositorymessage.RepositoryException;
import com.creek.staccato.domain.repositorymessage.RepositoryInformationMessage;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class EmailInformationMessageRepository extends AbstractHierarchyRepository<RepositoryInformationMessage> implements InformationMessageRepository {
    private static final int MAX_MESSAGES_FOLDER_SIZE = 20;
    private static final int MAX_MESSAGES_LEVEL_NUMBER = 4;

    public EmailInformationMessageRepository(MailMessageConnector connector) throws RepositoryException {
        this(connector, true);
    }

    public EmailInformationMessageRepository(MailMessageConnector connector, boolean init) throws RepositoryException {
        super(connector, init);
    }

    @Override
    public InformationMessage saveInformationMessage(InformationMessage message) throws RepositoryException {
        saveData(new RepositoryInformationMessage(message, VERSION), message.getMessageKey(), GROUPS_INFORMATION_MESSAGES_FOLDER_NAME, MAX_MESSAGES_LEVEL_NUMBER, MAX_MESSAGES_FOLDER_SIZE, INITIAL_MESSAGES_BASE);

        return message;
    }
}
