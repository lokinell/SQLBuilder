package com.rollen.sqlbuilder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateBuilderTest {
    @Test
    public void updateBuilderTest() {
        UpdateBuilder updateBuilder = new UpdateBuilder("Person");
        assertEquals("UPDATE Person", updateBuilder.toString());

        updateBuilder.set("name = rollen");
        assertEquals("UPDATE Person SET name = rollen", updateBuilder.toString());

        updateBuilder.set("age = 20");
        assertEquals("UPDATE Person SET name = rollen, age = 20", updateBuilder.toString());

        updateBuilder.wheres("name = rollen");
        assertEquals("UPDATE Person SET name = rollen, age = 20 WHERE name = rollen", updateBuilder.toString());

        updateBuilder.wheres("age = 12");
        assertEquals("UPDATE Person SET name = rollen, age = 20 WHERE name = rollen AND age = 12", updateBuilder.toString());

    }
}
