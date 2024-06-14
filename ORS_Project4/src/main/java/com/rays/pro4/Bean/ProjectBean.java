package com.rays.pro4.Bean;

import java.util.Date;

public class ProjectBean  extends BaseBean{
	
	private String Name;
	private String Category;
	private Date Dob;
	private String Version;
	
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Category;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Category;
	}
	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public Date getDob() {
		return Dob;
	}
	public void setDob(Date dob) {
		Dob = dob;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	 
	

	

}
