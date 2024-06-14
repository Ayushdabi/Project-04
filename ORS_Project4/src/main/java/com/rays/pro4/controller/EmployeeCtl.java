package com.rays.pro4.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.EmployeeModel;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "EmployeeCtl", urlPatterns = { "/ctl/EmployeeCtl" })

public class EmployeeCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {
		EmployeeModel model = new EmployeeModel();
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

		if (DataValidator.isNull(request.getParameter("EmployeeName"))) {
			request.setAttribute("EmployeeName", PropertyReader.getValue("error.require", "Employee Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Department"))) {
			request.setAttribute("Department", PropertyReader.getValue("error.require", "Department"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Joing Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("LastName"))) {
			request.setAttribute("LastName", PropertyReader.getValue("error.require", "LastName"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		EmployeeBean bean = new EmployeeBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setEmployeeName(DataUtility.getString(request.getParameter("EmployeeName")));
		bean.setDepartment(DataUtility.getString(request.getParameter("Department")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setLastName(DataUtility.getString(request.getParameter("LastName")));
		bean.setId(DataUtility.getLong(request.getParameter("Department")));


		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		EmployeeModel model = new EmployeeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			EmployeeBean bean;

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

		System.out.println(">>>>>>>>>>>>>>>>.**********" + id + op);

		EmployeeModel model = new EmployeeModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EMPLOYEE_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			EmployeeBean bean = (EmployeeBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Employee is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("employee not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);
					//ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("employee is successfully Added", request);
					bean.setId(pk);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("product not added");
					e.printStackTrace();
				}

			}

		}

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.EMPLOYEE_VIEW;
	}

}
