package com.saas.identity.ad.repository;

import org.springframework.stereotype.Repository;

import com.saas.core.repository.EntityRepository;
import com.saas.identity.ad.entity.ADConfig;

@Repository
public interface ADConfigRepositroy extends EntityRepository<ADConfig, Long> {

    
}
