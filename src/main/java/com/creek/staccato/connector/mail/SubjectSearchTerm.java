package com.creek.staccato.connector.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class SubjectSearchTerm extends SearchTerm {
    private static final long serialVersionUID = 39029482l;
    
    private String subj;
    
    public SubjectSearchTerm(String subj) {
        this.subj= subj;
    }

    @Override
    public boolean match(Message msg) {
        try {
            System.out.println("-----msg: " + msg);
            System.out.println("-----msgNumber: " + msg.getMessageNumber());
            System.out.println("-----contentType: " + msg.getContentType());
            String msgSubj = msg.getSubject();
            return subj.equalsIgnoreCase(msgSubj);
        } catch(MessagingException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
