package com.example.demo;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, EmployeeDTO> {

	@Override
	public EmployeeDTO process(EmployeeDTO arg0) throws Exception {
		return arg0;
	}

}
