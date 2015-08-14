package com.saas.code.converter;

import com.saas.code.entity.CodeValue;
import com.saas.code.repository.CodeValueRepository;
import com.saas.core.util.KeyValue;
import com.saas.tenant.TenantContextHolder;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is not a really a converter. It is use load data for options.
 *
 * @author 
 * @since 11/03/2013 3:13 PM
 */
@Component
public class CodeTypeOptionsConverter {

    private static CodeTypeOptionsConverter _instance;

    @Inject
    protected CodeValueRepository codeValueRepository;


    @PostConstruct
    public void init() {
        _instance = this;
    }


    public static CodeTypeOptionsConverter getInstance() {
        return _instance;
    }


    public List<KeyValue<String>> loadOptions(String codeTypeId) {
        if (codeTypeId != null) {
            List<CodeValue> values = codeValueRepository.findByCodeTypeId(codeTypeId);

            if (values != null) {
                List<KeyValue<String>> results = new ArrayList<KeyValue<String>>();
                for (CodeValue value : values) {
                    results.add(new KeyValue<String>(value.getCode(), value.getLabel()));
                }

                return results;
            }
        }
        return Collections.EMPTY_LIST;
    }


}
