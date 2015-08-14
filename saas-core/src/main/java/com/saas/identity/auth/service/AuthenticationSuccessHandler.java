package com.saas.identity.auth.service;

import com.saas.Constants;
import com.saas.core.util.KeyValue;
import com.saas.identity.auth.user.AuthenticatedUser;
import com.saas.identity.user.entitiy.User;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author 
 * @since 03/02/2013 5:38 PM
 */
@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Inject
    protected TenantRepository tenantRepository;

    @Inject
    protected UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        if (authentication.getPrincipal() instanceof AuthenticatedUser) {
            updateLoginInfo(request, (AuthenticatedUser) authentication.getPrincipal());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }


    protected void updateLoginInfo(HttpServletRequest request, AuthenticatedUser authUser) {
        Tenant defaultTenant = tenantRepository.findOne(authUser.getUser().getTenantId());
     
        request.getSession().setAttribute(Constants.TENANT, defaultTenant);
    }


}
