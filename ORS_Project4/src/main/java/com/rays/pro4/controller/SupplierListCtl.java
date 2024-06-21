package com.rays.pro4.controller;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SupplierBean;
import com.rays.pro4.Model.SupplierModel;
import com.rays.pro4.Util.DataUtility;

@WebServlet(name = "SupplierListCtl", urlPatterns = { "/ctl/SupplierListCtl" })

public class SupplierListCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {
		SupplierModel model = new SupplierModel();
		try {
			List rlist = model.list();
			request.setAttribute("prolist", rlist);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		SupplierBean bean = new SupplierBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("Name")));
		bean.setCategory(DataUtility.getString(request.getParameter("Category")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setPaymentTerm(DataUtility.getInt(request.getParameter("PaymentTerm")));
		return bean;
	}
	

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUPPLIER_LIST_VIEW;
	}

}
