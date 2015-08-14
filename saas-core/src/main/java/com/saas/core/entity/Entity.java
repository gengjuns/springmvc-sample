package com.saas.core.entity;

import java.io.Serializable;

/**
 * Entity marker interface
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public interface Entity<ID extends Serializable> extends Serializable {

    String getId();


    void setId(String id);


}
