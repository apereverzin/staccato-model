package com.creek.staccato.connector.test;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.creek.staccato.connector.mail.MailSessionKeeper;
 
public class SmtpTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(MailSessionKeeper.MAIL_SMTP_HOST, "smtp.gmail.com");
        props.put(MailSessionKeeper.MAIL_SMTP_SOCKET_FACTORY_PORT, "465");
        props.put(MailSessionKeeper.MAIL_SMTP_SOCKET_FACTORY_CLASS,
                "javax.net.ssl.SSLSocketFactory");
        props.put(MailSessionKeeper.MAIL_SMTP_AUTH, "true");
        props.put(MailSessionKeeper.MAIL_SMTP_PORT, "465");
 
        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("andrey.pereverzin","bertoluCCi");
                }
            });
 
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("andrey.pereverzin@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("andrey.pereverzin@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Test message");
 
            Transport.send(message);
 
            System.out.println("Done");
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
