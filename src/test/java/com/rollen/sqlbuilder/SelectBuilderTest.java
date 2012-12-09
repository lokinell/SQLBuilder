package com.rollen.sqlbuilder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SelectBuilderTest {

    @Test
    public void selectBuilderTest() {
        SelectBuilder selectBuilder = new SelectBuilder("Employee");
        assertEquals("SELECT * FROM Employee", selectBuilder.toString());

        selectBuilder.column("userName as name");
        assertEquals("SELECT userName as name FROM Employee", selectBuilder.toString());

        selectBuilder.column("age", true);
        assertEquals("SELECT userName as name, age FROM Employee GROUP BY age", selectBuilder.toString());

        selectBuilder = new SelectBuilder("Employee e").where("name like 'Bob%'");
        assertEquals("SELECT * FROM Employee e WHERE name like 'Bob%'", selectBuilder.toString());

        selectBuilder = new SelectBuilder("Employee e").where("name like 'Bob%'").where("age > 37");
        assertEquals("SELECT * FROM Employee e WHERE name like 'Bob%' AND age > 37", selectBuilder.toString());

        selectBuilder = new SelectBuilder("Employee e").join("Department d on e.dept_id = d.id");
        assertEquals("SELECT * FROM Employee e JOIN Department d on e.dept_id = d.id", selectBuilder.toString());

        selectBuilder = new SelectBuilder("Employee e").join("Department d on e.dept_id = d.id").where("name like 'Bob%'");
        assertEquals("SELECT * FROM Employee e JOIN Department d on e.dept_id = d.id WHERE name like 'Bob%'", selectBuilder.toString());

        selectBuilder = new SelectBuilder("Employee e").orderBy("name");
        assertEquals("SELECT * FROM Employee e ORDER BY name", selectBuilder.toString());

        selectBuilder = new SelectBuilder("Employee e").orderBy("name desc").orderBy("age");
        assertEquals("SELECT * FROM Employee e ORDER BY name desc, age", selectBuilder.toString());

        selectBuilder = new SelectBuilder("Employee").where("name like 'Bob%'").orderBy("age");
        assertEquals("SELECT * FROM Employee WHERE name like 'Bob%' ORDER BY age", selectBuilder.toString());

        selectBuilder = new SelectBuilder("Employee").where("id = 42").forUpdate();
        assertEquals("SELECT * FROM Employee WHERE id = 42 FOR UPDATE", selectBuilder.toString());

    }
}
