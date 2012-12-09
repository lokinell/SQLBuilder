package com.rollen.sqlbuilder;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InsertBuilderTest {
    @Test
    public void insertBuildTest(){
        InsertBuilder builder;
        builder = new InsertBuilder("Person");
        assertEquals("Insert INTO Person () VALUES ()",builder.toString());

        builder.set("name", "rollen");
        assertEquals("Insert INTO Person (name) VALUES (rollen)",builder.toString());

        builder.set("age", "12");
        assertEquals("Insert INTO Person (name, age) VALUES (rollen, 12)", builder.toString());
    }

}
