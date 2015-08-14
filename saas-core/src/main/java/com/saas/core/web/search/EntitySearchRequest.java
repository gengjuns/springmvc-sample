package com.saas.core.web.search;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.ComparablePath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;
import com.saas.Constants;
import com.saas.core.annotation.DateFormat;
import com.saas.core.annotation.DateTimeFormat;
import com.saas.core.context.ApplicationContextHolder;
import com.saas.core.entity.Entity;
import com.saas.core.repository.DynamicQueryEntityRepository;
import com.saas.core.util.ReflectionUtils;
import com.saas.core.util.StringUtils;
import com.saas.core.util.TypeDescriptorUtils;

/**
 * @author 
 * @since 17/01/2013 5:52 PM
 */
public class EntitySearchRequest<T extends Entity> {

    public static final Logger logger = LoggerFactory.getLogger(EntitySearchRequest.class);

    private int page = 0;

    private int pageSize = Constants.DEFAULT_PAGE_SIZE;

    protected List<SortDescriptor> sort;

    protected FilterDescriptor filter;

    // not in used yet
    protected int take = Constants.DEFAULT_PAGE_SIZE;

    // not in used yet
    protected int skip = 0;

    // not in used yet
    protected List<GroupDescriptor> group;

    // not in used yet
    protected List<AggregateDescriptor> aggregate;

    // not in used yet
    protected Map<String, Object> data = new HashMap<String, Object>();

    // not in used yet
    protected ConversionService conversionService;

    protected PageRequest pageRequest;

    //protected Predicate predicate;
    
    protected BooleanBuilder builder = new BooleanBuilder();

    protected HashMap<String, String> paramMap;

    public EntitySearchRequest() {
        // TODO Auto-generated constructor stub
    }

