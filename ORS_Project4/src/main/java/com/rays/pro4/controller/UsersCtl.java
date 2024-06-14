package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PriscriptionBean;
import com.rays.pro4.Bean.UsersBean;
import com.rays.pro4.Model.PriscriptionModel;
import com.rays.pro4.Model.UsersModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "UsersCtl", urlPatterns = { "/ctl/UsersCtl" })
public class UsersCtl extends BaseCtl {
	boolean pass = true;

	@Override
	protected boolean validate(HttpServletRequest request) {
		if (DataValidator.isNull(request.getParameter("UserName"))) {
			request.setAttribute("UserName", PropertyReader.getValue("error.require", "UserName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("UserName"))) {
			request.setAttribute("UserName", "Name must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "Dob"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Login"))) {
			request.setAttribute("Login", PropertyReader.getValue("error.require", "Login"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("Login"))) {
			request.setAttribute("Login", "Login Id is invalid Email ID");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Pass"))) {
			request.setAttribute("Pass", PropertyReader.getValue("error.require", "Pass"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("Pass"))) {
			request.setAttribute("Pass",
					"Pass contain 8 letters with alpha-numeric,capital latter & special Character");
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UsersBean bean = new UsersBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setUserName(DataUtility.getString(request.getParameter("UserName")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setLogin(DataUtility.getString(request.getParameter("Login")));
		bean.setPass(DataUtility.getString(request.getParameter("Pass")));
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		UsersModel model = new UsersModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			UsersBean bean;

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

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		UsersModel model = new 		UsersModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {
			
			System.out.println("in cancel button ");

			ServletUtility.redirect(ORSView.USERS_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USERS_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			UsersBean bean = (UsersBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("User is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Priscription not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("User is successfully Added", request);
					ServletUtility.setBean(bean, request);

					bean.setId(pk);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Priscription not added");
					e.printStackTrace();
				}

			}

		}
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USERS_VIEW;
	}

}
