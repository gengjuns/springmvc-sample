package com.saas.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.QueryHint;
import java.io.Serializable;

/**
 * Marker interface for relational database access by JPA
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@NoRepositoryBean
public interface EntityRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, DynamicQueryEntityRepository<T> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    T findById(ID id);


}
