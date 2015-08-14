package com.saas.code.web;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saas.code.entity.CodeType;
import com.saas.code.entity.CodeValue;
import com.saas.code.entity.QCodeType;
import com.saas.code.entity.QCodeValue;
import com.saas.code.repository.CodeTypeRepository;
import com.saas.code.repository.CodeValueRepository;
import com.saas.core.web.ControllerSupport;
import com.saas.tenant.entity.QTenant;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;

@Controller
@RequestMapping("codevalue")
public class CodeValueController extends ControllerSupport<CodeValue, String, CodeValueRepository> {

    @Inject
    TenantRepository tenantRepository;

    @Inject
    CodeTypeRepository codeTypeRepository;


    @Override
    protected void populateEditForm(CodeValue entity, Model model) {
        super.populateEditForm(entity, model);
        model.addAttribute("allTenants", getAllTenantList());
        model.addAttribute("allCodeTypes", getAllCodeTypeList());
        model.addAttribute("allCodeValues", getAllCodeValueList());
    }


    public List<Tenant> getAllTenantList() {
        return (List<Tenant>) tenantRepository.findAll(null, true, QTenant.tenant.name.asc());
    }


    public List<CodeType> getAllCodeTypeList() {
        return (List<CodeType>) codeTypeRepository.findAll(null, true, QCodeType.codeType.name.asc());
    }


    public List<CodeValue> getAllCodeValueList() {
        return (List<CodeValue>) getEntityRepository().findAll(null, true, QCodeValue.codeValue.code.asc());
    }


}
