package com.rollen.sqlbuilder;

import java.util.List;

public abstract class AbstractSqlBuilder {

    protected void appendList(StringBuilder stringBuilder, List<String> list, String init, String separator) {
        boolean flag = true;
        for (String item : list) {
            if (flag) {
                stringBuilder.append(init);
            } else {
                stringBuilder.append(separator);
            }
            flag = false;
            stringBuilder.append(item);
        }
    }
}
