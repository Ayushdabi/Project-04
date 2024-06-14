package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.SalaryBean;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Model.SalaryModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SalaryCtl", urlPatterns = { "/ctl/SalaryCtl" })
public class SalaryCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {

		SalaryModel model = new SalaryModel();
		try {
			List rlist = model.list();
			 request.setAttribute("rlist",rlist);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("Employee"))) {
			request.setAttribute("Employee", PropertyReader.getValue("error.require", "Employee"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Amount"))) {
			request.setAttribute("Amount", PropertyReader.getValue("error.require", "Amount"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Applied Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Status"))) {
			request.setAttribute("Status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		SalaryBean bean = new SalaryBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setEmployee(DataUtility.getString(request.getParameter("Employee")));
		bean.setAmount(DataUtility.getString(request.getParameter("Amount")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setStatus(DataUtility.getString(request.getParameter("Status")));
//bean.setId(DataUtility.getLong(request.getParameter("rlist")));
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		SalaryModel model = new SalaryModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			SalaryBean bean;

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

		SalaryModel model = new SalaryModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SALARY_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SALARY_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SalaryBean bean = (SalaryBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" Salary is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("product not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("Salary is successfully Added", request);

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
		return ORSView.SALARY_VIEW;
	}

}
