package com.saas.identity.user.web.interceptor;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.saas.Constants;
import com.saas.core.annotation.WebComponent;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.repository.UserRepository;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;

@WebComponent
public class UserPermissionInterceptor extends HandlerInterceptorAdapter{
	
	@Inject
	private TenantRepository tenantRepository;
	
	@Inject
	private UserRepository userRepository;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		Tenant rootTenant = tenantRepository.findByParentTenantId(null);
		Tenant loginTenant = (Tenant) request.getSession().getAttribute(Constants.TENANT);
		if(loginTenant == null){
			return false;
		}
		
		Map pathParameters =(Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String id = (String) pathParameters.get("id");
		
		User targetUser = userRepository.findById(id);
		
		//if login user is admin ,he can deal with all users
		if(loginTenant.getParentTenantId() == null){
			return true;
		}
		
		//if login user is reseller admin ,he can deal with user in the same reseller tenant and all tenant users of its customer tennat
		else if(loginTenant.getParentTenantId().equals(rootTenant.getId())){
			if(targetUser == null){
				return true;
			}
			Tenant tenant = tenantRepository.findById(targetUser.getTenantId());
			if(tenant.getId().equals(loginTenant.getId()) || tenant.getParentTenantId().equals(loginTenant.getId())){
				return true;
			}
		}
		
		//if login user is tenant admin,he can only deal with user in the same tenant
		else{
			if(targetUser == null || targetUser.getTenantId().equals(loginTenant.getId())){
				return true;
			}
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
