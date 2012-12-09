package com.rollen.sqlbuilder;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class DeleteBuilderTest {

    @Test
    public void testAll(){
        DeleteBuilder deleteBuilder=new DeleteBuilder("person");
        assertEquals("DELETE FROM person",deleteBuilder.toString());

        deleteBuilder.set("name = rollen");
        assertEquals("DELETE FROM person WHERE name = rollen",deleteBuilder.toString());

        deleteBuilder.set("age = 12");
        assertEquals("DELETE FROM person WHERE name = rollen AND age = 12",deleteBuilder.toString());
    }
}
