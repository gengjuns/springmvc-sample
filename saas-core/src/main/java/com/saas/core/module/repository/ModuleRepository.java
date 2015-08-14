package com.saas.core.module.repository;

import com.saas.core.module.entity.Module;
import com.saas.core.repository.EntityRepository;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * {@link Module} Repository for CRUD operation.
 *
 * @author 
 * @since 26/12/2012 4:43 PM
 */
@Repository
public interface ModuleRepository extends EntityRepository<Module, Long> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public Module findByName(String name);


}
