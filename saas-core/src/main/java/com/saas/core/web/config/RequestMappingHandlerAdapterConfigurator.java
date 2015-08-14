package com.saas.core.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.saas.core.annotation.WebComponent;
import com.saas.core.web.json.ObjectNullSerializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Register mvc annotation-driven tags to customize converters.
 *
 * @author 
 * @since 23/11/2012 3:56 PM
 */
@WebComponent
public class RequestMappingHandlerAdapterConfigurator {

    private static final boolean jackson2Present =
            ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", RequestMappingHandlerAdapterConfigurator.class.getClassLoader()) &&
                    ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", RequestMappingHandlerAdapterConfigurator.class.getClassLoader());

    @Value("${format.datetime.default:'dd/MM/yyyy HH:mm'}")
    protected String defaultDateTimeFormat;

    @Inject
    protected RequestMappingHandlerAdapter adapter;


    @PostConstruct
    public void init() {
        List<HttpMessageConverter<?>> converters = adapter.getMessageConverters();
        if (!jackson2Present || converters == null || converters.isEmpty()) {
            return;
        }


        HttpMessageConverter jackson = null;
        for (HttpMessageConverter<?> converter : converters) {
            if (MappingJackson2HttpMessageConverter.class.isAssignableFrom(converter.getClass())) {
                jackson = converter;
                break;
            }
        }
        if (jackson != null) {
            processJacksonConverter((MappingJackson2HttpMessageConverter) jackson);
        }

    }


    protected void processJacksonConverter(MappingJackson2HttpMessageConverter converter) {
        converter.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        converter.getObjectMapper().configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        converter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        converter.getObjectMapper().setDateFormat(new SimpleDateFormat(defaultDateTimeFormat));

        //this is a hack. Include only non-null value. Use JsonSerializer include always to convert null to object
        converter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.getObjectMapper().getSerializerProvider().setNullValueSerializer(new ObjectNullSerializer());
    }


}
