package com.rollen.sqlbuilder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.PreparedStatementCreator;

public class ParameterizedPreparedStatmentCreator implements Cloneable, Serializable,PreparedStatementCreator {

    private static final long serialVersionUID = 1L;

    private String sql;

    private Map<String, Object> paramesMap = new HashMap<String, Object>();

    private static final String NAME_REGEX = "[a-zA-Z][_a-zA-Z0-9]*";

    private static final String PARAM_REGEX = ":(" + NAME_REGEX + ")";

    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX, Pattern.CASE_INSENSITIVE);

    private static final Pattern PARAM_PATTERN = Pattern.compile(PARAM_REGEX, Pattern.CASE_INSENSITIVE);

    static class SQLAndParams {
        private String sql;
        private List<Object> params;

        private SQLAndParams(String sql, List<Object> params) {
            this.params = params;
            this.sql = sql;
        }

        public String getSql() {
            return this.sql;
        }

        public List<Object> getParams() {
            return this.params;
        }
    }

    public ParameterizedPreparedStatmentCreator(){

    }

    public ParameterizedPreparedStatmentCreator(ParameterizedPreparedStatmentCreator other) {
        this.sql = other.sql;
        for (String key : other.paramesMap.keySet()) {
            this.paramesMap.put(key, other.paramesMap.get(key));
        }
    }

    public ParameterizedPreparedStatmentCreator clone() {
        return new ParameterizedPreparedStatmentCreator(this);
    }


    public SQLAndParams createSqlWithParams() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Object> paramsValue = new ArrayList<Object>();

        Matcher matcher = PARAM_PATTERN.matcher(sql);
        int index = 0;
        while (matcher.find(index)) {
            stringBuilder.append(sql.substring(index, matcher.start()));
            String name = matcher.group(1);
            index = matcher.end();
            if (this.paramesMap.containsKey(name)) {
                stringBuilder.append("?");
                paramsValue.add(this.paramesMap.get(name));
            } else {
                throw new IllegalArgumentException("未知参数 '" + name);
            }
        }
        stringBuilder.append(sql.substring(index));
        return new SQLAndParams(stringBuilder.toString(), paramsValue);
    }

    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
        SQLAndParams sqlAndParams = createSqlWithParams();

        PreparedStatement stmt = conn.prepareStatement(sqlAndParams.getSql());
        for (int i = 0; i < sqlAndParams.getParams().size(); ++i) {
            stmt.setObject(i + 1, sqlAndParams.getParams().get(i));
        }
        return stmt;
    }


    public ParameterizedPreparedStatmentCreator setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public ParameterizedPreparedStatmentCreator setParams(String key, Object value) {
        if (NAME_PATTERN.matcher(key).matches()) {
            this.paramesMap.put(key, value);
        } else {
            throw new IllegalArgumentException(key + " 并不是一个合法的参数");
        }
        return this;
    }

    public String getSql() {
        return this.sql;
    }

    public Map<String, Object> getParamesMap() {
        return Collections.unmodifiableMap(this.paramesMap);
    }

}
