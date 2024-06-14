package com.rays.pro4.Bean;

import java.util.Date;

public class FollowUpBean  extends BaseBean{
private String Patient;
private String Doctor;
 private Date Dob;
  private String Fess;
  
	
	
	public String getPatient() {
	return Patient;
}

public void setPatient(String patient) {
	Patient = patient;
}

public String getDoctor() {
	return Doctor;
}

public void setDoctor(String doctor) {
	Doctor = doctor;
}

public Date getDob() {
	return Dob;
}

public void setDob(Date dob) {
	Dob = dob;
}

public String getFess() {
	return Fess;
}

public void setFess(String fess) {
	Fess = fess;
}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Patient;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Patient;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
