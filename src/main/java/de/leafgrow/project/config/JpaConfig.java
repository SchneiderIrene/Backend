package de.leafgrow.project.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "de.leafgrow.project.repository")
@EntityScan(basePackages = "de.leafgrow.project.domain.entity")
@EnableTransactionManagement
public class JpaConfig {
}
