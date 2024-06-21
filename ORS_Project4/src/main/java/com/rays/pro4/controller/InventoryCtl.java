package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.InventoryBean;
import com.rays.pro4.Bean.TransportationBean;
import com.rays.pro4.Model.InventoryModel;
import com.rays.pro4.Model.TransportationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "InventoryCtl", urlPatterns = { "/ctl/InventoryCtl" })

public class InventoryCtl extends BaseCtl {
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("SupplierName"))) {
			request.setAttribute("SupplierName", PropertyReader.getValue("error.require", "SupplierName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("SupplierName"))) {
			request.setAttribute("SupplierName", "SupplierName  must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "LastUpdate Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "LastUpdate Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Quantity"))) {
			request.setAttribute("Quantity", PropertyReader.getValue("error.require", "Quantity"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("Quantity"))) {
			request.setAttribute("Quantity", "Quantity contain intger value only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Product"))) {
			request.setAttribute("Product", PropertyReader.getValue("error.require", "Product"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Product"))) {
			request.setAttribute("Product", " Product must contains alphabet only");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		InventoryBean bean = new InventoryBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSupplierName(DataUtility.getString(request.getParameter("SupplierName")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setQuantity(DataUtility.getString(request.getParameter("Quantity")));
		bean.setProduct(DataUtility.getString(request.getParameter("Product")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		InventoryModel model = new InventoryModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			InventoryBean bean;

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
		System.out.println("uctl Do Post");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		InventoryModel model = new InventoryModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			InventoryBean bean = (InventoryBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Inventory is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Contact not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);
					ServletUtility.setSuccessMessage("Inventory is successfully Added", request);
					ServletUtility.setBean(bean, request);

					bean.setId(pk);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Contact not added");
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.INVENTORY_VIEW;
	}

}
