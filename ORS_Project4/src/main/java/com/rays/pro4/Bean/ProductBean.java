package com.rays.pro4.Bean;

import java.util.Date;

public class ProductBean extends BaseBean {
	private String ProductName;
	private String ProductAmmount;
	private String ProductStatus;
	private Date PurchaseDate;

	public Date getPurchaseDate() {
		return PurchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		PurchaseDate = purchaseDate;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

	public String getProductAmmount() {
		return ProductAmmount;
	}

	public void setProductAmmount(String productAmmount) {
		ProductAmmount = productAmmount;
	}

	public String getProductStatus() {
		return ProductStatus;
	}

	public void setProductStatus(String productStatus) {
		ProductStatus = productStatus;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+" ";
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return ProductAmmount;
	}

	@Override
	public int compareTo(BaseBean o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
