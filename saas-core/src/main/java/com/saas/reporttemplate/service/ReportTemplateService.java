package com.saas.reporttemplate.service;

import java.util.List;
import java.util.Map;

import org.springframework.mail.MailException;

import com.saas.reporttemplate.entity.ReportTemplate;

public interface ReportTemplateService {
	public void sendEmail(ReportTemplate reportTemplateTO, Map<String, String> map, String fromEmail,String fromName,String subject,List<String> sendTOList, List<String> ccList, List<String> bccList) 
     throws MailException;
	public String generateEmailTemplate(ReportTemplate reportTemplateTO, Map<String, String> map);
}
