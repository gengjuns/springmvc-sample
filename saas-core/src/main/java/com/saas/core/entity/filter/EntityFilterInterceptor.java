package com.saas.core.entity.filter;

import com.saas.core.entity.Entity;

import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 
 * @since 14/01/2013 5:43 PM
 */
@Component
public class EntityFilterInterceptor extends FilterInterceptorSupport implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(EntityFilterInterceptor.class);

    @PersistenceContext
    protected EntityManager entityManager;

    protected Set<EntityFilter> entityFilters = new HashSet<EntityFilter>();

    protected ApplicationContext applicationContext;


    public EntityFilterInterceptor() {

    }


    public EntityFilterInterceptor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void afterPropertiesSet() {
        Map<String, EntityFilter> filters = applicationContext.getBeansOfType(EntityFilter.class);
        if (filters != null && filters.size() > 0) {
            entityFilters.addAll(filters.values());
        }
    }


    @Override
    protected void before(MethodInvocation invocation, Class<? extends Entity> entityClass) {
        if (!entityManager.isOpen()) {
            return;
        }
        for (EntityFilter filter : entityFilters) {
            Session session = (Session) entityManager.getDelegate();
            if (session.isOpen()) {
                if (filter.filter(entityClass)) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("enabling query filter '" + filter.getFilterName() + "'");
                    }
                    session.enableFilter(filter.getFilterName()).setParameter(filter.getParameterName(), filter.getParameterValue());
                } else {
                    session.disableFilter(filter.getFilterName());
                }
            }
        }
    }


    @Override
    protected void after(MethodInvocation invocation, Class<? extends Entity> entityClass, Object result) {

    }


}
