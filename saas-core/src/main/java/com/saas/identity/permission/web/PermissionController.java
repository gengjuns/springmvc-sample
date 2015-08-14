package com.saas.identity.permission.web;

import com.saas.core.web.ControllerSupport;
import com.saas.identity.permission.entity.Permission;
import com.saas.identity.permission.repository.PermissionRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/permission")
public class PermissionController extends ControllerSupport<Permission, String, PermissionRepository> {


}
