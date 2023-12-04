package com.openpay.marvelservice.config;

import com.openpay.marvelservice.audit.AuditAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
@Profile("!test")
public class AuditConfiguration {


    @Bean
    public AuditAspect auditAspect(JdbcClient jdbcClient) {
        return new AuditAspect(jdbcClient);
    }
}
