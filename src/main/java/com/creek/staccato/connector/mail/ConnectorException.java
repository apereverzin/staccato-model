package com.creek.staccato.connector.mail;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class ConnectorException extends Exception {
    private static final long serialVersionUID = 34783772L;
    
    public ConnectorException(String msg) {
        super(msg);
    }
    
    public ConnectorException(Throwable ex) {
        super(ex);
    }
}
