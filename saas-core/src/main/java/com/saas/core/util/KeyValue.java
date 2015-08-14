package com.saas.core.util;

import java.io.Serializable;

/**
 * @author 
 * @since 03/02/2013 6:08 PM
 */
public class KeyValue<K extends Serializable> implements Serializable, Comparable<KeyValue> {

    protected K key;

    protected String value;


    public KeyValue(K key, String value) {
        this.key = key;
        this.value = value;
    }


    public K getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }


    @Override
    public int compareTo(KeyValue o) {
        return this.getValue().compareTo(o.getValue());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyValue)) return false;

        KeyValue keyValue = (KeyValue) o;

        if (!key.equals(keyValue.key)) return false;
        if (!value.equals(keyValue.value)) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }


}
