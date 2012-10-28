package com.creek.staccato.connector.test;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.URLName;

import com.sun.mail.pop3.POP3SSLStore;

public class Pop3Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        
        Properties pop3Props = new Properties();
        
        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop3Props.setProperty("mail.pop3.port",  "995");
        pop3Props.setProperty("mail.pop3.socketFactory.port", "995");
        
        URLName url = new URLName("pop3", "pop.gmail.com", 995, "",
                "andrey.pereverzin@gmail.com", "bertoluCCi");
        
        Session session = Session.getInstance(pop3Props, null);
        POP3SSLStore store = new POP3SSLStore(session, url);
        try {
            store.connect();
            Folder folder = store.getDefaultFolder();
            Folder[] folders = folder.list();
            for(int i = 0; i < folders.length; i++) {
                System.out.println(folders[i].getName());
                folders[i].open(Folder.READ_ONLY);
                try {
                    Message[] messages = folders[i].getMessages();
                    for(int j = 0; j < messages.length; j++) {
                        System.out.println(messages[i].getSubject());
                        System.out.println(messages[i].getFrom()[0]);
                    }
                } finally {
                    folders[i].close(false);
                }
            }
        } catch(MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
