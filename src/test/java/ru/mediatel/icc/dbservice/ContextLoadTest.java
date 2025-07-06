package ru.mediatel.icc.dbservice;

import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ContextLoadTest {

    @Autowired
    DSLContext dsl;

    @Test
    void contextLoads() {
        assertNotNull(dsl);
    }
}