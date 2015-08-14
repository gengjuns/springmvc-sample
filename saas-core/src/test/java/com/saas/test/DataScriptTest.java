package com.saas.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.saas.Constants;
import com.saas.code.entity.CodeType;
import com.saas.code.entity.CodeValue;
import com.saas.code.repository.CodeTypeRepository;
import com.saas.code.repository.CodeValueRepository;
import com.saas.identity.group.repository.GroupRepository;
import com.saas.identity.permission.repository.PermissionRepository;
import com.saas.identity.resource.entity.Resource;
import com.saas.identity.resource.repository.ResourceRepository;
import com.saas.identity.role.entity.Role;
import com.saas.identity.role.repository.RoleRepository;
import com.saas.identity.user.UserContextHolder;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.repository.UserRepository;
import com.saas.reporttemplate.entity.ReportTemplate;
import com.saas.reporttemplate.repository.ReportTemplateRepository;
import com.saas.tenant.TenantContextHolder;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/app-context.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class DataScriptTest {

    private static final Logger logger = LoggerFactory.getLogger(DataScriptTest.class);

    @PersistenceContext
    protected EntityManager entityManager;

    @Value("${superadmin.firstname}")
    protected String adminFirstname;

    @Value("${superadmin.lastname}")
    protected String adminLastname;

    @Value("${superadmin.email}")
    protected String adminEmail;

    @Value("${superadmin.username}")
    protected String adminUsername;

    @Value("${superadmin.password}")
    protected String adminPassword;

    @Inject
    protected UserRepository userRepository;

    @Inject
    protected GroupRepository groupRepository;

    @Inject
    protected RoleRepository roleRepository;

    @Inject
    protected PermissionRepository permissionRepository;
    @Inject
    protected ResourceRepository resourceRepository;

    @Inject
    protected TenantRepository tenantRepository;

    @Inject
    protected ReportTemplateRepository reportTemplateRepository;

    @Inject
    protected PasswordEncoder passwordEncoder;

    @Inject
    protected CodeTypeRepository codeTypeRepository;

    @Inject
    protected CodeValueRepository codeValueRepository;

    @Before
    public void init() {
    }

    @Test
    public void prepareDefaultDate() throws Exception {
        if(tenantRepository.findByName("Root") == null){
            addDefaultTenant();
            addDefaultResources();
            addDefaultUsers();
            addDefaultReportTemplates();
            addDefaultCodes();
        }
    }

    protected void addDefaultTenant() {
        Tenant rootTenant = tenantRepository.save(new Tenant("Root", "Root Tenant", Constants.TENANT_STATUS_ACTIVE));
        TenantContextHolder.setLoginTenantId(rootTenant.getId());

        List<Tenant> tenantList = new ArrayList<Tenant>();
        tenantList.add(new Tenant("Reseller", "Reseller Tenant", Constants.TENANT_STATUS_ACTIVE,rootTenant.getId()));
        tenantRepository.save(tenantList);
    }

    private void addDefaultReportTemplates() {
        List<ReportTemplate> reportTemplates = new ArrayList<ReportTemplate>();
        ReportTemplate reportTemplate = new ReportTemplate("NOF_USER_ACTIVE", "Activate your account", "Common", getTemplateContent("html/NOF_USER_ACTIVE.html"),
                "firstName,lastName,url,loginId,expiryDt", "A", "E", "Activate your account", null, null, null, "Noreply@saas.com.sg");
        reportTemplates.add(reportTemplate);

        reportTemplate = new ReportTemplate("NOF_RESET_PWD", "Reset your password", "Common", getTemplateContent("html/NOF_RESET_PWD.html"),
                "firstName,lastName,rsetPwdDt,expiryDt,url,loginId", "A", "E", "Reset your password", null, null, null, "Noreply@saas.com.sg");
        reportTemplates.add(reportTemplate);

        reportTemplateRepository.save(reportTemplates);
    }

    protected void addDefaultResources() {
        List<Resource> resources = new ArrayList<Resource>();
        resources.add(addResource("RES_HOME", "/home"));
        resources.add(addResource("RES_CODETYPE", "/codetype/**"));
        resources.add(addResource("RES_CODEVALUE", "/codevalue/**"));
        resources.add(addResource("RES_PERMISSION", "/permission/**"));
        resources.add(addResource("RES_ROLE", "/role/**"));
        resources.add(addResource("RES_RESELLER_USER", "/reseller/user/**"));
        resources.add(addResource("RES_TENANT", "/tenant/**"));
        resources.add(addResource("RES_USER", "/user/**"));
        resources.add(addResource("RES_AD", "/ad/**"));
        resources.add(addResource("RES_STATISTICS", "/statistics/**"));
        resourceRepository.save(resources);

    }

    protected void addDefaultUsers() {
        // portal admin role
        Role portalAdminRole = new Role("PORTAL_ADMIN", "Admin", "PORTAL");
        // portalAdminRole.addPermission(permissionRepository.findByName("PORTAL_ALL"));
        portalAdminRole.getResources().addAll(resourceRepository.findAll());
        roleRepository.save(portalAdminRole);

        // portal User role
        Role portalUserRole = new Role("PORTAL_USER", "User", "PORTAL");
        // portalUserRole.addPermission(permissionRepository.findByName("PORTAL_ALL"));
        portalUserRole.getResources().addAll(resourceRepository.findAll());
        roleRepository.save(portalUserRole);

        // reseller admin role
        Role resellerAdminRole = new Role("RESELLER_ADMIN", "Admin", "RESELLER");
        // resellerAdminRole.addPermission(permissionRepository.findByName("TENANT_ADMIN"));
        // resellerAdminRole.addPermission(permissionRepository.findByName("RESELLERUSER_ADMIN"));
        resellerAdminRole.addResource(resourceRepository.findByName("RES_HOME"));
        resellerAdminRole.addResource(resourceRepository.findByName("RES_RESELLER_USER"));
        resellerAdminRole.addResource(resourceRepository.findByName("RES_TENANT"));
        roleRepository.save(resellerAdminRole);

        // tenant admin role
        Role tenantAdminRole = new Role("TENANT_ADMIN", "Admin", "TENANT");
        // tenantAdminRole.addPermission(permissionRepository.findByName("TENANT_" + PermissionType.Read));
        // tenantAdminRole.addPermission(permissionRepository.findByName("USER_ADMIN"));
        tenantAdminRole.addResource(resourceRepository.findByName("RES_HOME"));
        tenantAdminRole.addResource(resourceRepository.findByName("RES_USER"));
        tenantAdminRole.addResource(resourceRepository.findByName("RES_AD"));
        roleRepository.save(tenantAdminRole);

        // tenant user role
        Role tenantUserRole = new Role("TENANT_USER", "User", "TENANT");
        tenantUserRole.addResource(resourceRepository.findByName("RES_HOME"));
        roleRepository.save(tenantUserRole);

        // default portal admin user
        Tenant adminTenant = tenantRepository.findByName("Root");
        User admin = new User(adminFirstname, adminLastname, adminUsername, adminEmail, passwordEncoder.encode(adminPassword), Constants.ACCOUNT_STATUS_ACTIVE);
        admin.addRole(portalAdminRole);
        admin.setTenantId(adminTenant.getId());
        admin = userRepository.save(admin);
        UserContextHolder.setLoginUserId(admin.getId());

        Tenant resellerTenant = tenantRepository.findByName("Reseller");
        User resellerAdmin = new User("Reseller", "Admin1", "reseller@saas.com", "gengjun@outook.com", passwordEncoder.encode(adminPassword),
                Constants.ACCOUNT_STATUS_ACTIVE);
        resellerAdmin.addRole(resellerAdminRole);
        resellerAdmin.setTenantId(resellerTenant.getId());
        userRepository.save(resellerAdmin);
    }

    protected void addDefaultCodes() {
        List<CodeType> types = new ArrayList<CodeType>();
        List<CodeValue> values = new ArrayList<CodeValue>();
        CodeType type = new CodeType("WFD-Element Type", "WFD Elements");

        type = new CodeType("Challenge-Question Type", "Challenge-Question Type");
        types.add(type);
        values.add(new CodeValue("BIRTH", "Name of your city of birth?", 1, type));
        values.add(new CodeValue("BIRTHPLACE", "Mother's birthplace", 2, type));
        values.add(new CodeValue("FIRSTVEHICLE", "Model of your first vehicle", 3, type));
        values.add(new CodeValue("FRIEND", "Best childhood friend", 4, type));
        values.add(new CodeValue("MUM", "Mother's maiden name", 5, type));
        values.add(new CodeValue("PET", "Name of first pet", 6, type));
        values.add(new CodeValue("SCHOOL", "Primary school", 7, type));
        values.add(new CodeValue("SINGERORHAND", "Favourite singer or band", 8, type));
        values.add(new CodeValue("SPORT", "Favourite sport", 9, type));
        values.add(new CodeValue("VISITED", "First country you visited", 10, type));

        // init code type/value for reportTemplate
        type = new CodeType("Template Parameters", "Parameters For Report Template");
        types.add(type);
        values.add(new CodeValue("firstName", "First Name", 1, type));
        values.add(new CodeValue("lastName", "Last Name", 2, type));
        values.add(new CodeValue("url", "Url", 3, type));
        values.add(new CodeValue("loginId", "Login Id", 4, type));
        values.add(new CodeValue("expiryDt", "Expiry Date", 5, type));
        values.add(new CodeValue("rsetPwdDt", "Reset PW Date", 6, type));

        type = new CodeType("Template Category", "Category For Report Template");
        types.add(type);
        values.add(new CodeValue("Common", "Common", 1, type));

        type = new CodeType("Template Mode", "Mode For Report Template");
        types.add(type);
        values.add(new CodeValue("E", "E", 1, type));

        type = new CodeType("Template Status", "Status For Report Template");
        types.add(type);
        values.add(new CodeValue("A", "Active", 1, type));
        values.add(new CodeValue("I", "Inactive", 2, type));

        codeTypeRepository.save(types);
        codeValueRepository.save(values);
    }

    private Resource addResource(String name, String url) {
        return new Resource(name, url);
    }
    private byte[] getTemplateContent(String filePath) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        try {
            int i = 0;
            while((i = (is.read(buffer, 0, buffer.length))) > 0){
                output.write(buffer, 0, i);
            }
            return output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

}
