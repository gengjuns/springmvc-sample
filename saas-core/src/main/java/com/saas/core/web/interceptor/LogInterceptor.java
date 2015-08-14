package com.saas.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.saas.Constants;
import com.saas.core.annotation.WebComponent;
import com.saas.core.web.search.EntitySearchRequest;
import com.saas.identity.auth.user.AuthenticatedUser;
import com.saas.identity.user.UserContextHolder;
import com.saas.tenant.TenantContextHolder;
import com.saas.tenant.entity.Tenant;

/**
 * Intercept each request to handle the retrieval of {@link com.saas.tenant.entity.Tenant} into context.
 *
 * @author 
 * @since 02/01/2013 4:25 PM
 */
@WebComponent
public class LogInterceptor extends HandlerInterceptorAdapter {

    public static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userName = "anonymous";
        AuthenticatedUser user = UserContextHolder.getLoginUser();
        if ( user!= null) {
            userName = user.getUsername();
        }
        logger.info("[SaaS] Start >> " + userName + " " + request.getMethod() + " " + request.getRequestURI());
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String userName = "anonymous";
        AuthenticatedUser user = UserContextHolder.getLoginUser();
        if ( user!= null) {
            userName = user.getUsername();
        }
        logger.info("[SaaS] Ended >> " + userName  + " " + request.getMethod() + " " + request.getRequestURI());
    }
}
