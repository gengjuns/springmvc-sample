package com.saas.core.repository.factory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author 
 * @since 26/03/2013 2:52 PM
 */
public class JpaRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
        extends org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean<R, T, I> {

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new JpaRepositoryFactory(entityManager);
    }


}
