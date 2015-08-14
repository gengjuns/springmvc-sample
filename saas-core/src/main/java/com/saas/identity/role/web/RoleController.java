package com.saas.identity.role.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saas.core.web.ControllerSupport;
import com.saas.identity.permission.entity.Permission;
import com.saas.identity.permission.entity.QPermission;
import com.saas.identity.permission.repository.PermissionRepository;
import com.saas.identity.role.entity.Role;
import com.saas.identity.role.repository.RoleRepository;

@Controller
@RequestMapping("/role")
public class RoleController extends ControllerSupport<Role, String, RoleRepository> {

    @Inject
    PermissionRepository permissionRepository;


    public Iterable<Permission> getPermissionList() {
        return permissionRepository.findAll(null, true, QPermission.permission.name.asc());
    }


    @Override
    protected void populateEditForm(Role entity, Model model) {
        super.populateEditForm(entity, model);
        model.addAttribute("permissions", getPermissionList());
    }


}
