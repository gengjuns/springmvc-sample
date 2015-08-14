package com.saas.identity.auth.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saas.Constants;
import com.saas.identity.auth.user.AuthenticatedUser;
import com.saas.identity.group.entity.Group;
import com.saas.identity.permission.entity.Permission;
import com.saas.identity.role.entity.Role;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.entitiy.UserLoginType;
import com.saas.identity.user.repository.UserRepository;

/**
 * Spring Security {@link UserDetailsService} implementation
 *
 * @author 
 * @since 02/01/2013 4:46 PM
 */
@Service("userService")
public class JpaUserServiceImpl implements UserService {

    @Inject
    protected UserRepository userRepository;

    @Value("${identity.role.prefix:ROLE_}")
    protected String rolePrefix;

    @Value("${identity.permission.prefix:PERM_}")
    protected String permissionPrefix;

    @Value("${identity.group.prefix:GROUP_}")
    protected String groupPrefix;
    
    


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsernameAndLoginType(username, UserLoginType.Database);
        if (user == null) {
            throw new UsernameNotFoundException("notFound");
        }
        if(user.getAccountStatus().equalsIgnoreCase(Constants.ACCOUNT_STATUS_NEW)){
        	throw new DisabledException("new");
        }
        if(user.getAccountStatus().equalsIgnoreCase(Constants.ACCOUNT_STATUS_LOCKED)){
        	throw new LockedException("locked");
        }
        //TODO other login errors
        
      
        return new AuthenticatedUser(user, getAuthorities(user));
    }


    protected Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        processRoles(user.getRoles(), authorities);
        for (Group group : user.getGroups()) {
            authorities.add(new SimpleGrantedAuthority(groupPrefix + group.getName().toUpperCase()));
            processRoles(group.getRoles(), authorities);
            processPermissions(group.getPermissions(), authorities);
        }
        return authorities;
    }


    protected void processRoles(Set<Role> roles, Set<GrantedAuthority> authorities) {
        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(rolePrefix + role.getName()));
                processPermissions(role.getPermissions(), authorities);
            }
        }
    }


    protected void processPermissions(Set<Permission> permissions, Set<GrantedAuthority> authorities) {
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permissionPrefix + permission.getName()));
            }
        }
    }


    @Override
    public User updateLoginInfo(String id, String remoteAddress) {
        User user = userRepository.findOne(id);
        //force load tenant
        user.setLastLoginIp(remoteAddress);
        user.setLastLoginDate(new Date());
        return userRepository.save(user);
    }


}
