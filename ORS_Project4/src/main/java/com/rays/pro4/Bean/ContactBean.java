package com.rays.pro4.Bean;

import java.util.Date;

public class ContactBean extends BaseBean{
	
	 private String Name;
	 private String City;
	 private Date Dob;
	 private String Mobile;
	 

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
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

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return City;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return City;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
