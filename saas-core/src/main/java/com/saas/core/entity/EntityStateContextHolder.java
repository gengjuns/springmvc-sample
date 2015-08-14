package com.saas.core.entity;

/**
 * @author 
 * @since 14/01/2013 12:10 PM
 */
public class EntityStateContextHolder {

    private static final ThreadLocal<EntityState> localEntityState = new ThreadLocal<EntityState>();


    public static EntityState getEntityState() {
        return localEntityState.get() != null ? localEntityState.get() : EntityState.Active;
    }


    public static void setEntityState(EntityState state) {
        localEntityState.set(state);
    }


    public static void clearEntityState() {
        localEntityState.set(null);
    }


}
