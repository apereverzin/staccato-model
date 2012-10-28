package com.creek.staccato.domain.repositorymessage;

import org.json.simple.JSONObject;

import com.creek.staccato.domain.group.Group;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
@SuppressWarnings("serial")
public class RepositoryGroup extends AbstractRepositoryMessage {
    private Group group;

    private static final String GROUP = "group";

    public RepositoryGroup(Group group, String productVersion) {
        super(productVersion);
        this.group = group;
    }

    public RepositoryGroup(JSONObject jsonObject) {
        super(jsonObject);
        this.group = new Group((JSONObject) jsonObject.get(GROUP));
    }

    public Group getData() {
        return group;
    }

    public int getMessageType() {
        return REPOSITORY_GROUP;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject toJSON() {
        JSONObject jsonObject = super.toJSON();
        jsonObject.put(GROUP, group.toJSON());
        return jsonObject;
    }
}
