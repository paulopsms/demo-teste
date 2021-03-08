package br.com.atlantico.demoteste.configuration.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(entityManagerFactoryRef = "demotesteEntityManagerFactory",
        transactionManagerRef = "demotesteTM",
        basePackages = {"br.com.atlantico.demoteste.repository"})
public class DemoTesteDatasourceConfiguration {

    private static final String ORG_POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final String ATLANTICODB = "jdbc:postgresql://localhost/atlanticodb";
    private static final String ATLANTICO = "atlantico";

    @Primary
    @Bean(name = "demotesteDS")
    public DataSource omniaDatasource() {
        return DataSourceBuilder.create()
                .driverClassName(ORG_POSTGRESQL_DRIVER)
                .url(ATLANTICODB)
                .username(ATLANTICO)
                .password(DemoTesteDatasourceConfiguration.ATLANTICO)
                .build();
    }

    @Primary
    @Bean(name = "demotesteEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("demotesteDS") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("br.com.atlantico.demoteste")
                .persistenceUnit("demotestePU")
                .build();
    }

    @Primary
    @Bean(name = "demotesteTM")
    public PlatformTransactionManager pgsqlPlatformTransactionManager(@Qualifier("demotesteEntityManagerFactory") EntityManagerFactory pgsqlEntityManagerFactory) {
        return new JpaTransactionManager(pgsqlEntityManagerFactory);
    }
}
