package com.example.demo;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;

public interface Infrastructure {
	@Bean
	public DataSource dataSource();
	
	@Bean
	public TaskExecutor taskExecutor();
}
