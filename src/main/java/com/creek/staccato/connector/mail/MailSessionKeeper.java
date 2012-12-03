package com.creek.staccato.connector.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class MailSessionKeeper {
    private Properties mailProps;
    
    private String smtpHost /* = "smtp.gmail.com" */;
    private int smtpPort /* = 587 */;
    private String imapHost /* = "imap.gmail.com" */;
    private int imapPort = -1;

    private String username;
    private String password;
    private Properties imapProps = new Properties();
    private Properties smtpProps = new Properties();

    private Store imapStore;

    public static final String MAIL_USERNAME = "mail.username";
    public static final String MAIL_PASSWORD = "mail.password";
    public static final String MAIL_STORE_PROTOCOL = "mail.store.protocol";

    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_TRANSPORT_PROTOCOL = "mail.smtp.transport.protocol";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";

    public static final String MAIL_IMAP_HOST = "mail.imap.host";
    public static final String MAIL_IMAP_PORT = "mail.imap.port";

    public MailSessionKeeper(Properties mailProps) {
        this.mailProps = mailProps;
        username = mailProps.getProperty(MAIL_USERNAME);
        password = mailProps.getProperty(MAIL_PASSWORD);

        smtpHost = mailProps.getProperty(MAIL_SMTP_HOST);
        smtpPort = Integer.parseInt(mailProps.getProperty(MAIL_SMTP_PORT));
        
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_HOST);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_PORT);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_TRANSPORT_PROTOCOL);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_AUTH);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_STARTTLS_ENABLE);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_SOCKET_FACTORY_PORT);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_SOCKET_FACTORY_CLASS);

        imapHost = mailProps.getProperty(MAIL_IMAP_HOST);
        try {
            imapPort = Integer.parseInt(mailProps.getProperty(MAIL_IMAP_PORT));
        } catch (NumberFormatException ex) {
            //
        }
        setPropertyIfNotNull(mailProps, imapProps, MAIL_STORE_PROTOCOL);
    }
    
    public Session getSMTPSession() {
        return Session.getInstance(smtpProps,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }
    
    public Store getIMAPStore() throws MessagingException {
        if (imapStore == null || !imapStore.isConnected()) {
            Session imapSession = Session.getDefaultInstance(imapProps, null);
            imapStore = imapSession.getStore("imaps");
            if (imapPort == -1) {
                imapStore.connect(imapHost, username, password);
            } else {
                imapStore.connect(imapHost, imapPort, username, password);
            }
        }
        return imapStore;
    }
    
    public void connectTransport(Transport transport) throws MessagingException {
        transport.connect(mailProps.getProperty(MAIL_SMTP_HOST), 
        		Integer.parseInt(mailProps.getProperty(MAIL_SMTP_PORT)), 
        		mailProps.getProperty(MAIL_USERNAME), 
        		mailProps.getProperty(MAIL_PASSWORD));
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    private void setPropertyIfNotNull(Properties propsFrom, Properties propsTo, String propName) {
        if (propsFrom.getProperty(propName) != null) {
            propsTo.setProperty(propName, propsFrom.getProperty(propName));
        }
    }
}
