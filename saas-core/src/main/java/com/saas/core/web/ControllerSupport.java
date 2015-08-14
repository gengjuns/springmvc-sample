package com.saas.core.web;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saas.Constants;
import com.saas.core.context.ApplicationContextHolder;
import com.saas.core.entity.Entity;
import com.saas.core.repository.EntityRepository;
import com.saas.core.util.BeanUtils;
import com.saas.core.util.StringUtils;
import com.saas.core.web.search.EntityJSONParam;
import com.saas.core.web.search.EntitySearchRequest;
import com.saas.core.web.search.EntitySearchResult;


public abstract class ControllerSupport<T extends Entity<ID>, ID extends Serializable, R extends EntityRepository<T, ID>> {


    protected Class<T> entityClass;

    protected String prefix;

    protected R entityRepository;

    protected String modelAttributeName;

    @Inject
    protected MessageSource messageSource;

    @PostConstruct
    protected void init() {
    }


    public Class<T> getEntityClass() {
        if (entityClass == null) {
            ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
            entityClass = (Class<T>) (superclass).getActualTypeArguments()[0];
        }
        return entityClass;
    }


    public String getModelAttributeName() {
        if (modelAttributeName == null) {
            modelAttributeName = StringUtils.uncapitalize(this.getEntityClass().getSimpleName());
        }
        return modelAttributeName;
    }

    public String getPrefix() {
        if (prefix == null) {
            RequestMapping mapping = this.getClass().getAnnotation(RequestMapping.class);
            if (mapping != null) {
                String[] values = mapping.value();
                if (values != null && values.length > 0) {
                    String value = values[0].trim();
                    if (value != null && !value.isEmpty()) {
                        if (value.startsWith("/")) {
                            value = value.substring(1);
                        }
                        if (value.endsWith("/")) {
                            value = value.substring(0, value.length() - 1);
                        }
//                        if (value.contains("/")) {
//                            value = value.substring(value.lastIndexOf("/") + 1, value.length());
//                        }
                        prefix = value;
                    }
                }
            }
            if (prefix == null) {
                prefix = "";
            }
        }
        return prefix;
    }


    public R getEntityRepository() {
        if (entityRepository == null) {
            ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<R> repositoryClass = (Class<R>) (superclass).getActualTypeArguments()[2];
            entityRepository = ApplicationContextHolder.getContext().getBean(repositoryClass);
        }
        return entityRepository;
    }


    public String getViewName(String path) {
        return getViewName(path, null, false);
    }


    public String getViewName(String path, Object parameter) {
        return getViewName(path, parameter, false);
    }


    public String getViewName(String path, boolean redirect) {
        return getViewName(path, null, redirect);
    }


