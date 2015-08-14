package com.saas.code.repository;

import com.saas.code.entity.CodeValue;
import com.saas.core.repository.EntityRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

import java.util.List;

/**
 * @author 
 * @since 11/01/2013 11:18 AM
 */
@Repository
public interface CodeValueRepository extends EntityRepository<CodeValue, String> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select cv from CodeValue cv join cv.type ct " +
            "where ct.name = :name " +
            "and (cv.effectiveDate is null or cv.effectiveDate <= current_timestamp) " +
            "and (cv.expiryDate is null or cv.expiryDate >= current_timestamp) " +
            "order by cv.position ,cv.label")
    List<CodeValue> findByGlobalCodeTypeName(@Param("name") String name);


    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select cv from CodeValue cv join cv.type ct " +
            "where ct.name = :name " +
            "and (cv.effectiveDate is null or cv.effectiveDate <= current_timestamp) " +
            "and (cv.expiryDate is null or cv.expiryDate >= current_timestamp) " +
            "order by cv.position ,cv.label")
    List<CodeValue> findByCodeTypeName(@Param("name") String name);


    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select cv from CodeValue cv join cv.type ct " +
            "where ct.id = :id " +
            "and (cv.effectiveDate is null or cv.effectiveDate <= current_timestamp) " +
            "and (cv.expiryDate is null or cv.expiryDate >= current_timestamp) " +
            "order by cv.position ,cv.label")
    List<CodeValue> findByCodeTypeId(@Param("id") String id);


}
