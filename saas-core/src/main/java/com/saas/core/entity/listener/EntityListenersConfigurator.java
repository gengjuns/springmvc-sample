package com.saas.core.entity.listener;

import com.saas.core.annotation.Listener;

import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreLoadEventListener;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import java.util.Map;

/**
 * Inject Spring dependencies into Hibernate JPA Listener
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Component
public class EntityListenersConfigurator implements ApplicationContextAware {

    @Inject
    protected EntityManagerFactory entityManagerFactory;


    protected ApplicationContext applicationContext;


    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }


    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * Find all listener configured in Spring marked by {@link Listener} annotation and registered in JPA
     */
    @PostConstruct
    public void registerListeners() {
        HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) entityManagerFactory;
        SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) hibernateEntityManagerFactory.getSessionFactory();
        SessionFactoryServiceRegistry serviceRegistry = (SessionFactoryServiceRegistry) sessionFactoryImplementor.getServiceRegistry();
        EventListenerRegistry registry = serviceRegistry.getService(EventListenerRegistry.class);

        Map<String, Object> listeners = applicationContext.getBeansWithAnnotation(Listener.class);
        for (Object listener : listeners.values()) {
            if (listener == null) {
                continue;
            }
            if (PreLoadEventListener.class.isAssignableFrom(listener.getClass())) {
                registry.getEventListenerGroup(EventType.PRE_LOAD).prependListener((PreLoadEventListener) listener);
            }
            if (PreInsertEventListener.class.isAssignableFrom(listener.getClass())) {
                registry.getEventListenerGroup(EventType.PRE_INSERT).prependListener((PreInsertEventListener) listener);
            }
            if (PreUpdateEventListener.class.isAssignableFrom(listener.getClass())) {
                registry.getEventListenerGroup(EventType.PRE_UPDATE).prependListener((PreUpdateEventListener) listener);
            }
            if (PreDeleteEventListener.class.isAssignableFrom(listener.getClass())) {
                registry.getEventListenerGroup(EventType.PRE_DELETE).prependListener((PreDeleteEventListener) listener);
            }
            if (PostLoadEventListener.class.isAssignableFrom(listener.getClass())) {
                registry.getEventListenerGroup(EventType.POST_LOAD).appendListener((PostLoadEventListener) listener);
            }
            if (PostInsertEventListener.class.isAssignableFrom(listener.getClass())) {
                registry.getEventListenerGroup(EventType.POST_INSERT).appendListener((PostInsertEventListener) listener);
            }
            if (PostUpdateEventListener.class.isAssignableFrom(listener.getClass())) {
                registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener((PostUpdateEventListener) listener);
            }
            if (PostDeleteEventListener.class.isAssignableFrom(listener.getClass())) {
                registry.getEventListenerGroup(EventType.POST_DELETE).appendListener((PostDeleteEventListener) listener);
            }
        }
    }
}
