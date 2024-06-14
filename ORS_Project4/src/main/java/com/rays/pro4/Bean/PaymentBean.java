package com.rays.pro4.Bean;

import java.util.Date;

import com.rays.pro4.Util.DataUtility;



public class PaymentBean extends BaseBean {

	
	private String A_Name;
	private String account;
	private String Upi_id;
	private String Total_Amount;
	private Date   Dob;
	
		public String getUpi_id() {
		return Upi_id;
	}
	public void setUpi_id(String upi_id) {
		Upi_id = upi_id;
	}
	public String getTotal_Amount() {
		return Total_Amount;
	}
	public void setTotal_Amount(String total_Amount) {
		Total_Amount = total_Amount;
	}
	public Date getDob() {
		return Dob;
	}
	public void setDob(Date dob) {
		Dob = dob;
	}
		@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Upi_id;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return  Upi_id;
	}
	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getA_Name() {
		return A_Name;
	}
	public void setA_Name(String a_Name) {
		A_Name = a_Name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

}