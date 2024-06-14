package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.CarBean;
import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Model.CarModel;
import com.rays.pro4.Model.EmployeeModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "CarCtl", urlPatterns = { "/ctl/CarCtl" })

public class CarCtl extends BaseCtl{
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("OwnerName"))) {
			request.setAttribute("OwnerName", PropertyReader.getValue("error.require", "Owner Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("CarName"))) {
			request.setAttribute("CarName", PropertyReader.getValue("error.require", "Car Name"));
			pass = false;
		}		
		if (DataValidator.isNull(request.getParameter("CarPrice"))) {
			request.setAttribute("CarPrice", PropertyReader.getValue("error.require", "Car Price"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Purchase Date"));
			pass = false;
		}
		return pass;
	}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CarBean bean = new CarBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setOwnerName(DataUtility.getString(request.getParameter("OwnerName")));
		bean.setCarName(DataUtility.getString(request.getParameter("CarName")));
		bean.setCarPrice(DataUtility.getString(request.getParameter("CarPrice")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));


		return bean;

	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		 CarModel model = new CarModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			CarBean bean;

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
	String op = DataUtility.getString(request.getParameter("operation"));

	long id = DataUtility.getLong(request.getParameter("id"));

	System.out.println(">>>>>>>>>>>>>>>>.**********" + id + op);

	CarModel model = new CarModel();

	if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.CAR_LIST_CTL, request, response);

	}
	if (OP_RESET.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.CAR_CTL, request, response);

	}

	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

		CarBean bean = (CarBean) populateBean(request);

		if (id > 0) {

			try {
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Car is successfully Updated", request);
				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {
				System.out.println("Car not update");
				e.printStackTrace();
			}

		} else {

			try {
				long pk = model.add(bean);
				//ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Car is successfully Added", request);
				bean.setId(pk);
				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {
				System.out.println("car not added");
				e.printStackTrace();
			}

		}

	}

}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CAR_VIEW;
	}

}
