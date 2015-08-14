package com.saas.code.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.saas.code.entity.CodeType;
import com.saas.core.repository.EntityRepository;

/**
 * @author 
 * @since 11/01/2013 11:17 AM
 */
@Repository
public interface CodeTypeRepository extends EntityRepository<CodeType, String> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    CodeType findByName(String name);

}
