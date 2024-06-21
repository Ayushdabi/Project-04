package com.rays.pro4.Bean;

import java.util.Date;

public class InventoryBean extends BaseBean {
	private String SupplierName;
	private Date Dob;
	 private String Quantity;
		private String  Product;

	 
	 public String getSupplierName() {
		return SupplierName;
	}

	public void setSupplierName(String supplierName) {
		SupplierName = supplierName;
	}

	public Date getDob() {
		return Dob;
	}

	public void setDob(Date dob) {
		Dob = dob;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}


	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return Product;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return Product;
	};

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
