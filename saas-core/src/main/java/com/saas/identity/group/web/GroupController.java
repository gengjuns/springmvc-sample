package com.saas.identity.group.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saas.core.web.ControllerSupport;
import com.saas.identity.group.entity.Group;
import com.saas.identity.group.entity.QGroup;
import com.saas.identity.group.repository.GroupRepository;
import com.saas.identity.permission.entity.Permission;
import com.saas.identity.permission.entity.QPermission;
import com.saas.identity.permission.repository.PermissionRepository;
import com.saas.identity.role.entity.QRole;
import com.saas.identity.role.entity.Role;
import com.saas.identity.role.repository.RoleRepository;

@Controller
@RequestMapping("/group")
public class GroupController extends ControllerSupport<Group, String, GroupRepository> {

    @Inject
    GroupRepository groupRepository;

    @Inject
    PermissionRepository permissionRepository;

    @Inject
    RoleRepository roleRepository;


    public List<Group> getGroups() {
        return (List<Group>) groupRepository.findAll(null, true, QGroup.group.name.asc());
    }


    public List<Role> getRoles() {
        return (List<Role>) roleRepository.findAll(null, true, QRole.role.name.asc());
    }


    public List<Permission> getPermissions() {
        return (List<Permission>) permissionRepository.findAll(null, true, QPermission.permission.name.asc());
    }


    @Override
    protected void populateEditForm(Group entity, Model model) {
        super.populateEditForm(entity, model);
        model.addAttribute("groups", getGroups());
        model.addAttribute("roles", getRoles());
        model.addAttribute("permissions", getPermissions());
    }


}
