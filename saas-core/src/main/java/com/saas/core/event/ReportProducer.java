package com.saas.core.event;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.saas.Constants;
import com.saas.core.entity.Entity;
import com.saas.identity.user.UserContextHolder;
import com.saas.tenant.TenantContextHolder;

@Component
public class ReportProducer {

	@Value("${report.jms.name}")
    protected String queueName;

    @EndpointInject(uri = "jms:queue:QUEUE.REPORT")
    protected ProducerTemplate producer;
    
    public void createReport(Entity<Long> report){
    	producer.asyncSendBody(queueName, createContext(report, EventStatus.Add));
    }
    
    protected EventContext createContext(Entity<Long> entity, EventStatus status) {
        EventContext context = new EventContext(entity.getId(), entity.getClass(), status);
        context.setProperty(Constants.TENANT_ID_KEY, TenantContextHolder.getLoginTenantId());
        context.setProperty(Constants.USER_ID_KEY, UserContextHolder.getLoginUserId());
        return context;
    }
}
