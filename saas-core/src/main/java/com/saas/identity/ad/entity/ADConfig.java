package com.saas.identity.ad.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novell.ldap.LDAPConnection;
import com.saas.core.validation.validator.WordNumber;
import com.saas.tenant.entity.support.TenantAuditableEntitySupport;

@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
@Audited
@Table(name = "config_ad")
public class ADConfig  extends TenantAuditableEntitySupport<Long>  {

    @WordNumber
    @NotEmpty
    @Index(name = "ad_name")
    @Column(length = 100)
    //must be in uppercase, without space
    private String name;
    
    @NotEmpty
    @Index(name = "ad_host")
    @Column(length = 100)
    private String host;
    
    @NotEmpty
    @Index(name = "ad_port")
    @Column(length = 10)
    private String port;
    
    @NotEmpty
    @Index(name = "ad_login_dn")
    @Column(length = 255)
    private String loginDN;
    
    @NotEmpty
    @Index(name = "ad_password")
    @Column(length = 255)
    private String password;
    
    @NotEmpty
    @Index(name = "ad_search_dn")
    @Column(length = 255)
    private String searchDN;
    
    @NotEmpty
    @Index(name = "ad_search_filter")
    @Column(length = 255)
    private String searchFilter="objectClass=*";
    
    @NotNull
    @Index(name = "ad_search_scope")
    @Column(length = 10)
    private int searchScope = LDAPConnection.SCOPE_SUB;
    
    @NotNull
    @Index(name = "ad_ldap_version")
    @Column(length = 10)
    private int ldapVersion = LDAPConnection.LDAP_V3;
    
    @OneToMany(mappedBy = "adConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    @ForeignKey(name="none")
    @JsonIgnore
    private List<CustomAD> customADs;
    
    @Transient
    private boolean canConnect = false;
    
    public ADConfig(){}
    
    public ADConfig(String host, String port, String loginDN, String password, String searchDN){
        this.host = host;
        this.port = port;
        this.loginDN = loginDN;
        this.password = password;
        this.searchDN = searchDN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLoginDN() {
        return loginDN;
    }

    public void setLoginDN(String loginDN) {
        this.loginDN = loginDN;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSearchDN() {
        return searchDN;
    }

    public void setSearchDN(String searchDN) {
        this.searchDN = searchDN;
    }

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public int getSearchScope() {
        return searchScope;
    }

    public void setSearchScope(int searchScope) {
        this.searchScope = searchScope;
    }

    public int getLdapVersion() {
        return ldapVersion;
    }

    public void setLdapVersion(int ldapVersion) {
        this.ldapVersion = ldapVersion;
    }

    public List<CustomAD> getCustomADs() {
        if(null == customADs)
        {
            customADs = new ArrayList<CustomAD>();
        }
        return customADs;
    }

    public void setCustomADs(List<CustomAD> customADs) {
        this.customADs = customADs;
    }

    public boolean getCanConnect() {
        return canConnect;
    }

    public void setCanConnect(boolean canConnect) {
        this.canConnect = canConnect;
    }
}
