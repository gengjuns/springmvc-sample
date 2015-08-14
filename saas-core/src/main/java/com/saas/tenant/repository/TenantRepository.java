package com.saas.tenant.repository;

import com.saas.core.repository.EntityRepository;
import com.saas.tenant.entity.Tenant;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Repository
public interface TenantRepository extends EntityRepository<Tenant, String> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public Tenant findByName(String name);
    
    
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public Tenant findByParentTenantId(Long parentTenantId);
    


}
