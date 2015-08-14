package com.saas.identity.auth.service;

import com.saas.identity.user.entitiy.User;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 
 * @since 03/02/2013 8:16 PM
 */
public interface UserService extends UserDetailsService {

    @Transactional
    User updateLoginInfo(String id, String remoteAddress);


}
