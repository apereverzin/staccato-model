package com.creek.staccato.domain.message.generic;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public interface GenericMessage extends Transformable {
    public static final int GROUP_MEMBERSHIP_INVITATION_REQUEST = 101;
    public static final int GROUP_MEMBERSHIP_INVITATION_RESPONSE = 102;
    public static final int GROUP_MEMBERSHIP_REQUEST = 103;
    public static final int GROUP_MEMBERSHIP_RESPONSE = 104;
    public static final int GROUP_MEMBERSHIP_VOTE_REQUEST = 105;
    public static final int GROUP_MEMBERSHIP_VOTE_RESPONSE = 106;
    public static final int GROUP_OWNERSHIP_INVITATION_REQUEST = 107;
    public static final int GROUP_OWNERSHIP_INVITATION_RESPONSE = 108;
    public static final int INFORMATION_MESSAGE = 109;
    public static final int LOCATION_MESSAGE = 110;
    public static final int GROUP_CREATED = 111;
    public static final int GROUP_UPDATED = 112;
    public static final int GROUP_DELETED = 113;
    public static final int PROFILE_CREATED = 114;
    public static final int PROFILE_UPDATED = 115;
    public static final int PROFILE_DELETED = 116;
    public static final int GROUP_PROFILE_UPDATED = 117;
    public static final int REPOSITORY_GROUP = 118;
    public static final int REPOSITORY_PROFILE = 119;
    public static final int REPOSITORY_INFORMATION_MESSAGE = 120;
    public static final int REPOSITORY_PROFILE_INFORMATION_MESSAGES = 121;
    public static final int REPOSITORY_GROUP_INFORMATION_MESSAGES = 122;
    
    int getMessageType();
    
    String getProductVersion();
}
