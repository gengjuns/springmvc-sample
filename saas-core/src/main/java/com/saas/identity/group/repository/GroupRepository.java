package com.saas.identity.group.repository;

import com.saas.core.repository.EntityRepository;
import com.saas.identity.group.entity.Group;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

import java.util.List;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Repository
public interface GroupRepository extends EntityRepository<Group, String> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public List<Group> findByNameContainingIgnoreCaseOrderByNameAsc(String name);


}
