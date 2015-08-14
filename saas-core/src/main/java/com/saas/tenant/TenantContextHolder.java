package com.saas.tenant;

import com.saas.Constants;
import com.saas.core.context.ApplicationContextHolder;

/**
 * Tenant Context holder to keep track of the login user's tenant
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public class TenantContextHolder {

    private static final ThreadLocal<String> tenantLocal = new ThreadLocal<String>();

    private static final ThreadLocal<Boolean> skipTenantLocal = new ThreadLocal<Boolean>();


    public static String getLoginTenantId() {
        return tenantLocal.get() != null ? tenantLocal.get() : null;
    }


    public static String getLoginTenantIdAsString() {
        return tenantLocal.get() != null ? tenantLocal.get().toString() : null;
    }


    public static void setLoginTenantId(String tenantId) {
        tenantLocal.set(tenantId);
    }


    //todo: temporary solution
    public static boolean isSkipTenant() {
        return skipTenantLocal.get() == Boolean.TRUE;
    }


    public static void setSkipTenant(Boolean bool) {
        skipTenantLocal.set(bool);
    }


    public static void clearTenantId() {
        tenantLocal.set(null);
        skipTenantLocal.set(null);
    }


    public static boolean isMultiTenantEnabled() {
        return false;
//        return ApplicationContextHolder.getContext().getBean(TenantModuleConfig.class).isMultiTenantEnabled();
    }


}
