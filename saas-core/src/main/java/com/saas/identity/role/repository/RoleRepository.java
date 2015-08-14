package com.saas.identity.role.repository;

import com.saas.core.repository.EntityRepository;
import com.saas.identity.role.entity.Role;

import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

import java.util.List;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public interface RoleRepository extends EntityRepository<Role, String> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public List<Role> findByNameContainingIgnoreCaseOrderByNameAsc(String name);


    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Role findByName(String name);
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<Role> findByCategory(String category);


}
