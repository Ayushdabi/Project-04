package com.rays.pro4.Bean;

import java.util.Date;

public class LeadBean  extends BaseBean{
	private Date Dob;
	 private String ContactName;
	 private String Mobile;
	 private String Status;
	
	 public Date getDob() {
		return Dob;
	}
	public void setDob(Date dob) {
		Dob = dob;
	}
	public String getContactName() {
		return ContactName;
	}
	public void setContactName(String contactName) {
		ContactName = contactName;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
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
		return Status;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Status;
	}
	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
