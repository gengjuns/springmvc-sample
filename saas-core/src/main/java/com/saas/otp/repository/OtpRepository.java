package com.saas.otp.repository;

import com.saas.core.repository.EntityRepository;
import com.saas.otp.entity.Otp;
import com.saas.reporttemplate.entity.ReportTemplate;
import com.saas.tenant.entity.Tenant;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface OtpRepository extends EntityRepository<Otp, Long> {

	@QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    public Otp findByOtp(String otp);

}
