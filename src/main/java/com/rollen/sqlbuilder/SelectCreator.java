package com.rollen.sqlbuilder;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 * 一个简单的示例：
 * <p/>
 * PreparedStatementCreator psc = new SelectCreator()
 * .column("name")
 * .column("salary")
 * .from("emp")
 * .whereEquals("id", employeeId)
 * .and("salary > :limit")
 * .setParameter("limit", 100000);
 * <p/>
 * new JdbcTemplate(dataSource).query(psc, new RowMapper() { ... });
 */
public class SelectCreator implements Cloneable, PreparedStatementCreator, Serializable {

    private static final long serialVersionUID = 1L;

    private SelectBuilder builder = new SelectBuilder();

    private ParameterizedPreparedStatmentCreator ppsc = new ParameterizedPreparedStatmentCreator();

    private int paramIndex;

    public SelectCreator() {
    }


    protected SelectCreator(SelectCreator other) {
        this.builder = other.builder.clone();
        this.paramIndex = other.paramIndex;
        this.ppsc = other.ppsc.clone();
    }


    public String allocateParameter() {
        return "param" + paramIndex++;
    }

    public SelectCreator and(String expr) {
        builder.and(expr);
        return this;
    }

    @Override
    public SelectCreator clone() {
        return new SelectCreator(this);
    }

    public SelectCreator column(String name) {
        builder.column(name);
        return this;
    }

    public SelectCreator column(String name, boolean groupBy) {
        builder.column(name, groupBy);
        return this;
    }


    public PreparedStatementCreator count(final Dialect dialect) {
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                ppsc.setSql(dialect.createCountSelect(builder.toString()));
                return ppsc.createPreparedStatement(con);
            }
        };
    }

    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
        ppsc.setSql(builder.toString());
        return ppsc.createPreparedStatement(conn);
    }

    public SelectCreator distinct() {
        builder.distinct();
        return this;
    }

    public SelectCreator forUpdate() {
        builder.forUpdate();
        return this;
    }

    public SelectCreator from(String table) {
        builder.from(table);
        return this;
    }

    public SelectCreator groupBy(String expr) {
        builder.groupBy(expr);
        return this;
    }

    public SelectCreator having(String expr) {
        builder.having(expr);
        return this;
    }

    public SelectCreator join(String join) {
        builder.join(join);
        return this;
    }

    public SelectCreator leftJoin(String join) {
        builder.leftJoin(join);
        return this;
    }

    public SelectCreator noWait() {
        builder.noWait();
        return this;
    }

    public SelectCreator orderBy(String name) {
        builder.orderBy(name);
        return this;
    }

    public SelectCreator orderBy(String name, boolean ascending) {
        builder.orderBy(name, ascending);
        return this;
    }


    public PreparedStatementCreator page(final Dialect dialect, final int limit, final int offset) {
        return new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                ppsc.setSql(dialect.createPageSelect(builder.toString(), limit, offset));
                return ppsc.createPreparedStatement(con);
            }
        };
    }

    public SelectCreator setParameter(String name, Object value) {
        ppsc.setParams(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(builder.toString());
        List<String> params = new ArrayList<String>(ppsc.getParamesMap().keySet());
        Collections.sort(params);
        for (String s : params) {
            sb.append(", ").append(s).append("=").append(ppsc.getParamesMap().get(s));
        }
        return sb.toString();
    }

    public SelectCreator where(String expr) {
        builder.where(expr);
        return this;
    }

    public SelectCreator whereEquals(String expr, Object value) {

        String param = allocateParameter();

        builder.where(expr + " = :" + param);
        ppsc.setParams(param, value);

        return this;
    }
}