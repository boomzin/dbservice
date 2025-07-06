package ru.mediatel.icc.dbservice.common;

import lombok.extern.slf4j.Slf4j;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;


@Slf4j
public class JooqLoggingListener implements ExecuteListener {
    @Override
    public void executeStart(ExecuteContext ctx) {
        log.info("Executing query: {}", ctx.query());
    }
}
