package com.saas.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.saas.Constants;
import com.saas.core.annotation.WebComponent;
import com.saas.tenant.TenantContextHolder;
import com.saas.tenant.entity.Tenant;

/**
 * Intercept each request to handle the retrieval of {@link com.saas.tenant.entity.Tenant} into context.
 *
 * @author 
 * @since 02/01/2013 4:25 PM
 */
@WebComponent
public class TenantInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Tenant selectedTenant = (Tenant) session.getAttribute(Constants.TENANT);
            if (selectedTenant != null) {
                TenantContextHolder.setLoginTenantId(selectedTenant.getId());
            }
        }
        
        //
        

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TenantContextHolder.clearTenantId();
    }
}
