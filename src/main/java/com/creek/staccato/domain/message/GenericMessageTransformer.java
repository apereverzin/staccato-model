package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.message.generic.AbstractGenericMessage;
import com.creek.staccato.domain.message.generic.GenericMessage;
import com.creek.staccato.domain.repositorymessage.RepositoryGroup;
import com.creek.staccato.domain.repositorymessage.RepositoryGroupInformationMessages;
import com.creek.staccato.domain.repositorymessage.RepositoryInformationMessage;
import com.creek.staccato.domain.repositorymessage.RepositoryProfile;
import com.creek.staccato.domain.repositorymessage.RepositoryProfileInformationMessages;
import com.creek.staccato.domain.servicemessage.GroupCreated;
import com.creek.staccato.domain.servicemessage.GroupDeleted;
import com.creek.staccato.domain.servicemessage.GroupProfileUpdated;
import com.creek.staccato.domain.servicemessage.GroupUpdated;
import com.creek.staccato.domain.servicemessage.ProfileAddedToGroup;
import com.creek.staccato.domain.servicemessage.ProfileDeleted;
import com.creek.staccato.domain.servicemessage.ProfileUpdated;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class GenericMessageTransformer {
    public static GenericMessage transform(JSONObject jsonObject) throws TransformException {
        int messageType;
        
        try {
            messageType = Integer.parseInt((String)jsonObject.get(AbstractGenericMessage.MESSAGE_TYPE));
        } catch(Exception ex) {
            throw new TransformException(ex);
        }
        
        if(messageType == GenericMessage.GROUP_MEMBERSHIP_INVITATION_REQUEST) {
            return new GroupMembershipInvitationRequest(jsonObject);
        } else if(messageType == GenericMessage.GROUP_MEMBERSHIP_INVITATION_RESPONSE) {
            return new GroupMembershipInvitationResponse(jsonObject);
        } else if(messageType == GenericMessage.GROUP_MEMBERSHIP_REQUEST) {
            return new GroupMembershipRequest(jsonObject);
        } else if(messageType == GenericMessage.GROUP_MEMBERSHIP_RESPONSE) {
            return new GroupMembershipResponse(jsonObject);
        } else if(messageType == GenericMessage.GROUP_MEMBERSHIP_VOTE_REQUEST) {
            return new GroupMembershipVoteRequest(jsonObject);
        } else if(messageType == GenericMessage.GROUP_MEMBERSHIP_VOTE_RESPONSE) {
            return new GroupMembershipVoteResponse(jsonObject);
        } else if(messageType == GenericMessage.GROUP_OWNERSHIP_INVITATION_REQUEST) {
            return new GroupOwnershipInvitationRequest(jsonObject);
        } else if(messageType == GenericMessage.GROUP_OWNERSHIP_INVITATION_RESPONSE) {
            return new GroupOwnershipInvitationResponse(jsonObject);
        } else if(messageType == GenericMessage.INFORMATION_MESSAGE) {
            return new InformationMessage(jsonObject);
        } else if(messageType == GenericMessage.LOCATION_MESSAGE) {
            return new LocationMessage(jsonObject);
        } else if(messageType == GenericMessage.GROUP_CREATED) {
            return new GroupCreated(jsonObject);
        } else if(messageType == GenericMessage.GROUP_UPDATED) {
            return new GroupUpdated(jsonObject);
        } else if(messageType == GenericMessage.GROUP_DELETED) {
            return new GroupDeleted(jsonObject);
        } else if(messageType == GenericMessage.PROFILE_CREATED) {
            return new ProfileAddedToGroup(jsonObject);
        } else if(messageType == GenericMessage.PROFILE_UPDATED) {
            return new ProfileUpdated(jsonObject);
        } else if(messageType == GenericMessage.PROFILE_DELETED) {
            return new ProfileDeleted(jsonObject);
        } else if(messageType == GenericMessage.GROUP_PROFILE_UPDATED) {
            return new GroupProfileUpdated(jsonObject);
        } else if(messageType == GenericMessage.REPOSITORY_GROUP) {
            return new RepositoryGroup(jsonObject);
        } else if(messageType == GenericMessage.REPOSITORY_PROFILE) {
            return new RepositoryProfile(jsonObject);
        } else if(messageType == GenericMessage.REPOSITORY_INFORMATION_MESSAGE) {
            return new RepositoryInformationMessage(jsonObject);
        } else if(messageType == GenericMessage.REPOSITORY_GROUP_INFORMATION_MESSAGES) {
            return new RepositoryGroupInformationMessages(jsonObject);
        } else if(messageType == GenericMessage.REPOSITORY_PROFILE_INFORMATION_MESSAGES) {
            return new RepositoryProfileInformationMessages(jsonObject);
        } else {
            throw new TransformException("Unknown message type " + messageType);
        }
    }
}
