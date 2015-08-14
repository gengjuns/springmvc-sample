package com.saas.core.web.extension;

import com.saas.core.entity.Entity;
import com.saas.core.repository.EntityRepository;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.Serializable;

/**
 * @author 
 * @since 03/03/2013 3:50 PM
 */
public abstract class ControllerExtensionAdaptor<T extends Entity<ID>, ID extends Serializable, R extends EntityRepository<T, ID>> implements ControllerExtension<T, ID, R> {

    protected String prefix;

    protected String modelAttributeName;

    protected R entityRepository;


    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    @Override
    public void setModelAttributeName(String modelAttributeName) {
        this.modelAttributeName = modelAttributeName;

    }


    @Override
    public void setEntityRepository(R entityRepository) {
        this.entityRepository = entityRepository;
    }


    @Override
    public void validate(T entity, BindingResult bindingResult, Model model) {

    }


    @Override
    public void initCreate(Model model) {

    }


    @Override
    public void initUpdate(Model model) {

    }


    @Override
    public void preCreate(T entity, BindingResult bindingResult, Model model) {

    }


    @Override
    public void postCreate(T entity, BindingResult bindingResult, Model model) {

    }


    @Override
    public String pathAfterCreate(T entity, BindingResult bindingResult, Model model, String defaultPath) {
        return defaultPath;
    }


    @Override
    public String pathAfterUpdate(T entity, BindingResult bindingResult, Model model, String defaultPath) {
        return defaultPath;
    }


    @Override
    public void handleCreateError(T entity, BindingResult bindingResult, Model model) {

    }


    @Override
    public void preUpdate(T entity, BindingResult bindingResult, Model model) {

    }


    @Override
    public void postUpdate(T entity, BindingResult bindingResult, Model model) {

    }


    @Override
    public void handleUpdateError(T entity, BindingResult bindingResult, Model model) {

    }


    @Override
    public void populateEditForm(T entity, Model model) {

    }
}