    public String getViewName(String path, Object parameter, boolean redirect) {
        StringBuilder view = new StringBuilder();
        if (redirect) {
            view.append("redirect:/");
        }
        view.append(getPrefix()).append(path);
        if (parameter != null) {
            view.append("/").append(parameter.toString());
        }
        return view.toString();
    }


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    /**
     * forward to object creation page
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createForm(Model model) {
        T entity = BeanUtils.instantiate(getEntityClass());
        model.addAttribute(getModelAttributeName(), entity);
        initCreate(model);
        populateEditForm(entity, model);
        return getViewName("/create");
    }


    /**
     * submit the request for object creation
     *
     * @param entity
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid T entity, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes) {
        validate(entity, bindingResult, model);
        model.addAttribute("success", !bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            handleCreateError(entity, bindingResult, model);
            populateEditForm(entity, model);
            return getViewName("/create");
        } else {

            preCreate(entity, bindingResult, model);
            entity = getEntityRepository().saveAndFlush(entity);
            postCreate(entity, bindingResult, model,redirectAttributes);
            //model.asMap().clear();
        }

        return pathAfterCreate(entity, bindingResult, model);
    }

    /**
     * forward to records list page
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(EntitySearchRequest<T> request, Model model) {
        return getViewName("/list");
    }


    /**
     * list out the matching records caused by kendoui
     *
     * @param request
     * @return
     */
    @Transactional
    @RequestMapping(value = "read1", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public EntitySearchResult<T> read1(@RequestBody EntitySearchRequest<T> request) {
        return request.execute(getEntityRepository(),customRead(request));
    }
    
    /**
     * list out the matching records caused by datatables
     *
     * @param request
     * @return
     */
    @Transactional
    @RequestMapping(value = "read", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
	public EntitySearchResult<T> read(@RequestBody EntityJSONParam[] params) {
		HashMap<String, String> paramMap = convertToMap(params);
		EntitySearchRequest<T> request = new EntitySearchRequest<T>(getEntityClass(),paramMap);
		preRead(request);
		return request.execute(getEntityRepository(), customRead(request));
	}

    protected Page<T> customRead(EntitySearchRequest<T> request){
    	return null;
    }
    
    protected void preRead(EntitySearchRequest<T> request){
        
    }
    /**
     * load object from database the request url pattern /update?company=1
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") ID id, Model model) {
        T entity = getEntityRepository().findOne(id);
        model.addAttribute(getModelAttributeName(), entity);
        initUpdate(model);
        populateEditForm(entity, model);
        return getViewName("/update");
    }


    /**
     * submit request for record updating.
     *
     * @param entity
     * @param bindingResult
     * @param model
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid T entity, @PathVariable("id") ID id, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes) {
        validate(entity, bindingResult, model);
        model.addAttribute("success", !bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            handleUpdateError(entity, bindingResult, model);
            populateEditForm(entity, model);
            return getViewName("/update");
        } else {
            preUpdate(entity, bindingResult, model);
            entity = getEntityRepository().saveAndFlush(entity);
            postUpdate(entity, bindingResult, model,redirectAttributes);
           // model.asMap().clear();
        }
        return pathAfterUpdate(entity, id, bindingResult, model);
    }


    /**
     * delete record from kendoui
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") ID id) {
        getEntityRepository().delete(id);
        return getViewName("/list", true);
    }


    /**
     * load entity information for view page
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/show/{id}")
    public String show(@PathVariable ID id, Model model) {
        model.addAttribute(getModelAttributeName(), getEntityRepository().findOne(id));
        return getViewName("/show");
    }


    protected void populateEditForm(T entity, Model model) {
    }


    protected String pathAfterCreate(T entity, BindingResult bindingResult, Model model) {
            return getViewName("/list", true);
    }


    protected String pathAfterUpdate(T entity, ID id, BindingResult bindingResult, Model model) {
            return getViewName("/update", id, true);
    }


    protected void validate(T entity, BindingResult bindingResult, Model model) {
    }


    protected void initCreate(Model model) {
    }


    protected void initUpdate(Model model) {
    }


    protected void preCreate(T entity, BindingResult bindingResult, Model model) {
    }


    protected void postCreate(T entity, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes) {
        List<String> messages = new ArrayList<String>();
        messages.add(messageSource.getMessage("msg_save_object_successful", null, null));
        redirectAttributes.addFlashAttribute(Constants.MESSAGE_SUCCESS, messages);
    }


    protected void handleCreateError(T entity, BindingResult bindingResult, Model model) {
    }


    protected void preUpdate(T entity, BindingResult bindingResult, Model model) {
    }


    protected void postUpdate(T entity, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes) {
        List<String> messages = new ArrayList<String>();
        messages.add(messageSource.getMessage("msg_save_object_successful", null, null));
        redirectAttributes.addFlashAttribute(Constants.MESSAGE_SUCCESS, messages);
        
    }


    protected void handleUpdateError(T entity, BindingResult bindingResult, Model model) {
        
    }

    protected HashMap<String, String> convertToMap(EntityJSONParam[] params) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (EntityJSONParam param : params) {
            map.put(param.getName(), param.getValue());
        }
        return map;
    }
    
    protected HttpServletRequest getRequest(){
    	RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)ra).getRequest();
        return request;
    }
    
    protected HttpSession getSession(){
        return getRequest().getSession(false);
    }
}
