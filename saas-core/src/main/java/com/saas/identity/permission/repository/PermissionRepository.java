package com.saas.identity.permission.repository;

import com.saas.core.repository.EntityRepository;
import com.saas.identity.permission.entity.Permission;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Repository
public interface PermissionRepository extends EntityRepository<Permission, String> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Permission findByName(String name);


}
