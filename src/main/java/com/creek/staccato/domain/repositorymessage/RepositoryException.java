package com.creek.staccato.domain.repositorymessage;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class RepositoryException extends Exception {
    private static final long serialVersionUID = 34781622L;
    
    public RepositoryException(String msg) {
        super(msg);
    }
    
    public RepositoryException(Exception ex) {
        super(ex);
    }
}
