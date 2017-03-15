package com.ag04.feeddit.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.ag04.feeddit.domain"})
@EnableJpaRepositories(basePackages = {"com.ag04.feeddit.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}