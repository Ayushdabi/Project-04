package com.rays.pro4.Bean;

import java.util.Date;

public class DoctarBean  extends BaseBean{
	
	private String Name;
	private Date Dob;
	private String Mobile;
	private String Experties;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getExperties() {
		return Experties;
	}

	public void setExperties(String experties) {
		Experties = experties;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Experties;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Experties;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
