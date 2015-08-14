package com.saas.core.web.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Initialize spring mvc using java configuration rather than mvc:annotation-driven
 *
 * @author 
 * @since 27/03/2013 3:35 PM
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    protected ApplicationContext applicationContext;

    protected ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }


    @Bean
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping mapping = super.requestMappingHandlerMapping();
        mapping.setUseSuffixPatternMatch(false);
        return mapping;
    }


    @Bean
    @Override
    public FormattingConversionService mvcConversionService() {
        return (FormattingConversionService) applicationContext.getBean("conversionService");
    }


    @Bean(name = "messageSource")
    public ResourceBundleMessageSource mvcResourceBundleMessageSource() {
        List<String> basenames = new ArrayList<String>();
        basenames.add("ValidationMessage");
        try {
            Resource[] resources = patternResolver.getResources("classpath:messages/messages*.properties");
            if (resources != null) {
                for (Resource resource : resources) {
                    basenames.add("messages/" + resource.getFilename().substring(0, resource.getFilename().lastIndexOf(".")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(basenames.toArray(new String[basenames.size()]));
        return messageSource;
    }


}
