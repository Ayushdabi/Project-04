package com.rays.pro4.Bean;

import java.util.Date;

public class CarBean  extends BaseBean{
	private String OwnerName;
	private String CarName;
	private String CarPrice;
	private Date Dob;

	



	public String getOwnerName() {
		return OwnerName;
	}

	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}

	public String getCarName() {
		return CarName;
	}

	public void setCarName(String carName) {
		CarName = carName;
	}

	public String getCarPrice() {
		return CarPrice;
	}

	public void setCarPrice(String carPrice) {
		CarPrice = carPrice;
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
		return id+" ";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Dob+"";
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
