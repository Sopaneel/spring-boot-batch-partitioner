package com.example.demo;

import java.io.Serializable;

public class EmployeeDTO implements Serializable
{
/**
	 * 
	 */
	private static final long serialVersionUID = -1243288720511063418L;
	
private String name;
private String salary;
private String location;
private String company;

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSalary() {
	return salary;
}
public void setSalary(String salary) {
	this.salary = salary;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getCompany() {
	return company;
}
public void setCompany(String company) {
	this.company = company;
}
@Override
public String toString() {
	return "EmployeeDTO [name=" + name + ", salary=" + salary + ", location=" + location + ", company=" + company + "]";
}

}
