package com.saas.core.repository.factory;

import com.saas.core.repository.support.CacheableQueryDslJpaRepository;
import com.saas.core.repository.support.CacheableQueryDslPredicateExecutor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.LockModeRepositoryPostProcessor;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;

import java.io.Serializable;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

/**
 * @author 
 * @since 26/03/2013 2:54 PM
 */
public class JpaRepositoryFactory extends org.springframework.data.jpa.repository.support.JpaRepositoryFactory {

    private final LockModeRepositoryPostProcessor lockModePostProcessor;


    public JpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.lockModePostProcessor = LockModeRepositoryPostProcessor.INSTANCE;
    }


    @Override
    protected <T, ID extends Serializable> JpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata,
                                                                                   EntityManager entityManager) {
        Class<?> repositoryInterface = metadata.getRepositoryInterface();
        JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());

        SimpleJpaRepository<?, ?> repo = isQueryDslExecutor(repositoryInterface)
                ? (isCacheableQueryDslExecutor(repositoryInterface)
                ? new CacheableQueryDslJpaRepository(entityInformation, entityManager)
                : new QueryDslJpaRepository(entityInformation, entityManager))
                : new SimpleJpaRepository(entityInformation, entityManager);
        repo.setLockMetadataProvider(lockModePostProcessor.getLockMetadataProvider());

        return repo;
    }


    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isQueryDslExecutor(metadata.getRepositoryInterface())) {
            return isCacheableQueryDslExecutor(metadata.getRepositoryInterface())
                    ? CacheableQueryDslJpaRepository.class
                    : QueryDslJpaRepository.class;
        } else {
            return super.getRepositoryBaseClass(metadata);
        }
    }


    private boolean isQueryDslExecutor(Class<?> repositoryInterface) {
        return QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }


    private boolean isCacheableQueryDslExecutor(Class<?> repositoryInterface) {
        return QUERY_DSL_PRESENT && CacheableQueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
    }


}
