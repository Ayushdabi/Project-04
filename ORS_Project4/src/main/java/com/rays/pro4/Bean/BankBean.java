package com.rays.pro4.Bean;

import java.util.Date;

import com.rays.pro4.Util.DataUtility;

public class BankBean extends BaseBean {

	private String Bank_Name;
	private String Account_NO;
	private String Customer_Name;
	private Date Dob;
	private String Address;

	public String getBank_Name() {
		return Bank_Name;
	}

	public void setBank_Name(String bank_Name) {
		Bank_Name = bank_Name;
	}

	public String getAccount_NO() {
		return Account_NO;
	}

	public void setAccount_NO(String account_NO) {
		Account_NO = account_NO;
	}

	public String getCustomer_Name() {
		return Customer_Name;
	}

	public void setCustomer_Name(String customer_Name) {
		Customer_Name = customer_Name;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Address;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Address;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
