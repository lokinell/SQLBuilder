package com.rollen.sqlbuilder;


import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeleteBuilder extends AbstractSqlBuilder implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tableName;

    private List<String> wheres = new ArrayList<String>();

    public DeleteBuilder(String tableName) {
        this.tableName = tableName;
    }

    public DeleteBuilder set(String expr) {
        Validate.notNull(expr);
        wheres.add(expr);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM ").append(tableName);
        appendList(stringBuilder, wheres, " WHERE ", " AND ");
        return stringBuilder.toString();
    }
}
