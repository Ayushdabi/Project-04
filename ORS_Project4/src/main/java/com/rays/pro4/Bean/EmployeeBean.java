package com.rays.pro4.Bean;

import java.util.Date;

public class EmployeeBean extends BaseBean {
	
	 private String EmployeeName;
	 private String Department;
	 private Date Dob;
	 private String LastName;

	 
	
	public String getEmployeeName() {
		return EmployeeName;
	}

	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Department;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Department;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}
	 

}
