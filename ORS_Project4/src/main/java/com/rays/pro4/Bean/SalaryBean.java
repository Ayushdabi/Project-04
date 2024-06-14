package com.rays.pro4.Bean;

import java.util.Date;

public class SalaryBean extends BaseBean {
	
	private String Employee;
	private String Amount;
	private Date Dob;
	private String Status;




	public String getEmployee() {
		return Employee;
	}

	public void setEmployee(String employee) {
		Employee = employee;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		 Status = status;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Amount;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Amount;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
