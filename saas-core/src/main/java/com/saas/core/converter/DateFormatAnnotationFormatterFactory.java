package com.saas.core.converter;

import com.saas.core.annotation.DateFormat;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.datetime.DateFormatter;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 
 * @since 29/01/2013 1:30 AM
 */
public class DateFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateFormat> {

    private static final Set<Class<?>> FIELD_TYPES;


    static {
        Set<Class<?>> fieldTypes = new HashSet<Class<?>>();
        fieldTypes.add(Date.class);
        fieldTypes.add(Calendar.class);
        fieldTypes.add(Long.class);
        FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
    }


    private String datePattern = "dd/MM/yyyy";


    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }


    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }


    @Override
    public Printer<?> getPrinter(DateFormat annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }


    @Override
    public Parser<?> getParser(DateFormat annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }


    protected Formatter<Date> getFormatter(DateFormat annotation, Class<?> fieldType) {
        DateFormatter formatter = new DateFormatter();
        formatter.setPattern(datePattern);
        return formatter;
    }


}
