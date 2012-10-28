package com.creek.staccato.domain.message.generic;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface Request extends MultipleProfileMessage {

    public String getRequestText();
}
