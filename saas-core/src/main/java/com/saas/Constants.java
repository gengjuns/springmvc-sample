package com.saas;

/**
 * @author 
 * @since 11/01/2013 7:08 PM
 */
public interface Constants {

    public static final String TENANT_ID_KEY = "tenantId";

    public static final String USER_ID_KEY = "userId";

    public static final String BUSINESS_KEY = "businessKey";

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String ROLE_PORTAL_ADMIN = "ROLE_PORTAL_ADMIN";

    public static final String ROLE_TENANT_ADMIN = "ROLE_TENANT_ADMIN";

    public static final String PERM_PORTAL_ALL = "PERM_PORTAL_ALL";

    public static final String TENANTS_LIST = "TENANT_LIST";

    public static final String TENANT = "TENANT_ID";

    public static final String DEMLIMITER_EXPRESSION = "||";

    public static final String CURRENT_ATTACHMENT_PATH = "currentPath";

    public static final String AVAILABLE_OBJECT_LIST = "AVAILABLE_OBJECT_LIST";

    public static final String BOOTSTRAP_THEME_NAME = "BOOTSTRAP_THEME_NAME";

    public static final String KENDO_THEME_NAME = "KENDO_THEME_NAME";

    /**
     * Key to use in workflow to define the attribute name of the attached {@link com.saas.core.entity.Entity} object.
     */
    public static final String ENTITY_KEY = "entity";

    public static final String DATATABLE_ID_KEY = "dataTableId";

    public static final String ENTITY_ID_KEY = "entityId";

    public static final String ENTITY_CLASSNAME_KEY = "entityClassName";

    public static final String DATAOBJECT_IDVALUEMAP = "idValueMap";


    public static final String INFO_DISPLAY_MESSAGE = "infoDisplayMessage";

    public static final String ERROR_DISPLAY_MESSAGE = "errorDisplayMessage";

    public static final String CSRF_TOKEN = "csrf_token";
    
    public static final String PUBLISH_STATUS_DRAFT = "D";
    public static final String PUBLISH_STATUS_INACTIVE = "I";
    public static final String PUBLISH_STATUS_ACTIVE = "A";
    
    public static final String ACCOUNT_STATUS_NEW = "NEW";
    public static final String ACCOUNT_STATUS_ACTIVE = "ACTIVE";
    public static final String ACCOUNT_STATUS_LOCKED = "LOCKED";
    
    public static final String TENANT_STATUS_ACTIVE = "ACTIVE";
    public static final String TENANT_STATUS_SUSPEND = "SUSPEND";
    
    public static final String TEMPLATE_ID_ACTIVE_ACCOUNT = "NOF_USER_ACTIVE";
    public static final String TEMPLATE_ID_RESET_PASSWORD = "NOF_RESET_PWD";
    
    public static final String OTP_TYPE_RESET_PASSWORD="RP";
    public static final String OTP_TYPE_DELETE_INDICATOR_Y="Y";
    public static final String OTP_TYPE_DELETE_INDICATOR_N="N";
    
    public static final String CHALLENGE_QUESTION="Challenge-Question Type";
    public static final String TEMPLATE_PARAMETER="Template Parameters";
    public static final String TEMPLATE_CATEGORY="Template Category";
    public static final String TEMPLATE_MODE="Template Mode";
    public static final String TEMPLATE_STATUS="Template Status";
    
    
    public static final String MESSAGE_SUCCESS = "MESSAGE_SUCCESS";
    public static final String MESSAGE_ERROR = "MESSAGE_ERROR";
    

}
