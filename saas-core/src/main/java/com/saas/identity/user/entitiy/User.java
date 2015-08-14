package com.saas.identity.user.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saas.core.annotation.DateTimeFormat;
import com.saas.core.annotation.ExtendedObject;
import com.saas.core.util.StringUtils;
import com.saas.dynamic.runtime.search.KeyWordLowerCaseAnalyzer;
import com.saas.identity.group.entity.Group;
import com.saas.identity.role.entity.Role;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.entity.support.TenantAuditableEntitySupport;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
@Audited
@Table(name = "aa_user")
@org.hibernate.annotations.Table(appliesTo = "tbl_aa_user", indexes = { @Index(name = "idx_user_fullname", columnNames = { "firstName", "lastName" }) })
@ExtendedObject
public class User extends TenantAuditableEntitySupport<String> {

    @Field
    @Analyzer(impl = KeyWordLowerCaseAnalyzer.class)
    @NotEmpty
    @Column(length = 50)
    protected String firstName;

    @Field
    @Analyzer(impl = KeyWordLowerCaseAnalyzer.class)
    @NotEmpty
    @Column(length = 50)
    protected String lastName;

    @Field
    @Analyzer(impl = KeyWordLowerCaseAnalyzer.class)
    @NotEmpty
    @Email
    @Column(length = 255)
    protected String email;

    @Field
    @Analyzer(impl = KeyWordLowerCaseAnalyzer.class)
    @Column(length = 50)
    protected String mobileNumber;

    @Field
    @Analyzer(impl = KeyWordLowerCaseAnalyzer.class)
    @Column(length = 50)
    protected String accountStatus;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date agreedToTermsDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date emailVerifiedDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastLoginDate;

    protected String lastLoginIp;

    @NotEmpty
    @Column(length = 255, unique = true)
    protected String username;

    @Column(length = 255)
    protected String password;

    protected transient String checkCode;

    @NotAudited
    @Formula("password")
    protected String currentPassword;

    protected transient String passwordConfirm;

    protected Integer passwordReset = 0;

    @DateTimeFormat
    @Temporal(TemporalType.TIMESTAMP)
    protected Date passwordModifiedDate;

    @Column(length = 255)
    protected String domain;

    @Column(length = 255)
    protected String question1;

    @Column(length = 255)
    protected String answer1;

    protected transient String answerTmp1;

    @Column(length = 255)
    protected String question2;

    @Column(length = 255)
    protected String answer2;

    protected transient String answerTmp2;

    protected transient String securityCodeTmp;

    @Index(name = "idx_userlogin_logintype")
    @NotNull
    protected UserLoginType loginType = UserLoginType.Database;

    @Column(length = 255)
    protected transient String loginProvider;

    @ManyToMany
    @JoinTable(name = "aa_user_groups", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "group_id", nullable = false, updatable = false) })
    protected Set<Group> groups;

    @ManyToMany
    @JoinTable(name = "aa_user_roles", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
    @NotEmpty
    protected Set<Role> roles;

    public User() {
    }

    public User(String firstName, String lastName, String username, String email, String password,String accountStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username=username;
        this.password=password;
        this.accountStatus = accountStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getAgreedToTermsDate() {
        return agreedToTermsDate;
    }

    public void setAgreedToTermsDate(Date agreedToTermsDate) {
        this.agreedToTermsDate = agreedToTermsDate;
    }

    public Date getEmailVerifiedDate() {
        return emailVerifiedDate;
    }

    public void setEmailVerifiedDate(Date emailVerifiedDate) {
        this.emailVerifiedDate = emailVerifiedDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Set<Group> getGroups() {
        if (groups == null) {
            groups = new HashSet<Group>();
        }
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Group addGroup(Group group) {
        if (!getGroups().contains(group)) {
            getGroups().add(group);
        }
        return group;
    }

    public Group removeGroup(Group group) {
        getGroups().remove(group);
        return group;
    }

    public Set<Role> getRoles() {
        if (roles == null) {
            roles = new HashSet<Role>();
        }
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Role addRole(Role role) {
        if (!getRoles().contains(role)) {
            getRoles().add(role);
        }
        return role;
    }

    public Role removeRole(Role role) {
        getRoles().remove(role);
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Integer getPasswordReset() {
        return passwordReset;
    }

    public void setPasswordReset(Integer passwordReset) {
        this.passwordReset = passwordReset;
    }

    public Date getPasswordModifiedDate() {
        return passwordModifiedDate;
    }

    public void setPasswordModifiedDate(Date passwordModifiedDate) {
        this.passwordModifiedDate = passwordModifiedDate;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswerTmp1() {
        return answerTmp1;
    }

    public void setAnswerTmp1(String answerTmp1) {
        this.answerTmp1 = answerTmp1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswerTmp2() {
        return answerTmp2;
    }

    public void setAnswerTmp2(String answerTmp2) {
        this.answerTmp2 = answerTmp2;
    }

    public String getSecurityCodeTmp() {
        return securityCodeTmp;
    }

    public void setSecurityCodeTmp(String securityCodeTmp) {
        this.securityCodeTmp = securityCodeTmp;
    }

    public UserLoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(UserLoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginProvider() {
        return loginProvider;
    }

    public void setLoginProvider(String loginProvider) {
        this.loginProvider = loginProvider;
    }
    @Field
    @Analyzer(impl = KeyWordLowerCaseAnalyzer.class)
    public String getFullName() {
        return StringUtils.arrayToDelimitedString(new String[] { StringUtils.trimToEmpty(firstName), StringUtils.trimToEmpty(lastName) }, " ");
    }

}
