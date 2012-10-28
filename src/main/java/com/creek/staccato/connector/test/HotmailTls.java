package com.creek.staccato.connector.test;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class HotmailTls {
    public static void main(String[] args) {
        String host = "smtp.live.com";
        int port = 587;
        String username = "andrey.pereverzin@hotmail.com";
        String password = "bertoluCCi";
 
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
 
        Session session = Session.getInstance(props);
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("andrey.pereverzin@hotmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("andrey.pereverzin@hotmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler," +
                    "\n\n No spam to my email, please!");
 
            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, username, password);
 
            Transport.send(message);
 
            System.out.println("Done");
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