    public EntitySearchRequest(Class<T> clazz, HashMap<String, String> paramMap) {
        this.paramMap = paramMap;
        int pageStart = Integer.parseInt(paramMap.get("iDisplayStart"));
        int pageSize = Integer.parseInt(paramMap.get("iDisplayLength"));
        String sortField = paramMap.get("mDataProp_" + paramMap.get("iSortCol_0"));
        String sortDir = paramMap.get("sSortDir_0");
        this.setPage(pageStart / pageSize);
        this.setPageSize(pageSize);
        SortDescriptor sortDescriptor = new SortDescriptor();
        sortDescriptor.setDir(sortDir);
        sortDescriptor.setField(sortField);
        this.getSort().add(sortDescriptor);

        filter = getFilter();
        String field = null;
        String fieldValue = null;
        int iColumns = Integer.parseInt(paramMap.get("iColumns"));
        for (int i = 0; i < iColumns; i++) {
            if (StringUtils.hasText(paramMap.get("sSearch_" + i))) {
                field = paramMap.get("mDataProp_" + i);
                fieldValue = paramMap.get("sSearch_" + i);
                FilterDescriptor filterDescriptor = new FilterDescriptor();
                filterDescriptor.setField(field);
                filterDescriptor.setLogic("and");
                filterDescriptor.setOperator("startswith");
                filterDescriptor.setValue(fieldValue);
                getFilter().getFilters().add(filterDescriptor);
            }
        }
        PathBuilder pathBuilder = new PathBuilder(clazz, StringUtils.uncapitalize(clazz.getSimpleName()));

        filter(builder, pathBuilder, getFilter(), clazz);
        //predicate = builder.getValue();
        pageRequest = sortAndPage(getSort(), getPage(), getPageSize());
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<SortDescriptor> getSort() {
        if (sort == null) {
            sort = new ArrayList<SortDescriptor>();
        }
        return sort;
    }

    public void setSort(List<SortDescriptor> sort) {
        this.sort = sort;
    }

    public FilterDescriptor getFilter() {
        if (filter == null) {
            filter = new FilterDescriptor();
        }
        return filter;
    }

    public void setFilter(FilterDescriptor filter) {
        this.filter = filter;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public List<GroupDescriptor> getGroup() {
        if (group == null) {
            group = new ArrayList<GroupDescriptor>();
        }
        return group;
    }

    public void setGroup(List<GroupDescriptor> group) {
        this.group = group;
    }

    public List<AggregateDescriptor> getAggregate() {
        if (aggregate == null) {
            aggregate = new ArrayList<AggregateDescriptor>();
        }
        return aggregate;
    }

    public void setAggregate(List<AggregateDescriptor> aggregate) {
        this.aggregate = aggregate;
    }

    public Map<String, Object> getData() {
        if (data == null) {
            data = new HashMap<String, Object>();
        }
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public ConversionService getConversionService() {
        if (conversionService == null) {
            conversionService = ApplicationContextHolder.getContext().getBean(ConversionService.class);
        }
        return conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @SuppressWarnings("unchecked")
    public EntitySearchResult<T> execute(DynamicQueryEntityRepository<T> repository, Page<T> customerPage) {
        // BooleanBuilder builder = new BooleanBuilder();
        // PathBuilder pathBuilder = new PathBuilder(clazz, StringUtils.uncapitalize(clazz.getSimpleName()));
        //
        // filter(builder, pathBuilder, getFilter(), clazz);
        // todo: future group and aggregates
        // PageRequest pageRequest = sortAndPage(getSort(), getPage(), getPageSize());

        Page<T> page = null;
        if (customerPage == null) {
            page = repository.findAll(builder.getValue(), pageRequest, true);
        } else {
            page = customerPage;
        }
        EntitySearchResult<T> result = page != null ? new EntitySearchResult<T>(page.getTotalElements(), page.getContent()) : new EntitySearchResult<T>();
        return result;
    }

    protected PageRequest sortAndPage(List<SortDescriptor> sorting, int pageNo, int pageSize) {
        Sort pageSort = null;
        if (sorting != null && !sorting.isEmpty()) {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();
            for (SortDescriptor sd : sorting) {
                orders.add(new Sort.Order("desc".equalsIgnoreCase(sd.getDir()) ? Sort.Direction.DESC : Sort.Direction.ASC, sd.getField()));
            }
            pageSort = new Sort(orders);
        }
        PageRequest pageRequest = new PageRequest(pageNo > 0 ? pageNo: pageNo, pageSize, pageSort);
        return pageRequest;
    }

    protected void filter(BooleanBuilder builder, PathBuilder pathBuilder, FilterDescriptor filter, Class clazz) {
        if (filter != null) {
            List<FilterDescriptor> filters = filter.getFilters();

            for (FilterDescriptor descriptor : filters) {
                if (!descriptor.getFilters().isEmpty()) {
                    filter(builder, pathBuilder, descriptor, clazz);
                } else {
                    filterCondition(builder, pathBuilder, descriptor, clazz, filter);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void filterCondition(BooleanBuilder builder, PathBuilder pathBuilder, FilterDescriptor descriptor, Class clazz, FilterDescriptor parent) {
        if (!StringUtils.hasText(descriptor.getField()) || !StringUtils.hasText(descriptor.getOperator()) || descriptor.getValue() == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("filter name '" + descriptor.getField() + "' or value '" + descriptor.getValue() + "' is null");
            }
            return;
        }

        Field field = ReflectionUtils.findField(clazz, descriptor.getField());
        if (field == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("PropertyDescriptor for name '" + descriptor.getField() + "' is null");
            }
            return;
        }
        TypeDescriptor typeDescriptor;
        if (field.getType() == Date.class) {
            if (field.getAnnotation(DateFormat.class) != null) {
                typeDescriptor = TypeDescriptorUtils.DateFormat;
            } else if (field.getAnnotation(DateTimeFormat.class) != null) {
                typeDescriptor = TypeDescriptorUtils.DateTimeFormat;
            } else {
                typeDescriptor = TypeDescriptor.valueOf(field.getType());
            }
        } else {
            typeDescriptor = TypeDescriptor.valueOf(field.getType());
        }
        if (typeDescriptor == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("TypeDescriptor for name '" + descriptor.getField() + "' is null");
            }
            return;
        }

        Path path;
        if (field.getType() == String.class) {
            path = pathBuilder.getString(descriptor.getField());
        } else {
            path = pathBuilder.getComparable(descriptor.getField(), field.getType());
        }
        if (path == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Path for name '" + descriptor.getField() + "' is null");
            }
            return;
        }

        Object value;
        if (typeDescriptor.equals(TypeDescriptorUtils.String)) {
            value = descriptor.getValue();
        } else {
            try {
                value = getConversionService().convert(descriptor.getValue(), TypeDescriptorUtils.String, typeDescriptor);
            } catch (Exception e) {
                value = null;
            }
        }

        if (value == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Value for name '" + descriptor.getField() + "' is null");
            }
            return;
        }

        EntitySearchLogic condition = EntitySearchLogic.resolve(descriptor.getLogic() != null ? descriptor.getLogic() : parent.getLogic());
        EntitySearchOperator operator = EntitySearchOperator.resolve(descriptor.getOperator());

        if (operator == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Operator for name '" + descriptor.getField() + "' is null");
            }
            return;
        }
        BooleanExpression expression = null;
        switch (operator) {
        case eq:
            if (path instanceof StringPath) {
                if (descriptor.isIgnoreCase()) {
                    expression = ((StringPath) path).equalsIgnoreCase((String) value);
                } else {
                    expression = ((StringPath) path).eq((String) value);
                }
            } else {
                expression = ((ComparablePath) path).eq(value);
            }
            break;
        case neq:
            if (path instanceof StringPath) {
                if (descriptor.isIgnoreCase()) {
                    expression = ((StringPath) path).notEqualsIgnoreCase((String) value);
                } else {
                    expression = ((StringPath) path).ne((String) value);
                }
            } else if (path instanceof DateTimePath) {
                expression = ((ComparablePath) path).ne(value);
            }
            break;
        case gt:
            expression = ((ComparablePath) path).gt((Comparable) value);
            break;
        case gte:
            expression = ((ComparablePath) path).between((Comparable) value, null);
            break;
        case lt:
            expression = ((ComparablePath) path).lt((Comparable) value);
            break;
        case lte:
            expression = ((ComparablePath) path).between(null, (Comparable) value);
            break;
        case startswith:
            if (descriptor.isIgnoreCase()) {
                expression = ((StringPath) path).startsWithIgnoreCase((String) value);
            } else {
                expression = ((StringPath) path).startsWith((String) value);
            }
            break;
        case contains:
        case doesnotcontain:
            if (descriptor.isIgnoreCase()) {
                expression = ((StringPath) path).containsIgnoreCase((String) value);
            } else {
                expression = ((StringPath) path).contains((String) value);
            }
            if (operator == EntitySearchOperator.doesnotcontain) {
                condition = (condition == EntitySearchLogic.and) ? EntitySearchLogic.andNot : EntitySearchLogic.orNot;
            }
            break;
        case endswith:
            if (descriptor.isIgnoreCase()) {
                expression = ((StringPath) path).endsWithIgnoreCase((String) value);
            } else {
                expression = ((StringPath) path).endsWith((String) value);
            }
            break;
        }
        if (expression == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Condition Expression for name '" + descriptor.getField() + "' is null");
            }
            return;
        }

        addCondition(builder, expression, condition);
    }

    protected void addCondition(BooleanBuilder builder, BooleanExpression expression, EntitySearchLogic condition) {
        switch (condition) {
        case and:
            builder.and(expression);
            break;
        case or:
            builder.or(expression);
            break;
        case andNot:
            builder.andNot(expression);
            break;
        case orNot:
            builder.orNot(expression);
            break;
        }
    }

    public static class SortDescriptor {

        private String field;

        private String dir;

        public SortDescriptor() {
        }

        public SortDescriptor(String field, String dir) {
            this.field = field;
            this.dir = dir;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }

    public static class GroupDescriptor extends SortDescriptor {

        private List<AggregateDescriptor> aggregates;

        public GroupDescriptor() {
            aggregates = new ArrayList<AggregateDescriptor>();
        }

        public List<AggregateDescriptor> getAggregates() {
            return aggregates;
        }
    }

    public static class AggregateDescriptor {

        private String field;

        private String aggregate;

        public AggregateDescriptor() {
        }

        public AggregateDescriptor(String field, String aggregate) {
            this.field = field;
            this.aggregate = aggregate;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getAggregate() {
            return aggregate;
        }

        public void setAggregate(String aggregate) {
            this.aggregate = aggregate;
        }
    }

    public static class FilterDescriptor {

        private String logic;

        private List<FilterDescriptor> filters = new ArrayList<FilterDescriptor>();

        private String field;

        private Object value;

        private String operator;

        private boolean ignoreCase = true;

        public FilterDescriptor() {
        }

        public FilterDescriptor(String field, Object value, String operator) {
            this.field = field;
            this.value = value;
            this.operator = operator;
        }

        public FilterDescriptor(String field, Object value, String operator, String logic) {
            this.field = field;
            this.value = value;
            this.operator = operator;
            this.logic = logic;
        }

        public FilterDescriptor(String field, Object value, String operator, String logic, boolean ignoreCase) {
            this.field = field;
            this.value = value;
            this.operator = operator;
            this.logic = logic;
            this.ignoreCase = ignoreCase;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public List<FilterDescriptor> getFilters() {
            return filters;
        }

        public void setFilters(List<FilterDescriptor> filters) {
            this.filters = filters;
        }
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }



    public BooleanBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(BooleanBuilder builder) {
        this.builder = builder;
    }

    public HashMap<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(HashMap<String, String> paramMap) {
        this.paramMap = paramMap;
    }

}
