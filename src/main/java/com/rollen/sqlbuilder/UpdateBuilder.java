package com.rollen.sqlbuilder;

import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UpdateBuilder extends AbstractSqlBuilder implements Serializable {
    private final static long serialVersionUID = 1L;

    private String tableName;

    private List<String> sets = new ArrayList<String>();

    private List<String> wheres = new ArrayList<String>();

    public UpdateBuilder(String tableName) {
        this.tableName = tableName;
    }

    public UpdateBuilder set(String expr) {
        Validate.notNull(expr);
        sets.add(expr);
        return this;
    }

    public UpdateBuilder wheres(String expr) {
        Validate.notNull(expr);
        wheres.add(expr);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ").append(tableName);
        appendList(stringBuilder, sets, " SET ", ", ");
        appendList(stringBuilder, wheres, " WHERE ", " AND ");
        return stringBuilder.toString();
    }
}
