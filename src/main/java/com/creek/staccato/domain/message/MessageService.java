package com.creek.staccato.domain.message;

import java.util.Set;

import com.creek.staccato.connector.mail.MailMessageConnector.Result;
import com.creek.staccato.domain.BusinessException;
import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.group.GroupRepository;
import com.creek.staccato.domain.message.generic.AddressedMessage;
import com.creek.staccato.domain.profile.ProfileKey;

/**
 * 
 * @author Andrey Pereverzin
 * 
 */
public interface MessageService {
    InformationMessage createInformationMessage(InformationMessage message, ProfileKey sender) throws BusinessException;

    InformationMessage updateInformationMessage(InformationMessage message) throws BusinessException;

    GroupMembershipInvitationRequest createGroupInvitationRequest(GroupMembershipInvitationRequest message, ProfileKey sender) throws BusinessException;

    GroupMembershipInvitationRequest updateGroupInvitationRequest(GroupMembershipInvitationRequest message) throws BusinessException;

    GroupMembershipInvitationResponse createGroupInvitationResponse(GroupMembershipInvitationResponse message, ProfileKey sender) throws BusinessException;

    GroupMembershipInvitationResponse updateGroupInvitationResponse(GroupMembershipInvitationResponse message) throws BusinessException;

    GroupMembershipRequest createGroupMembershipRequest(GroupMembershipRequest message, ProfileKey sender) throws BusinessException;

    GroupMembershipRequest updateGroupMembershipRequest(GroupMembershipRequest message) throws BusinessException;

    GroupMembershipResponse createGroupMembershipResponse(GroupMembershipResponse message, ProfileKey sender) throws BusinessException;

    GroupMembershipResponse updateGroupIMembershipResponse(GroupMembershipResponse message) throws BusinessException;

    GroupOwnershipInvitationRequest createGroupOwnerInvitationRequest(GroupOwnershipInvitationRequest message, ProfileKey sender) throws BusinessException;

    GroupOwnershipInvitationRequest updateGroupOwnerInvitationRequest(GroupOwnershipInvitationRequest message) throws BusinessException;

    GroupOwnershipInvitationResponse createGroupOwnerInvitationResponse(GroupOwnershipInvitationResponse message, ProfileKey sender) throws BusinessException;

    GroupOwnershipInvitationResponse updateGroupOwnerInvitationResponse(GroupOwnershipInvitationResponse message) throws BusinessException;

    GroupMembershipVoteRequest createVoteRequest(GroupMembershipVoteRequest message, ProfileKey sender) throws BusinessException;

    public GroupMembershipVoteRequest updateVoteRequest(GroupMembershipVoteRequest message) throws BusinessException;

    GroupMembershipVoteResponse createVoteResponse(GroupMembershipVoteResponse message, ProfileKey sender) throws BusinessException;

    GroupMembershipVoteResponse updateVoteResponse(GroupMembershipVoteResponse message) throws BusinessException;

    boolean deleteMessage(MessageKey messageKey) throws BusinessException;

    boolean deleteMessages(Set<MessageKey> messageKeys) throws BusinessException;

    Set<? extends AddressedMessage> getNewMessages() throws BusinessException;

    void saveInfMessage(InformationMessage message) throws BusinessException;

    void saveInformationMessages(Set<InformationMessage> messages) throws BusinessException;

    Set<InformationMessage> getInformationMessagesForGroup(GroupKey groupKey) throws BusinessException;

    Set<InformationMessage> getInformationMessagesForProfile(ProfileKey profileKey) throws BusinessException;

    Result sendMessage(AddressedMessage message) throws BusinessException;

    void setMessageRepository(MessageRepository messageRepository);

    void setInformationMessageRepository(InformationMessageRepository informationMessageRepository);
    
    public void setGroupRepository(GroupRepository groupRepository);
    
    void setMessageCommunicator(MessageCommunicator messageCommunicator);
}
