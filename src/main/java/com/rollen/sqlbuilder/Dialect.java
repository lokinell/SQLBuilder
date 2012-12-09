package com.rollen.sqlbuilder;

public interface Dialect {

    public String createCountSelect(String sql);

    public String createPageSelect(String sql, int limit, int offset);
}