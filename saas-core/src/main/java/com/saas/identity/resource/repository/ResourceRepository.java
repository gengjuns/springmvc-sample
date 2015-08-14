package com.saas.identity.resource.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.saas.core.repository.EntityRepository;
import com.saas.identity.resource.entity.Resource;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Repository
public interface ResourceRepository extends EntityRepository<Resource, Long> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Resource findByName(String name);


}
