package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private Infrastructure infrastructure;
	
	@Bean
	public Job partitionerJob(){
		return jobBuilderFactory.get("partitionerJob")
				.listener(protocolListener())
				.start(step())
				.build();
	}
	
	@Bean
	public Step step(){
		return stepBuilderFactory.get("step")
				.<EmployeeDTO,EmployeeDTO>chunk(1)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.taskExecutor(infrastructure.taskExecutor())
				.build();
	}
	
	@Bean
	public ItemReader<EmployeeDTO> reader(){
		System.out.println("inside reader");
		JdbcCursorItemReader<EmployeeDTO> itemReader = new JdbcCursorItemReader<>();
		itemReader.setDataSource(infrastructure.dataSource());
		itemReader.setSql("SELECT * FROM EMPLOYEE");
		itemReader.setRowMapper(new RowMapper<EmployeeDTO>() {
			@Override
			public EmployeeDTO mapRow(ResultSet rs, int arg1) throws SQLException {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setCompany(rs.getString("COMPANY"));
				dto.setLocation(rs.getString("LOCATION"));
				dto.setName(rs.getString("NAME"));
				dto.setSalary(rs.getString("SALARY"));
				return dto;
			}
		});
		return itemReader;
	}
	
	@Bean
	public ItemProcessor<EmployeeDTO,EmployeeDTO> processor(){
		System.out.println("inside processor");
		return new EmployeeProcessor();
	}
	
	@Bean
	public ItemWriter<EmployeeDTO> writer(){
		System.out.println("inside writer");
		JdbcBatchItemWriter<EmployeeDTO> batchItemWriter = new JdbcBatchItemWriter<>();
		batchItemWriter.setDataSource(infrastructure.dataSource());
		batchItemWriter.setSql("INSERT INTO employee_target (`NAME`, `SALARY`, `LOCATION`, `COMPANY`) VALUES(:name, :salary, :location, :company)");
		batchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EmployeeDTO>());
		return batchItemWriter;
	}
	
	@Bean
	public ProtocolListener protocolListener(){
		return new ProtocolListener();
	}
	
}
