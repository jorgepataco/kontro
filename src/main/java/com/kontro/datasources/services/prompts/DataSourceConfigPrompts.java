package com.kontro.datasources.services.prompts;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kontro.repositories.prompts.CategoriaPromptRepository;
import com.kontro.repositories.prompts.PromptRepository;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableJpaRepositories(basePackageClasses = 
{CategoriaPromptRepository.class, PromptRepository.class}, 
transactionManagerRef = "promptsTranscationManager", 
entityManagerFactoryRef = "promptsEntityManager")
@EnableTransactionManagement
@RequiredArgsConstructor
//@DependsOn("dataSourceRouting")
public class DataSourceConfigPrompts {

	private final String PACKAGE_SCAN = "com.kontro.entities.prompts";
	
	@Primary
    @Bean(name = "promptsDataSourceProperties")
    @ConfigurationProperties("spring.datasource.prompts")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }
	
	@Primary
    @Bean(name = "promptsDataSource")
    @ConfigurationProperties("spring.datasource.prompts.configuration")
    public DataSource promptsDataSource(@Qualifier("promptsDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
	
	@Primary
	@Bean(name = "promptsEntityManager")
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder primaryEntityManagerFactoryBuilder, @Qualifier("promptsDataSource") DataSource primaryDataSource) {

        Map<String, String> primaryJpaProperties = new HashMap<>();
        primaryJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        primaryJpaProperties.put("hibernate.hbm2ddl.auto", "none");

        return primaryEntityManagerFactoryBuilder
                .dataSource(primaryDataSource)
                .packages(PACKAGE_SCAN)
                .persistenceUnit("primaryDataSource")
                .properties(primaryJpaProperties)
                .build();
    }
	
	@Primary
	@Bean(name = "promptsTranscationManager")
	public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("promptsEntityManager") EntityManagerFactory primaryEntityManagerFactory) {

        return new JpaTransactionManager(primaryEntityManagerFactory);
    }
	
//	private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.show_sql", true);
//        properties.put("hibernate.format_sql", true);
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
//        return properties;
//    }
//	@Autowired
//	private DataSourceRouting dataSourceRouting;
	
//	@Bean
//	@Primary
//	public DataSource dataSource() {
//		System.out.println("Passo 1");
//		return dataSourceRouting;
//	}

//	@Bean(name = "FirstEntityManager")
//	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
//		System.out.println("Passo 6");
////		Map<String, String> primaryJpaProperties = new HashMap<>();
////        primaryJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        //primaryJpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
//        //DataSource dataSource = dataSource();
//		return builder.dataSource(dataSource()).packages(Parametro.class).build();
//	}
//
//	@Bean(name = "FirstTranscationManager")
//	public JpaTransactionManager transactionManager(
//			@Autowired @Qualifier("FirstEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
//		System.out.println("Passo 7");
//		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
//	}
	
}
