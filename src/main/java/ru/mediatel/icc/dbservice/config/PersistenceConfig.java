package ru.mediatel.icc.dbservice.config;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import ru.mediatel.icc.dbservice.common.JooqLoggingListener;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    DataSource dataSource() {
        return new HikariDataSource();
    }

    @Bean
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    ConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource()));
    }

    @Bean
    Settings jooqSettings() {
        Settings settings = new Settings();
        settings.setRenderSchema(true);
        settings.setExecuteWithOptimisticLocking(true);
        settings.withExecuteWithOptimisticLockingExcludeUnversioned(true);
        settings.setRenderNameCase(RenderNameCase.LOWER);
        return settings;
    }

    @Bean
    public DefaultDSLContext context(DataSource dataSource) {
        DefaultConfiguration config = (DefaultConfiguration) new DefaultConfiguration()
                .set(dataSource)
                .set(SQLDialect.POSTGRES)
                .set(jooqSettings())
                .set(connectionProvider())
                .set(new DefaultExecuteListenerProvider(new JooqLoggingListener()));

        return new DefaultDSLContext(config);
    }
}
