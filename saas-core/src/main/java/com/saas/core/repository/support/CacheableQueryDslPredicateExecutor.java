package com.saas.core.repository.support;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Interface to allow execution of QueryDsl {@link com.mysema.query.types.Predicate} instances and allow caching.
 *
 * @author 
 * @since 26/03/2013 5:20 PM
 */
public interface CacheableQueryDslPredicateExecutor<T> extends QueryDslPredicateExecutor<T> {

    /**
     * Returns a single entity matching the given {@link com.mysema.query.types.Predicate}.
     *
     * @param predicate
     * @param cacheable
     * @return
     */
    T findOne(Predicate predicate, boolean cacheable);


    /**
     * Returns all entities matching the given {@link Predicate}.
     *
     * @param predicate
     * @param cacheable
     * @return
     */
    Iterable<T> findAll(Predicate predicate, boolean cacheable);


    /**
     * Returns all entities matching the given {@link Predicate} applying the given {@link com.mysema.query.types.OrderSpecifier}s.
     *
     * @param predicate
     * @param cacheable
     * @param orders
     * @return
     */
    Iterable<T> findAll(Predicate predicate, boolean cacheable, OrderSpecifier<?>... orders);


    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities matching the given {@link Predicate}.
     *
     * @param predicate
     * @param pageable
     * @param cacheable
     * @return
     */
    Page<T> findAll(Predicate predicate, Pageable pageable, boolean cacheable);


    /**
     * Returns the number of instances that the given {@link Predicate} will return.
     *
     * @param predicate the {@link Predicate} to count instances for
     * @param cacheable
     * @return the number of instances
     */
    long count(Predicate predicate, boolean cacheable);


}
