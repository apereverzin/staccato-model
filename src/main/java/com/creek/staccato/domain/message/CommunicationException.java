package com.creek.staccato.domain.message;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class CommunicationException extends Exception {
    public CommunicationException(String msg) {
        super(msg);
    }
    
    public CommunicationException(Throwable ex) {
        super(ex);
    }
}
