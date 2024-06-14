package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.DoctarBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Model.DoctarModel;
import com.rays.pro4.Model.FollowUpModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;
@WebServlet(name = "FollowUpCtl", urlPatterns = { "/ctl/FollowUpCtl" })

public class FollowUpCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {
		FollowUpModel model = new FollowUpModel();
		try {
			List plist = model.list();
			request.setAttribute("pro", plist);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("Patient"))) {
			request.setAttribute("Patient", PropertyReader.getValue("error.require", "Patient"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Patient"))) {
			request.setAttribute("Patient", "Patient must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Doctor"))) {
			request.setAttribute("Doctor", PropertyReader.getValue("error.require", "Doctor"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Doctor"))) {
			request.setAttribute("Doctor", "Doctor must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Fess"))) {
			request.setAttribute("Fess", PropertyReader.getValue("error.require", "Fess"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("Fess"))) {
			request.setAttribute("Fess", "Fess Contain Intger Values");
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		FollowUpBean bean = new FollowUpBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPatient(DataUtility.getString(request.getParameter("Patient")));
		bean.setDoctor(DataUtility.getString(request.getParameter("Doctor")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setFess(DataUtility.getString(request.getParameter("Fess")));
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		FollowUpModel model = new FollowUpModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			FollowUpBean bean;

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

		FollowUpModel model = new FollowUpModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FOLLOWUP_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FOLLOWUP_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			FollowUpBean bean = (FollowUpBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("FollowUp is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("product not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("FollowUp is successfully Added", request);
					ServletUtility.setBean(bean, request);

					bean.setId(pk);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Doctor not added");
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FOLLOWUP_VIEW;
	}

}
