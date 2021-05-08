/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.db.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.skyeye.eve.dao", "com.skyeye.dao"}, sqlSessionFactoryRef = "baseSqlSessionFactory")
public class BaseDataSourceConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDataSourceConfig.class);

	@Bean(name="baseDataSource")
	@Primary// 必须加此注解，不然报错，下一个类则不需要添加，表示这个数据源是默认数据源
	@ConfigurationProperties(prefix = "spring.datasource.basedata") // prefix值必须是application.properteis中对应属性的前缀
	public DataSource baseDataSource(){
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "baseSqlSessionFactory")
	public SqlSessionFactory baseSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setMapperLocations(resolver.getResources("classpath*:mapper/**/*.xml"));
			bean.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
			return bean.getObject();
		} catch (Exception ee) {
			LOGGER.info("load db failed. ", ee);
			throw new RuntimeException(ee);
		}
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManagerOne(@Qualifier("baseDataSource") DataSource dataSourceOne) {
		return new DataSourceTransactionManager(dataSourceOne);
	}

	@Primary
	public SqlSessionTemplate sqlsessionTemplateOne(@Qualifier("sqlsessionTemplateOne") SqlSessionFactory sqlSessionFactory) throws Exception {
		// 使用上面配置的Factory
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
