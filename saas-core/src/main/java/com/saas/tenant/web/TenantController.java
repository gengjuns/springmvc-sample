package com.saas.tenant.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysema.query.BooleanBuilder;
import com.saas.Constants;
import com.saas.core.module.repository.ModuleRepository;
import com.saas.core.web.ControllerSupport;
import com.saas.core.web.search.EntitySearchRequest;
import com.saas.tenant.entity.QTenant;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;

/**
 * @author 
 * @since 23/11/2012 11:28 AM
 */
@Controller
@RequestMapping(value = "/tenant")
public class TenantController extends ControllerSupport<Tenant, String, TenantRepository> {

    @Inject
    protected TenantRepository tenantRepository;

    @Inject
    protected ModuleRepository moduleRepository;

	@Override
	protected void preCreate(Tenant entity, BindingResult bindingResult,
			Model model) {
		super.preCreate(entity, bindingResult, model);
		entity.setTenantStatus(Constants.TENANT_STATUS_ACTIVE);
	}
	
	@RequestMapping(value="/suspend/{id}")
	public String suspendTenant(@PathVariable("id") String id){
		Tenant tenant = tenantRepository.findById(id);
		tenant.setTenantStatus(Constants.TENANT_STATUS_SUSPEND);
		tenantRepository.saveAndFlush(tenant);
		return getViewName("/list", true);
	}
	
	@RequestMapping(value="/reinstatus/{id}")
	public String reinstatusTenant(@PathVariable("id") String id){
		Tenant tenant = tenantRepository.findById(id);
		tenant.setTenantStatus(Constants.TENANT_STATUS_ACTIVE);
		tenantRepository.saveAndFlush(tenant);
		return getViewName("/list", true);
	}
	
	@Override
	protected void preRead(EntitySearchRequest<Tenant> request) {
		String parentTenantId = null;
		try{
			parentTenantId = request.getParamMap().get("parentTenantId");
		}catch(Exception e){
			
		}
        BooleanBuilder userBuilder = new BooleanBuilder();
        userBuilder.and(QTenant.tenant.parentTenantId.eq(parentTenantId));
        request.getBuilder().and(userBuilder.getValue());
	}
	
	@RequestMapping(value="/customerlist_{id}")
	public String customerList(@PathVariable("id") String id,Model model){
		Tenant tenant = tenantRepository.findById(id);
		model.addAttribute("resellerTenant", tenant);
		return "/reseller/customertenant/list";
	}
	
	@RequestMapping(value="/customerlist_{id}/create",method=RequestMethod.GET)
	public String createCustomerTenantForm(@PathVariable("id") String id,Model model){
		Tenant tenant = tenantRepository.findById(id);
		model.addAttribute("resellerTenant", tenant);
		model.addAttribute("tenant", new Tenant());
		return "/reseller/customertenant/create";
	}
	
	@RequestMapping(value="/customerlist_{id}/create",method=RequestMethod.POST)
	public String createCustomerTenant(Tenant entity, Model model, BindingResult bindingResult,RedirectAttributes redirectAttributes){
		validate(entity, bindingResult, model);
        model.addAttribute("success", !bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            handleCreateError(entity, bindingResult, model);
            populateEditForm(entity, model);
            return getViewName("/customerlist_{id}/create");
        } else {
            preCreate(entity, bindingResult, model);
            entity.setId(null);
            entity = getEntityRepository().saveAndFlush(entity);
            postCreate(entity, bindingResult, model,redirectAttributes);
        }

        return "redirect:/tenant/customerlist_{id}";
	}
    
    
}
