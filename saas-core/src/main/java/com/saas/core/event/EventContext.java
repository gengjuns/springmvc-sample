package com.saas.core.event;

import com.saas.core.entity.Entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public class EventContext implements Serializable {

    protected EventStatus status = EventStatus.Add;

    protected String entityId;

    protected Class<? extends Entity> entityClass;

    protected Map<String, Object> properties;


    public EventContext(String entityId, Class<? extends Entity> entityClass) {
        this.entityId = entityId;
        this.entityClass = entityClass;
    }


    public EventContext(String entityId, Class<? extends Entity> entityClass, EventStatus status) {
        this.entityId = entityId;
        this.entityClass = entityClass;
        this.status = status;
    }


    public EventStatus getStatus() {
        return status;
    }


    public String getEntityId() {
        return entityId;
    }


    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }


    public Map<String, Object> getProperties() {
        if (properties == null) {
            properties = new HashMap<String, Object>();
        }
        return properties;
    }


    public void setProperty(String key, Object value) {
        getProperties().put(key, value);
    }


    public Object getProperty(String key) {
        return getProperties().get(key);
    }


    @SuppressWarnings("unchecked")
    public <T> T getProperty(String key, Class<T> clazz) {
        return (T) getProperties().get(key);
    }


}
