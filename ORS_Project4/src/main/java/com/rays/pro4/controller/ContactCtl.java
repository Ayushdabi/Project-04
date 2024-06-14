package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ContactBean;
import com.rays.pro4.Bean.LeadBean;
import com.rays.pro4.Model.ContactModel;
import com.rays.pro4.Model.LeadModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ContactCtl", urlPatterns = { "/ctl/ContactCtl" })

public class ContactCtl extends BaseCtl {

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
		if (DataValidator.isNull(request.getParameter("City"))) {
			request.setAttribute("City", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Date of birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Mobile"))) {
			request.setAttribute("Mobile", PropertyReader.getValue("error.require", "Mobile"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("Mobile"))) {
			request.setAttribute("Mobile", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ContactBean bean = new ContactBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("Name")));
		bean.setCity(DataUtility.getString(request.getParameter("City")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setMobile(DataUtility.getString(request.getParameter("Mobile")));
		return bean;

	}public ContactCtl() {
		// TODO Auto-generated constructor stub
	}@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		ContactModel  model = new ContactModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			ContactBean bean;

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
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("uctl Do Post");

	String op = DataUtility.getString(request.getParameter("operation"));

	long id = DataUtility.getLong(request.getParameter("id"));

	System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

	ContactModel model = new ContactModel();

	if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.CONTACT_LIST_CTL, request, response);

	}
	if (OP_RESET.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.CONTACT_CTL, request, response);

	}

	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

		ContactBean bean = (ContactBean) populateBean(request);

		if (id > 0) {

			try {
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Contact is successfully Updated", request);
				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {
				System.out.println("Contact not update");
				e.printStackTrace();
			}

		} else {

			try {
				long pk = model.add(bean);
				ServletUtility.setSuccessMessage("Contact is successfully Added", request);
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
		return ORSView.CONTACT_VIEW;
	}

}
