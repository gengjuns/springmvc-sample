package com.saas.core.util;

import com.saas.core.annotation.DateFormat;
import com.saas.core.annotation.DateTimeFormat;
import com.saas.core.entity.Entity;

import org.springframework.core.convert.TypeDescriptor;

import java.util.Date;

/**
 * @author 
 * @since 07/02/2013 5:45 PM
 */
public abstract class TypeDescriptorUtils {

    public static final TypeDescriptor DateFormat;

    public static final TypeDescriptor DateTimeFormat;

    public static final TypeDescriptor String = TypeDescriptor.valueOf(String.class);

    public static final TypeDescriptor Double = TypeDescriptor.valueOf(Double.class);

    public static final TypeDescriptor Boolean = TypeDescriptor.valueOf(Boolean.class);

    public static final TypeDescriptor Entity = TypeDescriptor.valueOf(Entity.class);


    static {
        DateFormat = new TypeDescriptor(ReflectionUtils.findField(TypeDescriptorUtils.class, "dateFormat"));
        DateTimeFormat = new TypeDescriptor(ReflectionUtils.findField(TypeDescriptorUtils.class, "dateTimeFormat"));
    }


    @DateFormat
    private Date dateFormat;

    @DateTimeFormat
    private Date dateTimeFormat;


    public static TypeDescriptor match(TypeDescriptor original, Class clazz) {
        if (original == Double && clazz != null && Number.class.isAssignableFrom(clazz)) {
            return TypeDescriptor.valueOf(clazz);
        }
        return original;
    }

}
