package com.saas.core.converter;

import com.saas.core.annotation.ApplicationConverter;
import com.saas.core.entity.Entity;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * @author 
 * @since 21/02/2013 5:58 PM
 */
@ApplicationConverter
@Component
public class EntityToStringIdConverter implements ConditionalGenericConverter {

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return targetType.getType() == String.class && Entity.class.isAssignableFrom(sourceType.getType());
    }


    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Object.class, String.class));
    }


    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return "";
        } else {
            return String.valueOf(((Entity) source).getId());
        }
    }
}
