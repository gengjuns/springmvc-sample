package com.saas.reporttemplate.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.Constants;
import com.saas.core.util.EmailUtils;
import com.saas.reporttemplate.entity.ReportTemplate;
import com.saas.reporttemplate.repository.ReportTemplateRepository;

@Service("reportTemplateService")
public class ReportTemplateServiceImpl implements ReportTemplateService {
    private Logger log = Logger.getLogger(ReportTemplateServiceImpl.class);
    
    @Inject
    private ReportTemplateRepository reportTemplateRepository;
    
    
    public void sendEmail(ReportTemplate reportTemplateTO, Map<String, String> map, String fromEmail,
            String fromName, String subject, List<String> sendTOList, List<String> ccList, List<String> bccList)
            throws MailException {
        String emailLog = "Send email notification.";
        try {
            //List<String> attachmentFileList = reportTemplateTO.getAttachmentFileList();
            reportTemplateTO = reportTemplateRepository.findBytemplateId(reportTemplateTO.getTemplateId());
            
            // send Email only when template's status is active
            if (Constants.PUBLISH_STATUS_ACTIVE.equals(reportTemplateTO.getTemplateStatus())) {
            
	            String message = this.generateEmailTemplate(reportTemplateTO, map);
	            
	            if (null == fromEmail) {
	                fromEmail = reportTemplateTO.getTemplateEmailFrom();
	                emailLog = emailLog + " Email From: " + fromEmail;
	            }
	            String defaultFromName = "gengjun@outlook.com";
	            
	            emailLog = emailLog + " Email Default From: " + defaultFromName;
	            
	            //mail.setSender(fromEmail, null == fromName ? defaultFromName : fromName);
	            if (null == subject) {
	                subject = reportTemplateTO.getTemplateSubject();
	                emailLog = emailLog + " Email Subject: " + subject;
	            }
	            //mail.setSubject(subject);
	            //mail.setBody(message);
	            
	            emailLog = emailLog + " Email Content: " + message;
	            
	            // sendTOList
	            emailLog = emailLog + " Email Recipient: ";
	            if (null != sendTOList && sendTOList.size() > 0) {
	                for (int i = 0; i < sendTOList.size(); i++) {
	                   // mail.addToRecipient(sendTOList.get(i), "");
	                    emailLog = emailLog + sendTOList.get(i) + ",";
	                }
	
	            }
	            if (null != reportTemplateTO.getTemplateEmailTo()) {
	                String[] emailToArray = reportTemplateTO.getTemplateEmailTo().split(",");
	                if (null != emailToArray && emailToArray.length > 0) {
	                    for (int i = 0; i < emailToArray.length; i++) {
	                        //mail.addToRecipient(emailToArray[i], "");
	                        emailLog = emailLog + emailToArray[i] + ",";
	                    }
	                }
	            }
	           
	            // ccList
	            if (null != ccList && ccList.size() > 0) {
	                for (int i = 0; i < ccList.size(); i++) {
	                   // mail.addCcRecipient(ccList.get(i), "");
	                    emailLog = emailLog + ccList.get(i) + ",";
	                }
	
	            }
	            if (null != reportTemplateTO.getTemplateEmailCc()) {
	                String[] emailCcArray = reportTemplateTO.getTemplateEmailCc().split(",");
	                if (null != emailCcArray && emailCcArray.length > 0) {
	                    for (int i = 0; i < emailCcArray.length; i++) {
	                       // mail.addCcRecipient(emailCcArray[i], "");
	                        emailLog = emailLog + emailCcArray[i] + ",";
	                    }
	                }
	            }
	            // bccList
	            if (null != bccList && bccList.size() > 0) {
	                for (int i = 0; i < bccList.size(); i++) {
	                    //mail.addBccRecipient(bccList.get(i), "");
	                    emailLog = emailLog + bccList.get(i) + ",";
	                }
	            }
	            if (null != reportTemplateTO.getTemplateEmailBcc()) {
	                String[] emailBccArray = reportTemplateTO.getTemplateEmailBcc().split(",");
	                if (null != emailBccArray && emailBccArray.length > 0) {
	                    for (int i = 0; i < emailBccArray.length; i++) {
	                        //mail.addBccRecipient(emailBccArray[i], "");
	                        emailLog = emailLog + emailBccArray[i] + ",";
	                    }
	                }
	            }
	            
	            // to set attachment file if any.
//	            if (null != attachmentFileList && !attachmentFileList.isEmpty()) {
//	                for (String path : attachmentFileList) {
//	                    mail.addAttachment(MailHelper.getAttachmentPath(path), MailHelper.getAttachmentFile(path));
//	                }
//	            }
	            
	            String host = "192.168.131.117";
	            int port = 25;
	            String adminId = "gengjun";
	            String password = "Pass777@";
	            	
	            EmailUtils em = new EmailUtils(host, port, adminId,password);
	            String[] recipients = new String[sendTOList.size()];
	            for(int i = 0;i<sendTOList.size();i++){
	            	recipients[i] = sendTOList.get(i).toString();
	            	System.out.println();
	            }
	            
	            boolean flag = em.postMailwithAttachment(subject, subject, recipients, new String[] { fromEmail }, null, null, subject,
	                    message, fromEmail, null);
	            if (log.isInfoEnabled()) {
	                log.info("Send email notification successfully. " + emailLog);
	            }
            }
        } catch (Exception e) {
            log.error("Error occus when send email notification. " + emailLog, e);
        }
        
    }
    
    public String generateEmailTemplate(ReportTemplate reportTemplate, Map<String, String> map) {

        if (reportTemplate == null) {
            throw new RuntimeException("[SaaSApp] Can not find template by Template Id. Template Id: " + reportTemplate.getTemplateId());
        }
        byte[] content = reportTemplate.getTemplateContent();
        if(content == null || content.length == 0){
            return "";
        }
        String emailBody = new String(content);
        String subject = reportTemplate.getTemplateSubject();
        String param = reportTemplate.getTemplateParameter();
        if(map == null || map.isEmpty() || param == null || param.length() == 0){
            return emailBody; 
        }
        
		String[] paramArray = param.split(",");
		String temp = null;
		for (int i = 0; i < paramArray.length; i++) {

			if (StringUtils.isEmpty(map.get(paramArray[i]))) {
				temp = "";
			} else {
				temp = map.get(paramArray[i]);
			}
			emailBody = emailBody.replace("${" + paramArray[i] + "}", temp);
			if(!StringUtils.isEmpty(subject)){
			    subject = subject.replace("${" + paramArray[i] + "}", temp);
			}
		}
		reportTemplate.setTemplateSubject(subject);
        return emailBody;

    }


 
   private String getFormatMobile(String beforeMobile){
		if (null == beforeMobile) {
			return null;
		} else if ("".equals(beforeMobile)) {
			return "";
		} else {
			String[] array = beforeMobile.split("-");
			StringBuilder str = new StringBuilder("");
			for (int i = 0; i < array.length; i++) {
				str.append(array[i]);
			}
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str.toString());
			String afterMobile = m.replaceAll("");

			if (!afterMobile.startsWith("*61")) {
				if (afterMobile.startsWith("61")) {
					afterMobile = "*" + afterMobile;
				} else if (afterMobile.startsWith("+61")) {
					afterMobile = afterMobile.replace("+", "*");
				} else {
					afterMobile = "*61" + afterMobile;
				}
			}
			return afterMobile;
		}
 	}
   

}
