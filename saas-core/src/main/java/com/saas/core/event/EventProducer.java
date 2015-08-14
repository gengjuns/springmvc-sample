package com.saas.core.event;

import com.saas.Constants;
import com.saas.core.entity.Entity;
import com.saas.identity.user.UserContextHolder;
import com.saas.tenant.TenantContextHolder;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Component
public class EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Value("${event.enabled:true}")
    protected boolean enabled;

    @Value("${event.jms.name}")
    protected String queueName;

    @EndpointInject(uri = "jms:queue:QUEUE.EVENT")
    protected ProducerTemplate producer;


    public <T extends Entity> void addEntity(T entity) {
        if (enabled) {
            producer.asyncSendBody(queueName, createContext(entity, EventStatus.Add));
        }
    }


    public <T extends Entity> void updateEntity(T entity) {
        if (enabled) {
            producer.asyncSendBody(queueName, createContext(entity, EventStatus.Update));
        }

    }


    public <T extends Entity> void removeEntity(T entity) {
        if (enabled) {
            producer.asyncSendBody(queueName, createContext(entity, EventStatus.Remove));
        }
    }


    protected <T extends Entity> EventContext createContext(T entity, EventStatus status) {
        EventContext context = new EventContext((String) entity.getId(), entity.getClass(), status);
        context.setProperty(Constants.TENANT_ID_KEY, TenantContextHolder.getLoginTenantId());
        context.setProperty(Constants.USER_ID_KEY, UserContextHolder.getLoginUserId());
        return context;
    }


}
