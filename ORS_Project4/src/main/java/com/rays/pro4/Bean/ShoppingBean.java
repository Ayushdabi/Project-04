package com.rays.pro4.Bean;

import java.util.Date;

public class ShoppingBean  extends BaseBean{
	private String ProductName;
	private String ShopName;
	private String ProductPrice;
	private Date dob;

	


	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getShopName() {
		return ShopName;
	}

	public void setShopName(String shopName) {
		ShopName = shopName;
	}

	public String getProductPrice() {
		return ProductPrice;
	}

	public void setProductPrice(String productPrice) {
		ProductPrice = productPrice;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return ProductPrice;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return ProductPrice;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
