package com.saas.core.entity;

import com.saas.core.util.ClassUtils;

import java.io.Serializable;

/**
 * Entity class/key holder for conversion
 *
 * @author 
 * @since 26/12/2012 11:47 AM
 */
public class EntityKeyInfo {

    protected Class<? extends Entity> entityClass;

    protected Serializable key;


    public EntityKeyInfo() {
    }


    public EntityKeyInfo(Entity entity) {
        if (entity != null) {
            this.entityClass = entity.getClass();
            this.key = entity.getId();
        }
    }


    public EntityKeyInfo(Class<? extends Entity> entityClass, Serializable key) {
        this.entityClass = entityClass;
        this.key = key;
    }


    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }


    public void setEntityClass(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }


    public Serializable getKey() {
        return key;
    }


    public void setKey(Serializable key) {
        this.key = key;
    }


    /**
     * Parse value to class/key object
     *
     * @param value String or Entity
     * @return class/key object
     */
    public static final EntityKeyInfo parse(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return parseString((String) value);
        } else if (value instanceof Entity) {
            return parseEntity((Entity) value);
        }

        return null;
    }


    /**
     * Parse concatenated string class|key to class/key object
     *
     * @param value concatenated string
     * @return class/key object
     */
    public static final EntityKeyInfo parseString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String[] values = value.split("\\|");
        if (values != null && values.length == 2) {
            //todo: now is hardcoded to Long.
            EntityKeyInfo holder = new EntityKeyInfo(ClassUtils.findClass(values[0]), Long.parseLong(values[1]));
            return (holder.getEntityClass() != null && holder.getKey() != null ? holder : null);
        }
        return null;
    }


    /**
     * Parse entity to class/key object
     *
     * @param entity JPA entity
     * @return class/key object
     */
    public static final EntityKeyInfo parseEntity(Entity entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        }
        return new EntityKeyInfo(entity);
    }


    /**
     * Convert class/key to concatenated string
     *
     * @return concatenated string
     */
    @Override
    public String toString() {
        if (key != null && entityClass != null) {
            return entityClass.getName() + "|" + key.toString();
        }
        return null;
    }
}
