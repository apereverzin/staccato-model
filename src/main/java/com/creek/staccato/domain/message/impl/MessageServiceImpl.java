package com.creek.staccato.domain.message.impl;

import java.util.Set;

import com.creek.staccato.connector.mail.MailMessageConnector.Result;
import com.creek.staccato.domain.BusinessException;
import com.creek.staccato.domain.group.GroupInformationMessages;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.group.GroupRepository;
import com.creek.staccato.domain.message.CommunicationException;
import com.creek.staccato.domain.message.GroupMembershipInvitationRequest;
import com.creek.staccato.domain.message.GroupMembershipInvitationResponse;
import com.creek.staccato.domain.message.GroupMembershipRequest;
import com.creek.staccato.domain.message.GroupMembershipResponse;
import com.creek.staccato.domain.message.GroupMembershipVoteRequest;
import com.creek.staccato.domain.message.GroupMembershipVoteResponse;
import com.creek.staccato.domain.message.GroupOwnershipInvitationRequest;
import com.creek.staccato.domain.message.GroupOwnershipInvitationResponse;
import com.creek.staccato.domain.message.InformationMessage;
import com.creek.staccato.domain.message.InformationMessageRepository;
import com.creek.staccato.domain.message.MessageCommunicator;
import com.creek.staccato.domain.message.MessageKey;
import com.creek.staccato.domain.message.MessageRepository;
import com.creek.staccato.domain.message.MessageService;
import com.creek.staccato.domain.message.generic.AddressedMessage;
import com.creek.staccato.domain.profile.ProfileInformationMessages;
import com.creek.staccato.domain.profile.ProfileKey;
import com.creek.staccato.domain.repositorymessage.RepositoryException;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;
    private GroupRepository groupRepository;
    private InformationMessageRepository informationMessageRepository;
    private MessageCommunicator messageCommunicator;

    @Override
    public InformationMessage createInformationMessage(InformationMessage message, ProfileKey sender) throws BusinessException {
        return (InformationMessage) createMessage(message, sender);
    }

    @Override
    public InformationMessage updateInformationMessage(InformationMessage message) throws BusinessException {
        return (InformationMessage) updateMessage(message);
    }

    @Override
    public GroupMembershipInvitationRequest createGroupInvitationRequest(GroupMembershipInvitationRequest message, ProfileKey sender) throws BusinessException {
        return (GroupMembershipInvitationRequest) createMessage(message, sender);
    }

    @Override
    public GroupMembershipInvitationRequest updateGroupInvitationRequest(GroupMembershipInvitationRequest message) throws BusinessException {
        return (GroupMembershipInvitationRequest) updateMessage(message);
    }

    @Override
    public GroupMembershipInvitationResponse createGroupInvitationResponse(GroupMembershipInvitationResponse message, ProfileKey sender) throws BusinessException {
        return (GroupMembershipInvitationResponse) createMessage(message, sender);
    }

    @Override
    public GroupMembershipInvitationResponse updateGroupInvitationResponse(GroupMembershipInvitationResponse message) throws BusinessException {
        return (GroupMembershipInvitationResponse) updateMessage(message);
    }

    @Override
    public GroupMembershipRequest createGroupMembershipRequest(GroupMembershipRequest message, ProfileKey sender) throws BusinessException {
        return (GroupMembershipRequest) createMessage(message, sender);
    }

    @Override
    public GroupMembershipRequest updateGroupMembershipRequest(GroupMembershipRequest message) throws BusinessException {
        return (GroupMembershipRequest) updateMessage(message);
    }

    @Override
    public GroupMembershipResponse createGroupMembershipResponse(GroupMembershipResponse message, ProfileKey sender) throws BusinessException {
        return (GroupMembershipResponse) createMessage(message, sender);
    }

    @Override
    public GroupMembershipResponse updateGroupIMembershipResponse(GroupMembershipResponse message) throws BusinessException {
        return (GroupMembershipResponse) updateMessage(message);
    }

    @Override
    public GroupOwnershipInvitationRequest createGroupOwnerInvitationRequest(GroupOwnershipInvitationRequest message, ProfileKey sender) throws BusinessException {
        return (GroupOwnershipInvitationRequest) createMessage(message, sender);
    }

    @Override
    public GroupOwnershipInvitationRequest updateGroupOwnerInvitationRequest(GroupOwnershipInvitationRequest message) throws BusinessException {
        return (GroupOwnershipInvitationRequest) updateMessage(message);
    }

    @Override
    public GroupOwnershipInvitationResponse createGroupOwnerInvitationResponse(GroupOwnershipInvitationResponse message, ProfileKey sender) throws BusinessException {
        return (GroupOwnershipInvitationResponse) createMessage(message, sender);
    }

    @Override
    public GroupOwnershipInvitationResponse updateGroupOwnerInvitationResponse(GroupOwnershipInvitationResponse message) throws BusinessException {
        return (GroupOwnershipInvitationResponse) updateMessage(message);
    }

    @Override
    public GroupMembershipVoteRequest createVoteRequest(GroupMembershipVoteRequest message, ProfileKey sender) throws BusinessException {
        return (GroupMembershipVoteRequest) createMessage(message, sender);
    }

    @Override
    public GroupMembershipVoteRequest updateVoteRequest(GroupMembershipVoteRequest message) throws BusinessException {
        return (GroupMembershipVoteRequest) updateMessage(message);
    }

    @Override
    public GroupMembershipVoteResponse createVoteResponse(GroupMembershipVoteResponse message, ProfileKey sender) throws BusinessException {
        return (GroupMembershipVoteResponse) createMessage(message, sender);
    }

    @Override
    public GroupMembershipVoteResponse updateVoteResponse(GroupMembershipVoteResponse message) throws BusinessException {
        return (GroupMembershipVoteResponse) updateMessage(message);
    }

    @Override
    public boolean deleteMessage(MessageKey messageKey) throws BusinessException {
        try {
            if (messageRepository.getMessage(messageKey) == null) {
                throw new BusinessException("Message not found: " + messageKey);
            }
            return messageRepository.deleteMessage(messageKey);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    @Override
    public boolean deleteMessages(Set<MessageKey> messageKeys) throws BusinessException {
        try {
            for (MessageKey messageKey : messageKeys) {
                if (messageRepository.getMessage(messageKey) == null) {
                    throw new BusinessException("Message not found: " + messageKey);
                }
            }
            return messageRepository.deleteMessages(messageKeys);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

   @Override
   public Set<? extends AddressedMessage> getNewMessages() throws BusinessException {
        try {
            return messageCommunicator.receiveMessages();
        } catch (CommunicationException ex) {
            throw new BusinessException(ex);
        }
    }

    @Override
    public void saveInformationMessage(InformationMessage message) throws BusinessException {
        try {
            informationMessageRepository.saveInformationMessage(message);
            
            // add message to the repository profile
            ProfileInformationMessages profileInformationMessages = groupRepository.getProfileInformationMessages(message.getMessageKey().getSender());
            profileInformationMessages.getInformationMessageKeys().add(message.getMessageKey().getTimestamp());
            groupRepository.updateProfileInformationMessages(profileInformationMessages);
            
            // add message to the repository group
            GroupInformationMessages groupInformationMessages = groupRepository.getGroupInformationMessages(message.getGroupsTo().iterator().next());
            groupInformationMessages.getInformationMessageKeys().add(message.getMessageKey());
            groupRepository.updateGroupInformationMessages(groupInformationMessages);
            
            // add message to recent messages

        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    @Override
    public void saveInformationMessages(Set<InformationMessage> messages) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<InformationMessage> getInformationMessagesForGroup(GroupKey groupKey) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<InformationMessage> getInformationMessagesForProfile(ProfileKey profileKey) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Result sendMessage(AddressedMessage message) throws BusinessException {
        try {
            return messageCommunicator.sendMessage(message);
        } catch (CommunicationException ex) {
            throw new BusinessException(ex);
        }
    }

    private <T extends AddressedMessage> T createMessage(T message, ProfileKey sender) throws BusinessException {
        try {
            return messageRepository.createMessage(message);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    private <T extends AddressedMessage> T updateMessage(T message) throws BusinessException {
        try {
            if (messageRepository.getMessage(message.getMessageKey()) == null) {
                throw new BusinessException("Message not found: " + message.getMessageKey());
            }
            return messageRepository.updateMessage(message);
        } catch (RepositoryException ex) {
            throw new BusinessException(ex);
        }
    }

    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void setInformationMessageRepository(InformationMessageRepository informationMessageRepository) {
        this.informationMessageRepository = informationMessageRepository;
    }

    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void setMessageCommunicator(MessageCommunicator messageCommunicator) {
        this.messageCommunicator = messageCommunicator;
    }
}
