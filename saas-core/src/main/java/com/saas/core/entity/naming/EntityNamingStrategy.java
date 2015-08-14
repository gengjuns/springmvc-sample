package com.saas.core.entity.naming;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * Automatic naming strategy for JPA table name
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public class EntityNamingStrategy extends ImprovedNamingStrategy {

    private static final String COLUMN_PREFIX = "";

    private static final String TABLE_PREFIX = "tbl_";


    @Override
    public String classToTableName(String className) {
        return TABLE_PREFIX + super.classToTableName(className);
    }


    @Override
    public String propertyToColumnName(String propertyName) {
        return COLUMN_PREFIX + super.propertyToColumnName(propertyName);
    }


    @Override
    public String tableName(String tableName) {
        String name = super.tableName(tableName);
        if (!name.startsWith(TABLE_PREFIX)) {
            return TABLE_PREFIX + name;
        }
        return name;
    }


    @Override
    public String columnName(String columnName) {
        return COLUMN_PREFIX + super.columnName(columnName);
    }


    @Override
    public String collectionTableName(String ownerEntity,
                                      String ownerEntityTable,
                                      String associatedEntity,
                                      String associatedEntityTable,
                                      String propertyName) {
        return tableName(ownerEntityTable + '_' + super.propertyToColumnName(propertyName));
    }


    @Override
    public String logicalCollectionTableName(String tableName,
                                             String ownerEntityTable,
                                             String associatedEntityTable,
                                             String propertyName) {
        if (tableName != null) {
            return tableName;
        }

        return tableName(ownerEntityTable + '_' + super.propertyToColumnName(propertyName));
    }


}
