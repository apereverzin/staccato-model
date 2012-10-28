package com.creek.staccato.domain.repositorymessage;

import com.creek.staccato.domain.message.generic.GenericMessage;
import com.creek.staccato.domain.message.generic.Transformable;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface RepositoryMessage extends GenericMessage {
    Transformable getData();
}
