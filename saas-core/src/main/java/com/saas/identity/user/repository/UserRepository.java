package com.saas.identity.user.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.saas.core.repository.EntityRepository;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.entitiy.UserLoginType;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Repository
public interface UserRepository extends EntityRepository<User, String> {


    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    User findByUsernameAndLoginType(String username, UserLoginType loginType);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    User findByUsername(String username);
}
