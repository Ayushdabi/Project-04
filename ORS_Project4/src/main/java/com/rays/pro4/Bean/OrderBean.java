
package com.rays.pro4.Bean;

import java.util.Date;

public class OrderBean extends BaseBean {
	private String ProductName;
	private Date Dob;
	private Long Quantity;
	private String Customer;

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Customer;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Customer;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public Long getQuantity() {
		return Quantity;
	}

	public void setQuantity(Long quantity) {
		Quantity = quantity;
	}

	public String getCustomer() {
		return Customer;
	}

	public void setCustomer(String customer) {
		Customer = customer;
	}

}
