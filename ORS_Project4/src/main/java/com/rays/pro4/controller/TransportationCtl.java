package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ContactBean;
import com.rays.pro4.Bean.TransportationBean;
import com.rays.pro4.Model.ContactModel;
import com.rays.pro4.Model.TransportationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;
@WebServlet(name = "TransportationCtl", urlPatterns = { "/ctl/TransportationCtl" })

public class TransportationCtl extends BaseCtl {
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("Description"))) {
			request.setAttribute("Description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Description"))) {
			request.setAttribute("Description", "Description  must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Mode"))) {
			request.setAttribute("Mode", PropertyReader.getValue("error.require", "Mode"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Mode"))) {
			request.setAttribute("Mode", "Mode  must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Date of birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Cost"))) {
			request.setAttribute("Cost", PropertyReader.getValue("error.require", "Cost"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("Cost"))) {
			request.setAttribute("Cost", "Cost Contain Integer Value only");
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TransportationBean bean = new TransportationBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setDescription(DataUtility.getString(request.getParameter("Description")));
		bean.setMode(DataUtility.getString(request.getParameter("Mode")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setCost(DataUtility.getString(request.getParameter("Cost")));

		return bean;

	}
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String op = DataUtility.getString(request.getParameter("operation"));

	TransportationModel  model = new TransportationModel();

	long id = DataUtility.getLong(request.getParameter("id"));

	System.out.println("product Edit Id >= " + id);

	if (id != 0 && id > 0) {

		System.out.println("in id > 0  condition " + id);
		TransportationBean bean;

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

	TransportationModel model = new TransportationModel();

	if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.TRANSPORTATION_LIST_CTL, request, response);

	}
	if (OP_RESET.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.TRANSPORTATION_CTL, request, response);

	}

	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

		TransportationBean bean = (TransportationBean) populateBean(request);

		if (id > 0) {

			try {
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Transportation is successfully Updated", request);
				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {
				System.out.println("Contact not update");
				e.printStackTrace();
			}

		} else {

			try {
				long pk = model.add(bean);
				ServletUtility.setSuccessMessage("Transportation is successfully Added", request);
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
		return ORSView.TRANSPORTATION_VIEW;
	}

}
