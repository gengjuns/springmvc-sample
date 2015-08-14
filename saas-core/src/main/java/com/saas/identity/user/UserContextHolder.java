package com.saas.identity.user;

import com.saas.Constants;
import com.saas.identity.auth.user.AuthenticatedUser;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public class UserContextHolder {

    private static final ThreadLocal<String> userLocal = new ThreadLocal<String>();

    private static final ThreadLocal<AuthenticatedUser> principalLocal = new ThreadLocal<AuthenticatedUser>();


    public static String getLoginUserId() {
        return userLocal.get() != null ? userLocal.get() : "";
    }


    public static String getLoginUserIdAsString() {
        return getLoginUserId().toString();
    }


    /**
     * For static set only. User setLoginUser in web environment
     *
     * @param userId
     */
    public static void setLoginUserId(String userId) {
        userLocal.set(userId);
    }


    public static AuthenticatedUser getLoginUser() {
        return principalLocal.get();
    }


    public static void setLoginUser(AuthenticatedUser user) {
        principalLocal.set(user);
        userLocal.set(user != null ? user.getUser().getId() : null);
    }


    public static void clearLoginUser() {
        userLocal.set(null);
        principalLocal.set(null);
    }


}
