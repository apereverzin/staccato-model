package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.AbstractGenericMessage;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractRepositoryMessage extends AbstractGenericMessage implements RepositoryMessage {

    public AbstractRepositoryMessage(String productVersion) {
        super(productVersion);
    }

    public AbstractRepositoryMessage(JSONObject jsonObject) {
        super(jsonObject);
    }
}
