package com.gaurav.springcloudproject.configuration;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "blogdbEntityManagerFactory",
        basePackages = {
                "com.gaurav.springcloudproject.databases.blogdb"
        },
        transactionManagerRef = "blogdbTransactionManager"
)
public class BlogDbConfig {

    @Bean(name = "blogdbDataSourceProperties")
    @ConfigurationProperties("blogdb.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "blogdbDataSource")
    @ConfigurationProperties("blogdb.datasource.configuration")
    public DataSource dataSource(@Qualifier("blogdbDataSourceProperties") DataSourceProperties blogdbDataSourceProperties) {
        return blogdbDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "blogdbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("blogdbDataSource") DataSource blogdbDataSource) {
        return builder
                .dataSource(blogdbDataSource)
                .packages("com.gaurav.springcloudproject.databases.blogdb")
                .persistenceUnit("blogdb")
                .build();
    }

    @Bean(name = "blogdbTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("blogdbEntityManagerFactory") EntityManagerFactory db1EntityManagerFactory) {
        return new JpaTransactionManager(db1EntityManagerFactory);
    }

    }
