package com.rays.pro4.Bean;

import java.util.Date;

public class JobBean extends BaseBean{
	 private String Title; 
	 private   Date Dob;
	 private String Experience;
	 private String Status; 

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getExperience() {
		return Experience;
	}

	public void setExperience(String experience) {
		Experience = experience;
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
