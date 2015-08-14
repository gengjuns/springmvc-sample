package com.saas.core.converter;

import com.saas.core.annotation.ApplicationConverter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import java.util.Map;

/**
 * @author 
 * @since 24/01/2013 1:29 AM
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean implements ApplicationContextAware {

    protected ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installConverters(getObject());
    }


    protected void installConverters(FormatterRegistry registry) {
        DomainClassConverter<FormattingConversionService> dcc = new DomainClassConverter<FormattingConversionService>((FormattingConversionService) registry);
        registry.addConverter(dcc);
        dcc.setApplicationContext(applicationContext);

        //custom converters
        Map<String, Object> converters = applicationContext.getBeansWithAnnotation(ApplicationConverter.class);
        if (converters == null || converters.isEmpty()) {
            return;
        }
        for (Object o : converters.values()) {
            if (Converter.class.isAssignableFrom(o.getClass())) {
                registry.addConverter((Converter) o);
            } else if (GenericConverter.class.isAssignableFrom(o.getClass())) {
                registry.addConverter((GenericConverter) o);
            }
        }
    }


}
