package com.saas.core.event;

import com.saas.core.annotation.Observable;
import com.saas.core.annotation.ReportRequest;
import com.saas.core.context.ApplicationContextHolder;
import com.saas.core.entity.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 * Entity replication listener to handle automatic replication
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public class EntityEventListener {

    private static final Logger logger = LoggerFactory.getLogger(EntityEventListener.class);


    /**
     * Replication for new entity
     *
     * @param entity object to be replicated
     */
    @PostPersist
    public void persistEntity(Object entity) {
        if (isObservable(entity)) {
            getProducer().addEntity((Entity) entity);
        }
        if (isReport(entity)) {
        	getRepProducer().createReport((Entity)entity);
        }
    }


    /**
     * Replication for updated entity
     *
     * @param entity object to be replicated
     */
    @PostUpdate
    public void updateEntity(Object entity) {
        if (isObservable(entity)) {
            getProducer().updateEntity((Entity) entity);
        }
    }


    /**
     * Replication for removed entity
     *
     * @param entity object to be replicated
     */
    @PostRemove
    public void removeEntity(Object entity) {
        if (isObservable(entity)) {
            getProducer().removeEntity((Entity) entity);
        }
    }


    protected EventProducer getProducer() {
        return ApplicationContextHolder.getContext().getBean(EventProducer.class);
    }
    
    protected ReportProducer getRepProducer() {
    	return ApplicationContextHolder.getContext().getBean(ReportProducer.class);
    }


    protected boolean isObservable(Object entity) {
        if (entity == null) {
            return false;
        }
        return entity.getClass().isAnnotationPresent(Observable.class);
    }
    
    protected boolean isReport(Object entity){
    	if (entity == null){
    		return false;
    	}
    	return entity.getClass().isAnnotationPresent(ReportRequest.class);
    }


}
