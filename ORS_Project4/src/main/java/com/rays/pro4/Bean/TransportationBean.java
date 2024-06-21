package com.rays.pro4.Bean;

import java.util.Date;

public class TransportationBean extends BaseBean {

	private String Description;
	private String Mode;
	private Date Dob;
	private String Cost;

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMode() {
		return Mode;
	}

	public void setMode(String mode) {
		Mode = mode;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getCost() {
		return Cost;
	}

	public void setCost(String cost) {
		Cost = cost;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Mode;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Mode;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
