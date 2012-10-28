package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class GroupMembershipVoteResponse extends AbstractYesNoResponse {
    public GroupMembershipVoteResponse(MessageKey requestKey, String responseText, boolean response, MessageKey messageKey, String productVersion) {
        super(requestKey, responseText, response, messageKey, productVersion);
    }

    public GroupMembershipVoteResponse(JSONObject jsonObject) {
        super(jsonObject);
    }

    public int getMessageType() {
        return GROUP_MEMBERSHIP_VOTE_RESPONSE;
    }
}
