package com.saas.reporttemplate.repository;

import com.saas.core.repository.EntityRepository;
import com.saas.reporttemplate.entity.ReportTemplate;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface ReportTemplateRepository extends EntityRepository<ReportTemplate, String> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    ReportTemplate findBytemplateId(String templateId);
    

}
