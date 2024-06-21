package com.rays.pro4.Bean;

import java.util.Date;

public class SupplierBean extends BaseBean {

	private String Name;
	private String Category;
	private Date Dob;
	private Integer PaymentTerm;

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

	public Integer getPaymentTerm() {
		return PaymentTerm;
	}

	public void setPaymentTerm(Integer paymentTerm) {
		PaymentTerm = paymentTerm;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return null;
	};

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
