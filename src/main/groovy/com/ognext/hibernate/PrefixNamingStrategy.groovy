package com.ognext.hibernate

import org.hibernate.cfg.ImprovedNamingStrategy

/**
 * @author Frank van Heeswijk
 */
class PrefixNamingStrategy extends ImprovedNamingStrategy {
    @Override
    String classToTableName(String className) {
        return "table_" + super.classToTableName(className)
    }

    @Override
    String propertyToColumnName(String propertyName) {
        return "col_" + super.propertyToColumnName(propertyName)
    }
}
