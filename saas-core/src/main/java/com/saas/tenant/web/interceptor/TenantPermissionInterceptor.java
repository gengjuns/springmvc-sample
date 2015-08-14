package com.saas.tenant.web.interceptor;

import java.util.Enumeration;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saas.Constants;
import com.saas.core.annotation.WebComponent;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;
import com.sun.xml.internal.ws.client.ResponseContext;

@WebComponent
public class TenantPermissionInterceptor extends HandlerInterceptorAdapter{
	
	@Inject
	private TenantRepository tenantRepository;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		Tenant loginTenant = (Tenant) request.getSession().getAttribute(Constants.TENANT);
		if(loginTenant == null){
			return false;
		}
		
		//if login user is admin, can deal with all reseller tenants and all customer tenants
		if(loginTenant.getParentTenantId() == null){
			return true;
		}
		
		//if login user is reseller,can only deal with its own customer tenants
		Map pathParameters =(Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String tenantId = (String) pathParameters.get("id");
		/*System.out.println("===========================tenantId:" + tenantId);*/
		
		if(tenantId == null){
			return true;
		}
		
		Tenant tenant = tenantRepository.findById(tenantId);
		//checek if tenant is loginTenant's customer tenant : tenant.parentTenantId = loginTenant.id
		if(tenant.getParentTenantId().equals(loginTenant.getId())){
			return true;
		}
		
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}
	
}
