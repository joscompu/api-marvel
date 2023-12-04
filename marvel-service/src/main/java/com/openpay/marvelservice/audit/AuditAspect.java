package com.openpay.marvelservice.audit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Aspect
public class AuditAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);
    private final JdbcClient jdbcClient;

    public AuditAspect(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Around("@annotation(ExecutionLog)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("@beforeExecution");
        var currentTime = Instant.now();

        var result = joinPoint.proceed();
        registerAudit(joinPoint, currentTime, Instant.now());

        return result;
    }

    private void registerAudit(ProceedingJoinPoint joinPoint,
                               Instant startTime,
                               Instant endTime) {
        try {
            boolean inserted = jdbcClient.sql("""
                                INSERT INTO audit_log (method,duration_in_millis,register_date)
                                VALUES(:method,:durationInMillis,:registerDate)
                            """)
                    .param("method", joinPoint.getSignature().getName())
                    .param("durationInMillis", ChronoUnit.MILLIS.between(startTime, endTime))
                    .param("registerDate", LocalDateTime.now())
                    .update() > 0;

            if (inserted) {
                LOGGER.info("AuditLog registered");
            }
        } catch (Exception ex) {
            LOGGER.error("Failed to register auditLog");
        }
    }
}
