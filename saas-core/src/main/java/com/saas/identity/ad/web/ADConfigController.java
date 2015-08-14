package com.saas.identity.ad.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saas.core.util.StringUtils;
import com.saas.core.web.ControllerSupport;
import com.saas.identity.ad.entity.ADConfig;
import com.saas.identity.ad.entity.CustomAD;
import com.saas.identity.ad.entity.KeyValue;
import com.saas.identity.ad.repository.ADConfigRepositroy;
import com.saas.identity.ad.repository.CustomADRepository;

@Controller
@RequestMapping("/ad")
public class ADConfigController extends ControllerSupport<ADConfig, Long, ADConfigRepositroy> {

    @Inject
    protected CustomADRepository customADRepository;

    @Inject
    protected ADConfigRepositroy adConfigRepositroy;

    @Inject
    protected PasswordEncoder passwordEncoder;

    protected LDAPService ldapService = new LDAPService();

    protected List<KeyValue> customfields = new ArrayList<KeyValue>();

    protected List<KeyValue> adfields = new ArrayList<KeyValue>();

    private static final String CONNECT_FAILED = "0";

    private static final String CONNECT_SUCCESS = "1";

    private static final String NO_ATTRIBUTE = "2";

    public List<KeyValue> getCustomFields() {
        if (customfields.isEmpty()) {
            customfields = new ArrayList<KeyValue>();
            customfields.add(new KeyValue("FIRST_NAME", "FIRST_NAME"));
            customfields.add(new KeyValue("LAST_NAME", "LAST_NAME"));
            customfields.add(new KeyValue("USER_NAME", "USER_NAME"));
        }

        return customfields;
    }

    public List<KeyValue> getADFields(List<String> searchAttributeNames) {
        adfields.clear();
        for (String attributeName : searchAttributeNames) {
            adfields.add(new KeyValue(attributeName, attributeName));
        }
        return adfields;
    }

    @RequestMapping(value = "/testconnect", method = RequestMethod.POST)
    @ResponseBody
    public String testConnect(@ModelAttribute ADConfig adConfig) {
        String host = adConfig.getHost();
        String password = adConfig.getPassword();
        String port = adConfig.getPort();
        String loginDN = adConfig.getLoginDN();
        int ldapVersion = adConfig.getLdapVersion();
        String searchDN = adConfig.getSearchDN();
        int searchScope = adConfig.getSearchScope();
        String searchFilter = adConfig.getSearchFilter();

        boolean canConnect = ldapService.getConnection(host, password, port, ldapVersion, loginDN);

        String result;

        if (!canConnect) {
            result = CONNECT_FAILED;
            adConfig.setCanConnect(false);
        } else {
            List<String> searchAttributeNames = ldapService.searchAttributeNames(searchDN, searchScope, searchFilter);
            if (searchAttributeNames.isEmpty()) {
                result = NO_ATTRIBUTE;
                adConfig.setCanConnect(false);
            } else {
                getADFields(searchAttributeNames);
                result = CONNECT_SUCCESS;
                adConfig.setCanConnect(true);
            }
        }
        ldapService.disConnection();
        return result;
    }

    @RequestMapping(value = "/getoptions", method = RequestMethod.GET)
    @ResponseBody
    public List<List<KeyValue>> getOptions() {
        List<List<KeyValue>> optionList = new ArrayList<List<KeyValue>>();
        optionList.add(getCustomFields());
        optionList.add(adfields);
        return optionList;
    }

    @Override
    protected void initCreate(Model model) {
        super.initCreate(model);
        customfields.clear();
        adfields.clear();
    }

    @Override
    protected void initUpdate(Model model) {
        super.initUpdate(model);

        ADConfig adConfig = (ADConfig) model.asMap().get("aDConfig");

        testConnect(adConfig);

        model.addAttribute("customfields", getCustomFields());
        model.addAttribute("adfields", adfields);
    }

    @Override
    protected void preCreate(ADConfig entity, BindingResult bindingResult, Model model) {
        super.preCreate(entity, bindingResult, model);
        putADIndex(entity);
    }

    @Override
    protected void preUpdate(ADConfig entity, BindingResult bindingResult, Model model) {
        super.preUpdate(entity, bindingResult, model);
        putADIndex(entity);

    }

    private void putADIndex(ADConfig entity) {
        List<CustomAD> customADsTmp = entity.getCustomADs();
        List<CustomAD> customADs = new ArrayList<CustomAD>();
        for (CustomAD customAD : customADsTmp) {
            if (StringUtils.isEmpty(customAD.getAdField()) || StringUtils.isEmpty(customAD.getCustomField())) {
                continue;
            }
            customADs.add(customAD);
        }
        for (CustomAD customAD : customADs) {
            customAD.setAdIndex(customADs.indexOf(customAD));
            customAD.setAdConfig(entity);
        }
        entity.setCustomADs(customADs);
    }
}
