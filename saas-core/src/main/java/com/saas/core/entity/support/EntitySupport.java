package com.saas.core.entity.support;

import java.io.Serializable;
import java.rmi.dgc.VMID;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Transient;

import com.saas.core.entity.Entity;
import com.saas.core.event.EntityEventListener;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@MappedSuperclass
@EntityListeners(EntityEventListener.class)
public abstract class EntitySupport<ID extends Serializable> implements Entity<ID> {

    @Id
    @GenericGenerator(name="systemUUID", strategy="uuid.hex")
    @GeneratedValue(generator="systemUUID")
    protected String id;

    @Transient
    protected transient Object _vmId;


    @Override
    public String getId() {
        return id;
    }


    @Override
    public void setId(String id) {
        this.id = id;
    }


    protected Object _getId() {
        if (_vmId == null) {
            if (id != null) {
                _vmId = id;
            } else {
                _vmId = new VMID();
            }
        } else {
            //hack the equals/hash once the id is set.
            if (id != null) {
                _vmId = id;
            }
        }
        return _vmId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntitySupport)) return false;

        EntitySupport that = (EntitySupport) o;

        return _getId().equals(((EntitySupport) o)._getId());
    }


    @Override
    public int hashCode() {
        return _getId().hashCode();
    }


}
