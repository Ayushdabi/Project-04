package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SupplierBean;
import com.rays.pro4.Bean.TransportationBean;
import com.rays.pro4.Model.SupplierModel;
import com.rays.pro4.Model.TransportationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SupplierCtl", urlPatterns = { "/ctl/SupplierCtl" })

public class SupplierCtl extends BaseCtl {
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("Name"))) {
			request.setAttribute("Name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Name"))) {
			request.setAttribute("Name", "Name  must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Category"))) {
			request.setAttribute("Category", PropertyReader.getValue("error.require", "Category"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Category"))) {
			request.setAttribute("Category", "Category  must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "RegistrationDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "RegistrationDate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("PaymentTerm"))) {
			request.setAttribute("PaymentTerm", PropertyReader.getValue("error.require", "PaymentTerm"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("PaymentTerm"))) {
			request.setAttribute("PaymentTerm", "PaymentTerm Contain Integer Value only");
			pass = false;
		}

		return pass;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		SupplierModel model = new SupplierModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			SupplierBean bean;

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

		SupplierModel model = new SupplierModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUPPLIER_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUPPLIER_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SupplierBean bean = (SupplierBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Supplier is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Contact not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);
					ServletUtility.setSuccessMessage("Supplier is successfully Added", request);
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
		return ORSView.SUPPLIER_VIEW;
	}

}
