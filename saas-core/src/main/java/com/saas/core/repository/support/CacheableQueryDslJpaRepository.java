package com.saas.core.repository.support;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.PathBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author 
 * @since 26/03/2013 4:49 PM
 */
public class CacheableQueryDslJpaRepository<T, ID extends Serializable>
        extends org.springframework.data.jpa.repository.support.QueryDslJpaRepository<T, ID>
        implements CacheableQueryDslPredicateExecutor<T> {

    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final EntityPath<T> path;

    private final PathBuilder<T> builder;

    private final Querydsl querydsl;


    public CacheableQueryDslJpaRepository(JpaEntityInformation<T, ID> entityInformation,
                                          EntityManager entityManager) {
        this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
    }


    public CacheableQueryDslJpaRepository(JpaEntityInformation<T, ID> entityInformation,
                                          EntityManager entityManager,
                                          EntityPathResolver resolver) {
        super(entityInformation, entityManager, resolver);

        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(entityManager, builder);
    }


    protected JPQLQuery createQuery(boolean cacheable, Predicate... predicate) {
        JPQLQuery query = super.createQuery(predicate);
        if (cacheable && query instanceof JPAQuery) {
            ((JPAQuery) query).setHint("org.hibernate.cacheable", "true");
        }
        return query;
    }


    @Override
    public T findOne(Predicate predicate, boolean cacheable) {
        return createQuery(cacheable, predicate).uniqueResult(path);
    }


    @Override
    public Iterable<T> findAll(Predicate predicate, boolean cacheable) {
        return createQuery(cacheable, predicate).list(path);
    }


    @Override
    public Iterable<T> findAll(Predicate predicate, boolean cacheable, OrderSpecifier<?>... orders) {
        return createQuery(cacheable, predicate).orderBy(orders).list(path);
    }


    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable, boolean cacheable) {
        JPQLQuery countQuery = createQuery(cacheable, predicate);
        JPQLQuery query = querydsl.applyPagination(pageable, createQuery(cacheable, predicate));

        return new PageImpl<T>(query.list(path), pageable, countQuery.count());
    }


    @Override
    public long count(Predicate predicate, boolean cacheable) {
        return createQuery(cacheable, predicate).count();
    }


}
