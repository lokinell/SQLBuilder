package com.rollen.sqlbuilder;

import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectBuilder extends AbstractSqlBuilder implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isDistinct;

    private List<String> tableNames = new ArrayList<String>();

    private List<String> columns = new ArrayList<String>();

    private List<String> wheres = new ArrayList<String>();

    private List<String> orderBys = new ArrayList<String>();

    private List<String> groupBys = new ArrayList<String>();

    private List<String> havings = new ArrayList<String>();

    private List<String> joins = new ArrayList<String>();

    private List<String> leftJoins = new ArrayList<String>();

    private boolean forUpdate;

    private boolean noWait;

    public SelectBuilder() {
    }

    public SelectBuilder(String tableName) {
        Validate.notNull(tableName);
        tableNames.add(tableName);
    }

    public SelectBuilder(SelectBuilder other) {
        Validate.notNull(other);

        this.isDistinct = other.isDistinct;
        this.forUpdate = other.forUpdate;
        this.noWait = other.noWait;

        this.tableNames.addAll(other.tableNames);
        this.columns.addAll(other.columns);
        this.wheres.addAll(other.wheres);
        this.orderBys.addAll(other.orderBys);
        this.groupBys.addAll(other.groupBys);
        this.havings.addAll(other.havings);
        this.joins.addAll(other.joins);
        this.leftJoins.addAll(other.leftJoins);

    }

    public SelectBuilder where(String expr){
        Validate.notNull(expr);
        wheres.add(expr);
        return this;
    }

    public SelectBuilder and(String expr){
        Validate.notNull(expr);
        return where(expr);
    }

    public SelectBuilder column(String name) {
        columns.add(name);
        return this;
    }

    public SelectBuilder column(String name, boolean isGroupBy){
        columns.add(name);
        if(isGroupBy){
            groupBys.add(name);
        }
        return this;
    }

    public SelectBuilder clone(){
        return new SelectBuilder(this);
    }

    public SelectBuilder distinct() {
        this.isDistinct = true;
        return this;
    }

    public SelectBuilder forUpdate() {
        forUpdate = true;
        return this;
    }

    public SelectBuilder from(String table) {
        tableNames.add(table);
        return this;
    }

    public SelectBuilder groupBy(String expr) {
        groupBys.add(expr);
        return this;
    }

    public SelectBuilder having(String expr) {
        havings.add(expr);
        return this;
    }

    public SelectBuilder join(String join) {
        joins.add(join);
        return this;
    }

    public SelectBuilder leftJoin(String join) {
        leftJoins.add(join);
        return this;
    }

    public SelectBuilder noWait() {
        if (!forUpdate) {
            throw new RuntimeException("noWait without forUpdate cannot be called");
        }
        noWait = true;
        return this;
    }

    public SelectBuilder orderBy(String name) {
        orderBys.add(name);
        return this;
    }

    public SelectBuilder orderBy(String name, boolean ascending) {
        if (ascending) {
            orderBys.add(name + " ASC");
        } else {
            orderBys.add(name + " DESC");
        }
        return this;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder("SELECT ");

        if (isDistinct) {
            stringBuilder.append("DISTINCT ");
        }

        if (columns.size() == 0) {
            stringBuilder.append("*");
        } else {
            appendList(stringBuilder, columns, "", ", ");
        }

        appendList(stringBuilder, tableNames, " FROM ", ", ");
        appendList(stringBuilder, joins, " JOIN ", " JOIN ");
        appendList(stringBuilder, leftJoins, " LEFT JOIN ", " LEFT JOIN ");
        appendList(stringBuilder, wheres, " WHERE ", " AND ");
        appendList(stringBuilder, groupBys, " GROUP BY ", ", ");
        appendList(stringBuilder, havings, " HAVING ", " AND ");
        appendList(stringBuilder, orderBys, " ORDER BY ", ", ");

        if (forUpdate) {
            stringBuilder.append(" FOR UPDATE");
            if (noWait) {
                stringBuilder.append(" NOWAIT");
            }
        }

        return stringBuilder.toString();
    }
}
