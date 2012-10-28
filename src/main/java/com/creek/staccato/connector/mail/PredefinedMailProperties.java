package com.creek.staccato.connector.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class PredefinedMailProperties {
    private static Map<String, Properties> mailProperties = new HashMap<String, Properties>();
    
    private static Properties yahooProps = new Properties();
    private static Properties gmailProps = new Properties();
    private static Properties googlemailProps = new Properties();
    private static Properties aolProps = new Properties();
    static {
        yahooProps.setProperty(MailSessionKeeper.MAIL_STORE_PROTOCOL, "imap");
        yahooProps.setProperty(MailSessionKeeper.MAIL_SMTP_HOST, "smtp.mail.yahoo.com");
        yahooProps.setProperty(MailSessionKeeper.MAIL_SMTP_PORT, "587");
        yahooProps.setProperty(MailSessionKeeper.MAIL_SMTP_AUTH, "true");
        yahooProps.setProperty(MailSessionKeeper.MAIL_SMTP_STARTTLS_ENABLE, "true");
        yahooProps.setProperty(MailSessionKeeper.MAIL_IMAP_HOST, "imap.mail.yahoo.com");
        yahooProps.setProperty(MailSessionKeeper.MAIL_IMAP_PORT, "993");
        
        gmailProps.setProperty(MailSessionKeeper.MAIL_STORE_PROTOCOL, "imap");
        gmailProps.setProperty(MailSessionKeeper.MAIL_SMTP_HOST, "smtp.gmail.com");
        gmailProps.setProperty(MailSessionKeeper.MAIL_SMTP_PORT, "465");
        gmailProps.setProperty(MailSessionKeeper.MAIL_SMTP_AUTH, "true");
        gmailProps.setProperty(MailSessionKeeper.MAIL_SMTP_STARTTLS_ENABLE, "true");
        gmailProps.setProperty(MailSessionKeeper.MAIL_SMTP_SOCKET_FACTORY_PORT, "465");
        gmailProps.setProperty(MailSessionKeeper.MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        gmailProps.setProperty(MailSessionKeeper.MAIL_IMAP_HOST, "imap.gmail.com");
        
        googlemailProps.setProperty(MailSessionKeeper.MAIL_STORE_PROTOCOL, "imap");
        googlemailProps.setProperty(MailSessionKeeper.MAIL_SMTP_HOST, "smtp.gmail.com");
        googlemailProps.setProperty(MailSessionKeeper.MAIL_SMTP_PORT, "465");
        googlemailProps.setProperty(MailSessionKeeper.MAIL_SMTP_AUTH, "true");
        googlemailProps.setProperty(MailSessionKeeper.MAIL_SMTP_STARTTLS_ENABLE, "true");
        googlemailProps.setProperty(MailSessionKeeper.MAIL_SMTP_SOCKET_FACTORY_PORT, "465");
        googlemailProps.setProperty(MailSessionKeeper.MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        googlemailProps.setProperty(MailSessionKeeper.MAIL_IMAP_HOST, "imap.gmail.com");
        
        aolProps.setProperty(MailSessionKeeper.MAIL_STORE_PROTOCOL, "imap");
        aolProps.setProperty(MailSessionKeeper.MAIL_SMTP_HOST, "smtp.aol.com");
        aolProps.setProperty(MailSessionKeeper.MAIL_SMTP_PORT, "587");
        aolProps.setProperty(MailSessionKeeper.MAIL_SMTP_AUTH, "true");
        aolProps.setProperty(MailSessionKeeper.MAIL_SMTP_STARTTLS_ENABLE, "true");
        aolProps.setProperty(MailSessionKeeper.MAIL_IMAP_HOST, "imap.aol.com");
        aolProps.setProperty(MailSessionKeeper.MAIL_IMAP_PORT, "993");
        
        mailProperties.put("yahoo", yahooProps);
        mailProperties.put("gmail", gmailProps);
        mailProperties.put("googlemailProps", aolProps);
        mailProperties.put("aol", aolProps);
    }
    
    public static Set<String> getPredefinedServers() {
        return mailProperties.keySet();
    }
    
    public static Properties getPredefinedPropertiesForServer(String server) {
        return mailProperties.get(server);
    }
    
    public static Properties getPredefinedProperties(String emailAddress) {
        for (String server : getPredefinedServers()) {
            if (emailAddress.contains(server)) {
                return mailProperties.get(server);
            }
        }
        return null;
    }
}
