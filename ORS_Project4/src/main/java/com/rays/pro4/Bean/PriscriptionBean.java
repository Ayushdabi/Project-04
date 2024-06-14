package com.rays.pro4.Bean;

import java.util.Date;

public class PriscriptionBean extends BaseBean{
	
	private String Name;
	private String Decease;
	private Date Dob;
	 private String capicity;
	 

	public String getCapicity() {
		return capicity;
	}

	public void setCapicity(String capicity) {
		this.capicity = capicity;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	

	public String getDecease() {
		return Decease;
	}

	public void setDecease(String decease) {
		Decease = decease;
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
		return capicity;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return capicity;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
