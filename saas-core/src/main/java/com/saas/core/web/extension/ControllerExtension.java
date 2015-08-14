package com.saas.core.web.extension;

import com.saas.core.entity.Entity;
import com.saas.core.repository.EntityRepository;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.Serializable;

/**
 * @author 
 * @since 03/03/2013 3:43 PM
 */
public interface ControllerExtension<T extends Entity<ID>, ID extends Serializable, R extends EntityRepository<T, ID>> {

    void setPrefix(String prefix);


    void setModelAttributeName(String modelAttributeName);


    void setEntityRepository(R entityRepository);


    void validate(T entity, BindingResult bindingResult, Model model);


    void initCreate(Model model);


    void initUpdate(Model model);


    void preCreate(T entity, BindingResult bindingResult, Model model);


    void postCreate(T entity, BindingResult bindingResult, Model model);


    String pathAfterCreate(T entity, BindingResult bindingResult, Model model, String defaultPath);


    String pathAfterUpdate(T entity, BindingResult bindingResult, Model model, String defaultPath);


    void handleCreateError(T entity, BindingResult bindingResult, Model model);


    void preUpdate(T entity, BindingResult bindingResult, Model model);


    void postUpdate(T entity, BindingResult bindingResult, Model model);


    void handleUpdateError(T entity, BindingResult bindingResult, Model model);


    void populateEditForm(T entity, Model model);

}
