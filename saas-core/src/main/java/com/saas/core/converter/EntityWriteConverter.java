package com.saas.core.converter;

import com.saas.core.annotation.ApplicationConverter;
import com.saas.core.entity.Entity;
import com.saas.core.entity.EntityKeyInfo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Convert {@link Entity} to {@link EntityKeyInfo} using Spring converter.
 * This is a generic converter to get class/key using entity.
 *
 * @author 
 * @since 26/12/2012 10:35 AM
 */
@ApplicationConverter
@Component
public class EntityWriteConverter implements Converter<Entity, EntityKeyInfo> {

    private static EntityWriteConverter _instance;


    /**
     * Automatically convert {@link Entity} to {@link EntityKeyInfo}
     *
     * @param source JPA entity
     * @return class/key
     */
    @Override
    public EntityKeyInfo convert(Entity source) {
        if (source == null || source.getId() == null) {
            return null;
        }
        return EntityKeyInfo.parseEntity(source);
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
    public static EntityWriteConverter getInstance() {
        return _instance;
    }


}
