package com.saas.core.web.interceptor;

import com.saas.core.annotation.WebComponent;
import com.saas.identity.auth.user.AuthenticatedUser;
import com.saas.identity.user.UserContextHolder;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Intercept each request to handle the retrieval of {@link com.saas.identity.user.entitiy.User} into context.
 *
 * @author 
 * @since 02/01/2013 6:24 PM
 */
@WebComponent
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserContextHolder.clearLoginUser();
            return true;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof AuthenticatedUser) {
            UserContextHolder.setLoginUser(((AuthenticatedUser) principal));
        }

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clearLoginUser();
    }
}
