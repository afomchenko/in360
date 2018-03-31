/*
 * This file is part of in360TourBuilder.
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 * 24.02.18 22:35 Anton Fomchenko 360@in360.ru
 */

package ru.in360.db.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import javax.validation.Validator;
import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

@Configuration
@EnableJpaRepositories(basePackages = "ru.in360.db.repo")
@EnableTransactionManagement
@PropertySources({@PropertySource("classpath:/jdbc.properties")})
public class PersistenceConfig {

    private final Environment env;

    @Autowired
    public PersistenceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("jdbc.minPoolSize")));
        dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("jdbc.maxPoolSize")));
        dataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("jdbc.maxIdleTime")));
        dataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getProperty("jdbc.idleConnectionTestPeriod")));
        try {
            dataSource.setDriverClass(env.getProperty("jdbc.driverClassName"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/liquibase.changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @Bean
    @DependsOn("liquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(Properties hibernateProps, DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.in360.db.model", "ru.in360.db.model.security");
        factory.setDataSource(dataSource);
        factory.setJpaProperties(hibernateProps);
        factory.setValidationMode(ValidationMode.NONE);
        return factory;
    }

    @Bean
    public Properties hibernateProps() {
        return getNonNullProperties(env::getProperty,
                "hibernate.dialect",
                "hibernate.hbm2ddl.auto",
                "hibernate.show_sql",
                "hibernate.format_sql",
                "hibernate.connection.CharSet",
                "hibernate.character_encoding",
                "hibernate.useunicode",
                "hibernate.default_schema",
                "hibernate.id.new_generator_mappings",
                "hibernate.batch_size",
                "hibernate.order_inserts",
                "hibernate.order_updates",
                "hibernate.jdbc.batch_versioned_data");
    }

    private Properties getNonNullProperties(Function<String, String> fetcher, String... propertyNames) {
        return Stream.of(propertyNames).collect(
                Properties::new,
                (props, propertyName) -> {
                    Optional<String> propertyValue = Optional.ofNullable(fetcher.apply(propertyName));
                    propertyValue.ifPresent(value -> props.setProperty(propertyName, value));
                },
                Map::putAll);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    public Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
}