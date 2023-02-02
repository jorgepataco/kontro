package com.kontro.datasources.services.parametros;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.EmptyInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kontro.enums.DataSourceEnum;
import com.kontro.repositories.parametros.ParametroRepository;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableJpaRepositories(basePackageClasses = ParametroRepository.class, 
transactionManagerRef = "parametrosTranscationManager", 
entityManagerFactoryRef = "parametrosEntityManager")
@EnableTransactionManagement
@RequiredArgsConstructor
//@DependsOn("dataSourceRouting")
public class DataSourceConfigParametros {

	private final String PACKAGE_SCAN = "com.kontro.entities.parametros";
	
    @Bean(name = "paramUverHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.uvercenter.hml")
    public DataSourceProperties paramUverHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "paramUverHmlDataSource")
    @ConfigurationProperties("spring.datasource.uvercenter.hml.configuration")
    public DataSource paramUverHmlDataSource(@Qualifier("paramUverHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "paramFibraHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.fibra.hml")
    public DataSourceProperties paramFibraHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "paramFibraHmlDataSource")
    @ConfigurationProperties("spring.datasource.fibra.hml.configuration")
    public DataSource paramFibraHmlDataSource(@Qualifier("paramFibraHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "paramRacoHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.raco.hml")
    public DataSourceProperties paramRacoHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "paramRacoHmlDataSource")
    @ConfigurationProperties("spring.datasource.raco.hml.configuration")
    public DataSource paramRacoHmlDataSource(@Qualifier("paramRacoHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "tvHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.tv.hml")
    public DataSourceProperties paramTvHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "tvHmlDataSource")
    @ConfigurationProperties("spring.datasource.tv.hml.configuration")
    public DataSource paramTvHmlDataSource(@Qualifier("tvHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "oitotalHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.oitotal.hml")
    public DataSourceProperties paramOitotalHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "oitotalHmlDataSource")
    @ConfigurationProperties("spring.datasource.oitotal.hml.configuration")
    public DataSource paramOitotalHmlDataSource(@Qualifier("oitotalHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "empHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.emp.hml")
    public DataSourceProperties empHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "empHmlDataSource")
    @ConfigurationProperties("spring.datasource.emp.hml.configuration")
    public DataSource empHmlDataSource(@Qualifier("empHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "fixaHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.fixa.hml")
    public DataSourceProperties fixaHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "fixaHmlDataSource")
    @ConfigurationProperties("spring.datasource.fixa.hml.configuration")
    public DataSource fixaHmlDataSource(@Qualifier("fixaHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "102HmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.102.hml")
    public DataSourceProperties param102HmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "102HmlDataSource")
    @ConfigurationProperties("spring.datasource.102.hml.configuration")
    public DataSource param102HmlDataSource(@Qualifier("102HmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "posHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.pos.hml")
    public DataSourceProperties posHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "posHmlDataSource")
    @ConfigurationProperties("spring.datasource.pos.hml.configuration")
    public DataSource posHmlDataSource(@Qualifier("posHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "shortcodeHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.shortcode.hml")
    public DataSourceProperties shortcodeHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "shortcodeHmlDataSource")
    @ConfigurationProperties("spring.datasource.shortcode.hml.configuration")
    public DataSource shortcodeHmlDataSource(@Qualifier("shortcodeHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "cadastroHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.cadastro.hml")
    public DataSourceProperties cadastroHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "cadastroHmlDataSource")
    @ConfigurationProperties("spring.datasource.cadastro.hml.configuration")
    public DataSource cadastroHmlDataSource(@Qualifier("cadastroHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "672HmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.672.hml")
    public DataSourceProperties param672HmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "672HmlDataSource")
    @ConfigurationProperties("spring.datasource.672.hml.configuration")
    public DataSource param672HmlDataSource(@Qualifier("672HmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "ouvidoriaHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.ouvidoria.hml")
    public DataSourceProperties ouvidoriaHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "ouvidoriaHmlDataSource")
    @ConfigurationProperties("spring.datasource.ouvidoria.hml.configuration")
    public DataSource ouvidoriaHmlDataSource(@Qualifier("ouvidoriaHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "padigitalHmlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.padigital.hml")
    public DataSourceProperties padigitalHmlDataSourceProperties() {
        return new DataSourceProperties();
    }
	
    @Bean(name = "padigitalHmlDataSource")
    @ConfigurationProperties("spring.datasource.padigital.hml.configuration")
    public DataSource padigitalHmlDataSource(@Qualifier("padigitalHmlDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
		return primaryDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
	@Bean(name = "parametrosRoutingDataSource")
    public DataSource multiRoutingDataSource(
    		@Qualifier("paramUverHmlDataSource") DataSource paramUverHmlDataSource,
    		@Qualifier("paramFibraHmlDataSource") DataSource paramFibraHmlDataSource,
    		@Qualifier("paramRacoHmlDataSource") DataSource paramRacoHmlDataSource,
    		@Qualifier("tvHmlDataSource") DataSource tvHmlDataSource,
    		@Qualifier("oitotalHmlDataSource") DataSource oitotalHmlDataSource,
    		@Qualifier("empHmlDataSource") DataSource empHmlDataSource,
    		@Qualifier("fixaHmlDataSource") DataSource fixaHmlDataSource,
    		@Qualifier("102HmlDataSource") DataSource param102HmlDataSource,
    		@Qualifier("posHmlDataSource") DataSource posHmlDataSource,
    		@Qualifier("shortcodeHmlDataSource") DataSource shortcodeHmlDataSource,
    		@Qualifier("cadastroHmlDataSource") DataSource cadastroHmlDataSource,
    		@Qualifier("672HmlDataSource") DataSource param672HmlDataSource,
    		@Qualifier("ouvidoriaHmlDataSource") DataSource ouvidoriaHmlDataSource,
    		@Qualifier("padigitalHmlDataSource") DataSource padigitalHmlDataSource) {
		
        Map<Object, Object> targetDataSources = new HashMap<>();
        
        targetDataSources.put(DataSourceEnum.hml_fibra, paramFibraHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_uvercenter, paramUverHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_tv, tvHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_oitotal, oitotalHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_emp, empHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_fixa, fixaHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_102, param102HmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_pos, posHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_shortcode, shortcodeHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_cadastro, cadastroHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_672, param672HmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_ouvidoria, ouvidoriaHmlDataSource);
        targetDataSources.put(DataSourceEnum.hml_padigital, padigitalHmlDataSource);
        
        
        MultiRoutingDataSourceParametros multiRoutingDataSource = new MultiRoutingDataSourceParametros();
        multiRoutingDataSource.setDefaultTargetDataSource(paramUverHmlDataSource);
        multiRoutingDataSource.setTargetDataSources(targetDataSources);
        return multiRoutingDataSource;
        
    }
	
	@Bean(name = "parametrosEntityManager")
    public LocalContainerEntityManagerFactoryBean multiEntityManager(@Qualifier("parametrosRoutingDataSource") DataSource parametrosRoutingDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(parametrosRoutingDataSource);
        em.setPackagesToScan(PACKAGE_SCAN);
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }
	
	@Bean(name = "parametrosTranscationManager")
    public PlatformTransactionManager multiTransactionManager(@Qualifier("parametrosEntityManager") LocalContainerEntityManagerFactoryBean bean) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
        		bean.getObject());
        return transactionManager;
    }
	
//    @Bean(name = "dbSessionFactory")
//    public LocalSessionFactoryBean dbSessionFactory() {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(multiRoutingDataSource());
//        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
//        sessionFactoryBean.setHibernateProperties(hibernateProperties());
//        return sessionFactoryBean;
//    }
	
	private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        properties.put("hibernate.ejb.interceptor", new InterceptorTableNameParametros());
        return properties;
    }
	
//	@Bean
//	public EmptyInterceptor hibernateInterceptor() {
//	    return new EmptyInterceptor() {
//	    	/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//	    	public String onPrepareStatement(String sql) {
//	    		System.out.println("SQL: " + sql);
//	    		return sql;
//	    	}
//	    };
//	}
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
