package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class GroupOwnershipInvitationResponse extends AbstractYesNoResponse {
    public GroupOwnershipInvitationResponse(MessageKey requestKey, String responseText, boolean response, MessageKey messageKey, String productVersion) {
        super(requestKey, responseText, response, messageKey, productVersion);
    }

    public GroupOwnershipInvitationResponse(JSONObject jsonObject) {
        super(jsonObject);
    }

    public int getMessageType() {
        return GROUP_MEMBERSHIP_INVITATION_RESPONSE;
    }
}
