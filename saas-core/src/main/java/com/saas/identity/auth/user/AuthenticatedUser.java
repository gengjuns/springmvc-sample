package com.saas.identity.auth.user;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.saas.identity.user.entitiy.User;

/**
 * Spring Security user wrapper
 *
 * @author 
 * @since 02/01/2013 6:14 PM
 */
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

    protected User user;

    public AuthenticatedUser(User user, Set<GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }


    public User getUser() {
        return user;
    }


}
