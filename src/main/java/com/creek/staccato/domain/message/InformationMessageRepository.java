package com.creek.staccato.domain.message;

import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface InformationMessageRepository {
    InformationMessage saveInformationMessage(InformationMessage message) throws RepositoryException;
}
