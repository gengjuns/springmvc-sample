package com.saas.identity.user.entitiy;

/**
 * @author 
 * @since 14/01/2013 11:20 AM
 */
public enum UserLoginType {

    Database, Ldap, OpenID;


    public String getOrdinal() {
        return String.valueOf(this.ordinal());
    }


    public String getName() {
        return this.name();
    }

}
