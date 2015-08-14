package com.saas.identity.permission;

/**
 * @author 
 * @since 25/01/2013 10:09 AM
 */
public enum PermissionType {

    Read("READ"), Write("WRITE"), Create("CREATE"), Delete("DELETE"), Admin("ADMIN"), All("ALL");

    private String type;


    PermissionType(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }


}
