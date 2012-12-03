package com.creek.staccato.connector.mail;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Flags.Flag;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.AbstractRequest;
import com.creek.staccato.domain.message.GenericMessageTransformer;
import com.creek.staccato.domain.message.InformationMessage;
import com.creek.staccato.domain.message.TransformException;
import com.creek.staccato.domain.message.generic.AddressedMessage;
import com.creek.staccato.domain.message.generic.GenericMessage;
import com.creek.staccato.domain.message.generic.MultipleGroupMessage;
import com.creek.staccato.domain.message.generic.MultipleProfileMessage;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.util.JSONTransformer;
import com.creek.staccato.repository.email.AbstractRepository;

import static com.creek.staccato.repository.email.AbstractRepository.*;

/**
 * 
 * @author Andrey Pereverzin
 */
public class MailMessageConnector {
    public enum Result {
        SUCCESS
    }

    private MailSessionKeeper sessionKeeper;

    private static final String STACCATO_SUBJECT = "Staccato";

    public MailMessageConnector(Properties mailProps) {
        sessionKeeper = new MailSessionKeeper(mailProps);
    }

    public void checkSMTPConnection() throws ConnectorException {
        try {
            Session session = sessionKeeper.getSMTPSession();
            Transport transport = session.getTransport("smtp");
            sessionKeeper.connectTransport(transport);
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public void checkIMAPConnection() throws ConnectorException {
        try {
            sessionKeeper.getIMAPStore().getDefaultFolder();
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Set<AddressedMessage> receiveMessages() throws ConnectorException {
        try {
            Folder inboxFolder = getFolder(INBOX_FOLDER_NAME);
            inboxFolder.open(Folder.READ_WRITE);
            Folder informationMessagesFolder = getFolder(MESSAGES_DB_FOLDER_NAME, NEW_INFORMATION_MESSAGES_FOLDER_NAME);
            Folder pendingRequestsFolder = getFolder(MESSAGES_DB_FOLDER_NAME, PENDING_REQUESTS_FOLDER_NAME);
            Set<Message> informationMessages = new HashSet<Message>();
            Set<Message> pendingRequests = new HashSet<Message>();
            Set<AddressedMessage> addressedMessages = new HashSet<AddressedMessage>();

            System.out.println("--------receiveMessages");
            Message[] msgs = inboxFolder.search(new SubjectSearchTerm(STACCATO_SUBJECT));
            for (Message msg : msgs) {
                try {
                    AddressedMessage message = (AddressedMessage) transformMessageToGenericMessage(msg);
                    if (message instanceof InformationMessage) {
                        informationMessages.add(msg);
                    } else if (message instanceof AbstractRequest) {
                        pendingRequests.add(msg);
                    }
                    addressedMessages.add(message);
                } catch (TransformException ex) {
                    //
                }
            }
            moveMessagesToFolder(informationMessages, informationMessagesFolder);
            moveMessagesToFolder(pendingRequests, pendingRequestsFolder);
            for (Message msg : msgs) {
                msg.setFlag(Flag.DELETED, true);
            }
            inboxFolder.close(true);
            return addressedMessages;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }
    
    public Result sendMessage(AddressedMessage message) throws ConnectorException {
        Session session = sessionKeeper.getSMTPSession();
        try {
            Set<String> emailsTo = new HashSet<String>();
            if (message instanceof MultipleGroupMessage) {
                for (GroupKey groupKey : ((MultipleGroupMessage) message).getGroupsTo()) {
                    //
                }
                for (ProfileKey profileKey : ((MultipleGroupMessage) message).getProfilesTo()) {
                    emailsTo.clear();
                    emailsTo.add(profileKey.getEmailAddress());
                    sendToAddresses(message, emailsTo, session);
                }
            } else if (message instanceof MultipleProfileMessage) {
                for (ProfileKey profileKey : ((MultipleProfileMessage) message).getProfilesTo()) {
                    emailsTo.clear();
                    emailsTo.add(profileKey.getEmailAddress());
                    sendToAddresses(message, emailsTo, session);
                }
            } else {
                //
            }
            return Result.SUCCESS;
        } catch (MessagingException e) {
            throw new ConnectorException(e);
        }
    }

    public boolean removeMessage(String folderName, String subject) {
        return true;
    }

    public boolean removeMessage(Folder folder, String subject) throws ConnectorException {
        Message message = getMailMessageBySubject(folder, subject);
        if (message == null) {
            return false;
        }
        try {
            message.setFlag(Flag.DELETED, true);
            folder.expunge();
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
        return true;
    }

    public GenericMessage getMessageBySubject(String folderName, String subject) throws ConnectorException {
        Folder folder = null;
        folder = getParentFolder(folderName);
        return getMessageBySubject(folder, subject);
    }

    public GenericMessage getMessageBySubject(Folder folder, String subject) throws ConnectorException {
        try {
            folder.open(Folder.READ_ONLY);
            return transformMessageToGenericMessage(getMailMessageBySubject(folder, subject));
        } catch (TransformException ex) {
            throw new ConnectorException(ex);
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        } finally {
            if (folder != null) {
                try {
                    folder.close(true);
                } catch (MessagingException ex) {
                    throw new ConnectorException(ex);
                }
            }
        }
    }

    public GenericMessage[] getAllMessages(Folder folder) throws ConnectorException {
        try {
            folder.open(Folder.READ_ONLY);
            return transformMessagesToGenericMessage(getMailMessages(folder));
        } catch (TransformException ex) {
            throw new ConnectorException(ex);
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        } finally {
            if (folder != null) {
                try {
                    folder.close(true);
                } catch (MessagingException ex) {
                    throw new ConnectorException(ex);
                }
            }
        }
    }

    /**
     * Put generic message into specified folder.
     * 
     * @param folderName - folder where to put
     * @param message - message to put
     * @param subject
     * @return true if success
     * @throws CommunicationException
     */
    public boolean putRepositoryMessageToFolderWithUniqueSubject(Folder parentFolder, String folderName, GenericMessage message, String subject) throws ConnectorException {
        try {
            Folder folder = getFolder(parentFolder, folderName);
            if (folder == null) {
                throw new ConnectorException(folderName + " - folder does not exist");
            }

            folder.open(Folder.READ_WRITE);
            putRepositoryMessageToFolderWithUniqueSubject(folder, message, subject);
            return true;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public boolean putRepositoryMessageToFolderWithUniqueSubject(Folder folder, GenericMessage message, String subject) throws ConnectorException {
        try {
            System.out.println("--------putRepositoryMessageToFolderWithUniqueSubject");
            Message[] msgs = folder.search(new SubjectSearchTerm(subject));
            for (Message msg : msgs) {
                msg.setFlag(Flags.Flag.DELETED, true);
            }
            folder.expunge();
            folder.close(true);

            // Session smtpSession = Session.getInstance(smtpProps);
            Session smtpSession = sessionKeeper.getSMTPSession();
            Message mailMessage = new MimeMessage(smtpSession);
            mailMessage.setFrom(new InternetAddress(AbstractRepository.APPLICATION));
            mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sessionKeeper.getUsername()));
            mailMessage.setSubject(subject);
            mailMessage.setText(message.toJSON().toString());
            folder.appendMessages(new Message[] { mailMessage });
            System.out.println("------" + folder.getName() + " " + subject);
            return true;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public boolean putRepositoryMessagesToFolder(String folderName, GenericMessage[] messages, String[] subjects) throws ConnectorException {
        try {
            Folder folder = getFolder(folderName);
            if (folder == null) {
                throw new ConnectorException(folderName + " - folder does not exist");
            }

            folder.open(Folder.READ_WRITE);
            System.out.println("--------putRepositoryMessagesToFolder");
            for (int i = 0; i < subjects.length; i++) {
                Message[] msgs = folder.search(new SubjectSearchTerm(subjects[i]));
                for (Message msg : msgs) {
                    msg.setFlag(Flags.Flag.DELETED, true);
                }
            }
            folder.expunge();
            folder.close(true);

            // Session smtpSession = Session.getInstance(smtpProps);
            Session smtpSession = sessionKeeper.getSMTPSession();
            Message[] msgs = new Message[messages.length];
            for (int i = 0; i < messages.length; i++) {
                msgs[i] = new MimeMessage(smtpSession);
                msgs[i].setRecipients(Message.RecipientType.TO, InternetAddress.parse(sessionKeeper.getUsername()));
                msgs[i].setSubject(subjects[i]);
                msgs[i].setText(messages[i].toJSON().toString());
            }
            folder.appendMessages(msgs);
            return true;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Folder createFolder(String folderName) throws ConnectorException {
        try {
            return createFolder(sessionKeeper.getIMAPStore().getDefaultFolder(), folderName, false);
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Folder createFolder(String parentFolderName, String folderName) throws ConnectorException {
        try {
            if (parentFolderName == null || parentFolderName.trim().length() == 0) {
                throw new IllegalArgumentException("parentFolderName should not be null or empty");
            }

            Folder parentFolder = getParentFolder(parentFolderName);
            parentFolder.open(Folder.READ_WRITE);
            return createFolder(parentFolder, folderName, false);
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Folder createFolder(Folder parentFolder, String folderName, boolean open) throws ConnectorException {
        try {
            if (parentFolder.isOpen()) {
                if (parentFolder.getMode() != Folder.READ_WRITE) {
                    parentFolder.close(false);
                    parentFolder.open(Folder.READ_WRITE);
                }
            } else {
                if (open) {
                    parentFolder.open(Folder.READ_WRITE);
                }
            }
            Folder folder = parentFolder.getFolder(folderName);
            folder.create(Folder.HOLDS_MESSAGES);
            if (parentFolder.isOpen()) {
                parentFolder.close(true);
            }
            return folder;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Folder renameFolder(Folder parentFolder, Folder folder, String folderName) throws ConnectorException {
        try {
            parentFolder.open(Folder.READ_WRITE);
            Folder newFolder = parentFolder.getFolder(folderName);
            folder.renameTo(newFolder);
            parentFolder.close(true);
            return folder;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public void deleteFolder(String parentFolderName, String folderName) throws ConnectorException {
        try {
            Folder parentFolder = getParentFolder(parentFolderName);
            parentFolder.open(Folder.READ_WRITE);
            Folder folder = parentFolder.getFolder(folderName);
            folder.delete(true);
            parentFolder.close(true);
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public String[] getChildFolders(String folderName) throws ConnectorException {
        try {
            Folder folder = getParentFolder(folderName);
            folder.open(Folder.READ_ONLY);
            Folder[] folders = folder.list();
            String[] folderNames = new String[folders.length];
            for (int i = 0; i < folders.length; i++) {
                folderNames[i] = folders[i].getName();
            }
            folder.close(true);
            return folderNames;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Folder getFolder(String folderName) throws ConnectorException {
        try {
            Folder rootFolder = sessionKeeper.getIMAPStore().getDefaultFolder();
            Folder[] folders = rootFolder.list();
            for (Folder folder : folders) {
                if (folderName.equalsIgnoreCase(folder.getName())) {
                    return folder;
                }
            }
            return null;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Folder getFolder(String parentFolderName, String folderName) throws ConnectorException {
        try {
            Folder rootFolder = sessionKeeper.getIMAPStore().getDefaultFolder();
            Folder[] folders = rootFolder.list();
            for (Folder folder : folders) {
                if (parentFolderName.equalsIgnoreCase(folder.getName())) {
                    return getFolder(folder, folderName);
                }
            }
            return null;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Folder getFolder(Folder parentFolder, String folderName) throws ConnectorException {
        try {
            Folder[] folders = parentFolder.list();
            for (Folder folder : folders) {
                if (folderName.equalsIgnoreCase(folder.getName())) {
                    return folder;
                }
            }
            return null;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    /**
     * Transform mail message to GenericMessage.
     * 
     * @param msg - message to transform
     * @return transformed message
     * @throws CommunicationException if error occurs
     */
    public GenericMessage transformMessageToGenericMessage(Message msg) throws TransformException {
        if (msg == null) {
            return null;
        }

        try {
            String content = (String) msg.getContent();
            JSONParser parser = new JSONParser();
            JSONTransformer transformer = new JSONTransformer();
            parser.parse(content, transformer);

            JSONObject jsonObject = (JSONObject) transformer.getResult();

            return GenericMessageTransformer.transform(jsonObject);
        } catch (MessagingException ex) {
            throw new TransformException(ex);
        } catch (IOException ex) {
            throw new TransformException(ex);
        } catch (ParseException ex) {
            throw new TransformException(ex);
        }
    }

    public GenericMessage[] transformMessagesToGenericMessage(Message[] msgs) throws TransformException {
        if (msgs == null) {
            return new GenericMessage[0];
        }

        GenericMessage[] messages = new GenericMessage[msgs.length];
        for (int i = 0; i < msgs.length; i++) {
            messages[i] = transformMessageToGenericMessage(msgs[i]);
        }
        return messages;
    }

    private void sendToAddresses(AddressedMessage message, Set<String> emailAddresses, Session session) throws MessagingException {
        Message mailMessage = new MimeMessage(session);
        mailMessage.setFrom(new InternetAddress(message.getMessageKey().getSender().getEmailAddress()));
        StringBuilder sb = new StringBuilder();
        if (emailAddresses.size() > 0) {
            for (String emailAddress : emailAddresses) {
                sb.append(emailAddress).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sb.toString()));
        mailMessage.setSubject(STACCATO_SUBJECT);
        mailMessage.setText(message.toJSON().toString());

        Transport transport = session.getTransport("smtp");
        sessionKeeper.connectTransport(transport);

        Transport.send(mailMessage);
    }

    private Message getMailMessageBySubject(Folder folder, String subject) throws ConnectorException {
        try {
            System.out.println("--------getMailMessageBySubject");
            Message[] msgs = folder.search(new SubjectSearchTerm(subject));
            if (msgs.length > 0) {
                return msgs[0];
            }
            return null;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    private Message[] getMailMessages(Folder folder) throws ConnectorException {
        try {
            return folder.getMessages();
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    private Folder getParentFolder(String parentFolderName) throws ConnectorException {
        if (!MESSAGES_DB_FOLDER_NAME.equals(parentFolderName)) {
            return getFolder(MESSAGES_DB_FOLDER_NAME, parentFolderName);
        } else {
            return getFolder(parentFolderName);
        }
    }

    private void moveMessagesToFolder(Set<Message> messagesToMove, Folder folderTo) throws MessagingException {
        Message[] messagesArray = new Message[messagesToMove.size()];
        int i = 0;
        for (Message msg : messagesToMove) {
            messagesArray[i] = msg;
            i++;
        }
        folderTo.appendMessages(messagesArray);
    }
}
