package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class GroupMembershipResponse extends AbstractYesNoResponse {
    public GroupMembershipResponse(MessageKey requestKey, String responseText, boolean response, MessageKey messageKey, String productVersion) {
        super(requestKey, responseText, response, messageKey, productVersion);
    }
    
    public GroupMembershipResponse(JSONObject jsonObject) {
        super(jsonObject);
    }
    
    public int getMessageType() {
        return GROUP_MEMBERSHIP_RESPONSE;
    }
}
