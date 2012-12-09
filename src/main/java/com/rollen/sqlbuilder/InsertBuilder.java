package com.rollen.sqlbuilder;

import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InsertBuilder extends AbstractSqlBuilder implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tableName;

    private List<String> columns = new ArrayList<String>();

    private List<String> values = new ArrayList<String>();

    public InsertBuilder(String tableName) {
        this.tableName = tableName;
    }

    public InsertBuilder set(String column, String value) {
        Validate.notNull(column);
        Validate.notNull(value);
        columns.add(column);
        values.add(value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        appendList(stringBuilder, columns, "", ", ");
        stringBuilder.append(") VALUES (");
        appendList(stringBuilder, values, "", ", ");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
