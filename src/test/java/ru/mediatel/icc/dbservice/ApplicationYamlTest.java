package ru.mediatel.icc.dbservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationYamlTest {
    @Value("${spring.datasource.url}")
    String jdbcUrl;

    @Test
    void shouldLoadJdbcUrl() {
        System.out.println("JDBC URL: " + jdbcUrl);
        assertNotNull(jdbcUrl);
    }
}
