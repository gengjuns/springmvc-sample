package com.saas.core.repository;

import com.saas.core.repository.support.CacheableQueryDslPredicateExecutor;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Marker interface to
 *
 * @author 
 * @since 17/01/2013 5:46 PM
 */
public interface DynamicQueryEntityRepository<T>
        extends QueryDslPredicateExecutor<T>, CacheableQueryDslPredicateExecutor<T> {


}
