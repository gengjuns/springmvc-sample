package com.saas.identity.group.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saas.core.annotation.ExtendedObject;
import com.saas.dynamic.runtime.search.KeyWordLowerCaseAnalyzer;
import com.saas.identity.permission.entity.Permission;
import com.saas.identity.role.entity.Role;
import com.saas.tenant.entity.support.TenantAuditableEntitySupport;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
@Audited
@Table(name = "aa_group")
@ExtendedObject
public class Group extends TenantAuditableEntitySupport<String> {

    @Field
    @Analyzer(impl = KeyWordLowerCaseAnalyzer.class)
    @NotEmpty
    @Index(name = "idx_group_name")
    @Column(length = 50)
    //must be in uppercase, without space
    protected String name;

    @Field
    @Analyzer(impl = KeyWordLowerCaseAnalyzer.class)
    @Column(length = 255)
    protected String description;

    protected boolean logical = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_group_id")
    protected Group parentGroup;

    @OneToMany(mappedBy = "parentGroup")
    @JsonIgnore
    protected List<Group> children;

    @ManyToMany
    @JoinTable(name = "aa_group_roles",
            joinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = false)})
    protected Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "aa_group_permissions",
            joinColumns = {@JoinColumn(name = "group_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", nullable = false, updatable = false)})
    protected Set<Permission> permissions;


    public Group() {
    }


    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isLogical() {
        return logical;
    }


    public void setLogical(boolean logical) {
        this.logical = logical;
    }


    public Group getParentGroup() {
        return parentGroup;
    }


    public void setParentGroup(Group parent) {
        this.parentGroup = parent;
    }


    public List<Group> getChildren() {
        return children;
    }


    public void setChildren(List<Group> children) {
        this.children = children;
    }


    public void addChild(Group group) {
        group.setParentGroup(this);
        group.addChild(group);
    }


    public void removeChild(Group group) {
        group.setParentGroup(null);
        group.removeChild(group);
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


    public void addRole(Role role) {
        if (!getRoles().contains(role)) {
            getRoles().add(role);
        }
    }


    public void removeRole(Role role) {
        getRoles().remove(role);
    }


    public Set<Permission> getPermissions() {
        if (permissions == null) {
            permissions = new HashSet<Permission>();
        }
        return permissions;
    }


    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }


    public void addPermission(Permission permission) {
        if (!getPermissions().contains(permission)) {
            getPermissions().add(permission);
        }
    }


    public void removePermission(Permission permission) {
        getPermissions().remove(permission);
    }


}
