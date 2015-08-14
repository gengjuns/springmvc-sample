package com.saas.core.entity.filter;

import com.saas.core.entity.Entity;
import com.saas.core.repository.EntityRepository;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author 
 * @since 15/01/2013 1:53 PM
 */
public abstract class FilterInterceptorSupport implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FilterInterceptorSupport.class);

    protected Map<Class, Class<? extends Entity>> entityCacheMap = new WeakHashMap<Class, Class<? extends Entity>>();


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (logger.isTraceEnabled()) {
            logger.trace("start filter interceptor for method '" + invocation.getMethod().getName() + "'");
        }

        if (logger.isTraceEnabled()) {
            logger.trace("finding Entity class for invocation");
        }
        Class<? extends Entity> entityClass = getEntityClass(invocation);
        if (logger.isDebugEnabled()) {
            logger.trace("Entity class = " + entityClass);
        }

        if (entityClass != null) {
            before(invocation, entityClass);
        }

        Object result = invocation.proceed();

        if (entityClass != null) {
            after(invocation, entityClass, result);
        }

        if (logger.isTraceEnabled()) {
            logger.trace("end filter interceptor for method '" + invocation.getMethod().getName() + "'");
        }
        return result;
    }


    protected abstract void before(MethodInvocation invocation, Class<? extends Entity> entityClass);


    protected abstract void after(MethodInvocation invocation, Class<? extends Entity> entityClass, Object result);


    @SuppressWarnings("unchecked")
    protected Class<? extends Entity> getEntityClass(MethodInvocation invocation) {
        Class clazz = getRepositoryClass(invocation);
        if (clazz == null) {
            return null;
        }

        if (entityCacheMap.containsKey(clazz)) {
            if (logger.isTraceEnabled()) {
                logger.debug("found from cache " + clazz);
            }
            return entityCacheMap.get(clazz);
        }

        Class<Entity> entityClass = null;
        for (Type type : clazz.getGenericInterfaces()) {
            ParameterizedType ptype = (ParameterizedType) type;
            if (((ParameterizedType) type).getRawType() == EntityRepository.class) {
                Type[] types = ptype.getActualTypeArguments();
                if (types != null && types.length > 0 && types[0] != null &&
                        Entity.class.isAssignableFrom((Class) types[0])) {
                    entityClass = (Class<Entity>) types[0];
                    break;
                }
            }
        }

        entityCacheMap.put(clazz, entityClass);

        return entityClass;
    }


    protected Class getRepositoryClass(MethodInvocation invocation) {
        if (AopUtils.isAopProxy(invocation.getThis())) {
            Class[] proxyClasses = ((Advised) invocation.getThis()).getProxiedInterfaces();
            if (proxyClasses != null && proxyClasses.length > 0) {
                for (Class clazz : proxyClasses) {
                    if (isEntityRepositoryClass(clazz)) {
                        return clazz;
                    }
                }
            }
        } else {
            return isEntityRepositoryClass(invocation.getMethod().getDeclaringClass())
                    ? invocation.getMethod().getDeclaringClass()
                    : null;
        }

        return null;
    }


    protected boolean isEntityRepositoryClass(Class clazz) {
        return EntityRepository.class.isAssignableFrom(clazz);
    }


}
