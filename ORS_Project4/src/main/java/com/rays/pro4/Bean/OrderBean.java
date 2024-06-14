package com.rays.pro4.Bean;

import java.util.Date;

public class OrderBean extends BaseBean {
	private String Order_Name;
	private String Order_Status;
	private String Order_Price;
	private Date Dob;

	public String getOrder_Name() {
		return Order_Name;
	}

	public void setOrder_Name(String order_Name) {
		Order_Name = order_Name;
	}

	public String getOrder_Status() {
		return Order_Status;
	}

	public void setOrder_Status(String order_Status) {
		Order_Status = order_Status;
	}

	public String getOrder_Price() {
		return Order_Price;
	}

	public void setOrder_Price(String order_Price) {
		Order_Price = order_Price;
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
		return Order_Price;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Order_Price;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
