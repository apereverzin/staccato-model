package com.creek.staccato.domain;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class BusinessException extends Exception {
    private static final long serialVersionUID = 34781622L;
    
    public BusinessException(String msg) {
        super(msg);
    }
    
    public BusinessException(Exception ex) {
        super(ex);
    }
}
