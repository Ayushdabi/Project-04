package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.ShoppingBean;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Model.ShoppingModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ShoppingCtl", urlPatterns = { "/ctl/ShoppingCtl" })

public class ShoppingCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		ShoppingModel model = new ShoppingModel();

		ShoppingBean bean = new ShoppingBean();

		try {

			List rlist = model.list();
			request.setAttribute("rlist", rlist);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("ProductName"))) {
			request.setAttribute("ProductName", PropertyReader.getValue("error.require", "Product Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ShopName"))) {
			request.setAttribute("ShopName", PropertyReader.getValue("error.require", "Shop Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("ProductPrice"))) {
			request.setAttribute("ProductPrice", PropertyReader.getValue("error.require", "Product Price"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Purchase Date"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ShoppingBean bean = new ShoppingBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setProductName(DataUtility.getString(request.getParameter("ProductName")));
		bean.setShopName(DataUtility.getString(request.getParameter("ShopName")));
		bean.setProductPrice(DataUtility.getString(request.getParameter("ProductPrice")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setId(DataUtility.getLong(request.getParameter("ProductPrice")));
		

		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		ShoppingModel model = new ShoppingModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			ShoppingBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		ShoppingModel model = new ShoppingModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SHOPPING_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SHOPPING_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ShoppingBean bean = (ShoppingBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Shopping is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Shopping not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);
					ServletUtility.setSuccessMessage("Shopping is successfully Added", request);
					bean.setId(pk);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Shopping not added");
					e.printStackTrace();
				}

			}

		}

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SHOPPING_VIEW;
	}

}
