package com.creek.staccato.domain.message;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.GroupKey;
import com.creek.staccato.domain.message.generic.SingleGroupMessage;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractSingleGroupMessage extends AbstractAddressedMessage implements SingleGroupMessage {
    private GroupKey groupTo;

    private static final String GROUP_TO = "groupTo";

    public AbstractSingleGroupMessage(GroupKey groupTo, MessageKey messageKey, String productVersion) {
        super(messageKey, productVersion);
        this.groupTo = groupTo;
    }

    public AbstractSingleGroupMessage(JSONObject jsonObject) {
        super(jsonObject);
        this.groupTo = new GroupKey((JSONObject) jsonObject.get(GROUP_TO));
    }

    public GroupKey getGroupTo() {
        return groupTo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(GROUP_TO, groupTo.toJSON());
        return jsonObject;
    }
}
