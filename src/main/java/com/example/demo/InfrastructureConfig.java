package com.example.demo;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class InfrastructureConfig implements Infrastructure {

	/*@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	private DataSourceProperties properties() {
		return new DataSourceProperties();
	}*/
	
	@Bean
//	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder
				.create()
				.url("jdbc:mysql://localhost:3307/sopaneel_db")
				.username("root")
				.password("admin")
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.build();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		return taskExecutor;
	}

}
