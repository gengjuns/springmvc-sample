package com.saas.reporttemplate.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saas.Constants;
import com.saas.code.entity.CodeValue;
import com.saas.code.repository.CodeValueRepository;
import com.saas.core.util.StringUtils;
import com.saas.core.web.ControllerSupport;
import com.saas.identity.user.entitiy.User;
import com.saas.reporttemplate.entity.ReportTemplate;
import com.saas.reporttemplate.repository.ReportTemplateRepository;

/**
 * 
 * @author tomchen
 * 
 */
@Controller
@RequestMapping(value = "/reporttemplate")
public class ReportTemplateController extends ControllerSupport<ReportTemplate, String, ReportTemplateRepository>{
	
	@Inject
	protected ReportTemplateRepository reportTemplateRepository;
	
	@Inject
	protected CodeValueRepository codeValueRepository;
	
	
	private List<CodeValue> findByType(String codeType){
		return codeValueRepository.findByGlobalCodeTypeName(codeType);
	}
	
	@Override
    protected void populateEditForm(ReportTemplate entity, Model model) {
        super.populateEditForm(entity, model);
        model.addAttribute("parameters", findByType(Constants.TEMPLATE_PARAMETER));
		model.addAttribute("categorys", findByType(Constants.TEMPLATE_CATEGORY));
		model.addAttribute("modes", findByType(Constants.TEMPLATE_MODE));
		model.addAttribute("status", findByType(Constants.TEMPLATE_STATUS));
    }
	
	@Override
	protected void validate(ReportTemplate entity, BindingResult bindingResult,
			Model model) {
		super.validate(entity, bindingResult, model);
		
		//create:entity.getId() == null ,1 check file not null, 2 check file .html, 3 check file max size
		if(entity.getId() == null){
			
			if(reportTemplateRepository.findBytemplateId(entity.getTemplateId()) != null){
				bindingResult.rejectValue("templateId", "msg_template_id_existed");
				return;
			}
			
			if(entity.getFile().getSize() <= 0){
				bindingResult.rejectValue("file", "msg_template_content_null");
				return;
			}
			checkFileTypeAndMaxSize(entity, bindingResult);
		}else{
			//update:entity.getId() != null
			//if no upload file,do not check file and do not update templateContent
			if(entity.getFile().getSize() <= 0){
				ReportTemplate template = reportTemplateRepository.findById(entity.getId());
				entity.setTemplateContent(template.getTemplateContent());
			}else{
				// if upload new file,check file and update templateContent
				checkFileTypeAndMaxSize(entity, bindingResult);
			}
		}
		
	}

	private void checkFileTypeAndMaxSize(ReportTemplate entity,
			BindingResult bindingResult) {
		//check upload file's type and max size
		if(!entity.getFile().getOriginalFilename().endsWith(".html")){
			bindingResult.rejectValue("file", "msg_template_content_type_error");
			return;
		}
		
		byte[] btyes = null;
		try {
			btyes = entity.getFile().getBytes();
		} catch (IOException e) {
			
		}
		if(btyes.length > 102400){
			bindingResult.rejectValue("file", "msg_template_content_size_over");
			return;
		}
	}

	@Override
	protected void preCreate(ReportTemplate entity,
			BindingResult bindingResult, Model model) {
		super.preCreate(entity, bindingResult, model);
		formatParametersAndContent(entity);
	}

	@Override
	protected void preUpdate(ReportTemplate entity,
			BindingResult bindingResult, Model model) {
		super.preUpdate(entity, bindingResult, model);
		formatParametersAndContent(entity);
	}

	@Override
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model model) {
		ReportTemplate entity = reportTemplateRepository.findOne(id);
		entity.setParameters(entity.getTemplateParameter().split(","));
		
		populateEditForm(entity, model);
		model.addAttribute("reportTemplate", entity);
		return "reporttemplate/update";
	}

	private void formatParametersAndContent(ReportTemplate entity) {
		//generate templateParamners string
		String templateParams = "";
		if(entity.getParameters()!=null && entity.getParameters().length>0){
			for(int i = 0; i < entity.getParameters().length-1; i++){
				templateParams += entity.getParameters()[i] + ",";
			}
			templateParams += entity.getParameters()[entity.getParameters().length-1];
		}
		entity.setTemplateParameter(templateParams);
		
		if(entity.getFile().getSize() > 0){
			try {
				entity.setTemplateContent(entity.getFile().getBytes());
			} catch (IOException e) {
				
			}
		}
	}
	
	@RequestMapping(value = "/checkTemplateId",method = RequestMethod.POST)
    @ResponseBody
    public String checkTemplateId(String templateId){
    	ReportTemplate template = reportTemplateRepository.findBytemplateId(templateId);
    	return template == null ? "true" : "false";
    }
	
	
}
