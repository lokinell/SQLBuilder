package com.rollen.sqlbuilder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 * 你可以这么使用它：
 * <p/>
 * PreparedStatementCreator psc = new UpdateCreator(&quot;emp&quot;).setValue(&quot;name&quot;, employee.getName()).whereEquals(&quot;id&quot;,
 * employeeId);
 * <p/>
 * new JdbcTemplate(dataSource).update(psc);
 */
public class UpdateCreator implements PreparedStatementCreator, Serializable {

    private static final long serialVersionUID = 1;

    private UpdateBuilder builder;

    private ParameterizedPreparedStatmentCreator ppsc = new ParameterizedPreparedStatmentCreator();

    public UpdateCreator(String table) {
        builder = new UpdateBuilder(table);
    }

    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
        ppsc.setSql(builder.toString());
        return ppsc.createPreparedStatement(conn);
    }

    public UpdateCreator set(String expr) {
        builder.set(expr);
        return this;
    }

    public UpdateCreator setParameter(String name, Object value) {
        ppsc.setParams(name, value);
        return this;
    }

    public UpdateCreator setValue(String column, Object value) {
        builder.set(column + " = :" + column);
        ppsc.setParams(column, value);
        return this;
    }

    public UpdateCreator where(String expr) {
        builder.wheres(expr);
        return this;
    }

    public UpdateCreator whereEquals(String column, Object value) {
        builder.wheres(column + " = :" + column);
        ppsc.setParams(column, value);
        return this;
    }

}
