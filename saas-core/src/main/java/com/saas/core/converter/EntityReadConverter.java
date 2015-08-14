package com.saas.core.converter;

import com.saas.core.annotation.ApplicationConverter;
import com.saas.core.entity.Entity;
import com.saas.core.entity.EntityKeyInfo;
import com.saas.core.util.StringUtils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Convert {@link EntityKeyInfo} to {@link Entity} using Spring converter.
 * This is a generic converter to get Entity using the class and id key.
 *
 * @author 
 * @since 26/12/2012 10:31 AM
 */
@ApplicationConverter
@Component
public class EntityReadConverter implements Converter<EntityKeyInfo, Entity> {

    public static EntityReadConverter _instance;

    @PersistenceContext
    protected EntityManager entityManager;


    /**
     * Automatically convert {@link EntityKeyInfo} to {@link Entity} using JPA {@link EntityManager}
     *
     * @param source entity class and key
     * @return JPA entity
     */
    @Override
    public Entity convert(EntityKeyInfo source) {
        if (source == null || source.getEntityClass() == null || source.getKey() == null) {
            return null;
        }
        return entityManager.find(source.getEntityClass(), source.getKey());
    }


    public Entity convert(String entityClassName, String id) {
        if (StringUtils.isEmpty(entityClassName)) {
            return null;
        }
        Class<? extends Entity> entityClass = null;
        ;
        try {
            entityClass = (Class<? extends Entity>) Class.forName(entityClassName);
        } catch (Exception e) {
            return null;
        }
        Long entityId = null;
        try {
            entityId = Long.valueOf(id);
        } catch (Exception e) {
            return null;
        }

        return entityManager.find(entityClass, entityId);
    }


    @PostConstruct
    public void init() {
        _instance = this;
    }


    /**
     * Get a singleton instance of this converter
     *
     * @return converter instance
     */
    public static EntityReadConverter getInstance() {
        return _instance;
    }


}
