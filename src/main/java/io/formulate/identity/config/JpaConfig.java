package io.formulate.identity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
// Unnecessary in Spring Boot applications
@EnableTransactionManagement
public class JpaConfig {}
