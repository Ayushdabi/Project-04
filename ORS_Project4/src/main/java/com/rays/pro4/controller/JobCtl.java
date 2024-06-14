package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.JobBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Model.JobModel;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "JobCtl", urlPatterns = { "/ctl/JobCtl" })

public class JobCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		JobModel model = new JobModel();

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

		if (DataValidator.isNull(request.getParameter("Title"))) {
			request.setAttribute("Title", PropertyReader.getValue("error.require", "Title"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Opening Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Experience"))) {
			request.setAttribute("Experience", PropertyReader.getValue("error.require", "Experience"));
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

		JobBean bean = new JobBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setTitle(DataUtility.getString(request.getParameter("Title")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setExperience(DataUtility.getString(request.getParameter("Experience")));
		bean.setStatus(DataUtility.getString(request.getParameter("Status")));
		bean.setId(DataUtility.getLong(request.getParameter("Status")));

		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		JobModel model = new JobModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("Job Edit Id >= " + id);

		if (id != 0 && id > 0) {

			// System.out.println("in id > 0 condition " + id);
			JobBean bean;

			try {
				bean = model.findByPK(id);
				System.out.println("aa gai hai id > 0  condition " + id);

				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		JobModel model = new JobModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.JOB_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.JOB_CTL, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			JobBean bean = (JobBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Product is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("product not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("Product is successfully Added", request);

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
		return ORSView.JOB_VIEW;
	}

}
