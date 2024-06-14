package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PaymentBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.PaymentModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PaymentCtl", urlPatterns = { "/ctl/PaymentCtl" })
public class PaymentCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {
		PaymentModel model = new PaymentModel();

		try {
			List rlist = model.list(0, 0);
			request.setAttribute("Upi", rlist);

		} catch (Exception e) {

		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("AName"))) {
			request.setAttribute("AName", PropertyReader.getValue("error.require", "Customer Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("AName"))) {
			request.setAttribute("AName", " Must Contain  alphabet Only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("account"))) {
			request.setAttribute("account", PropertyReader.getValue("error.require", "Payment Mode"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Upi_id"))) {
			request.setAttribute("Upi_id", PropertyReader.getValue("error.require", "Upi_id"));
			pass = false;
		}
		// else if (!DataValidator.isName(request.getParameter("Upi_id"))) {
//			request.setAttribute("Upi_id", " Must Contain  alphabet Only");
//			pass = false;
//		}
		if (DataValidator.isNull(request.getParameter("Total_Amount"))) {
			request.setAttribute("Total_Amount", PropertyReader.getValue("error.require", "Total_Amount"));
			pass = false;
		}
		// else if (!DataValidator.is(request.getParameter(" Total_Amount"))) {
//			request.setAttribute(" Total_Amount", " Must Contain  alphabet Only");
//			pass = false;
//		}

		/*
		 * else if (!DataValidator.isName(request.getParameter("accu"))) {
		 * request.setAttribute("accu", "contain alphabet only"); pass = false; }
		 */

		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Payment Date"));
			pass = false;
		}
		/*
		 * else if (!DataValidator.isDate(request.getParameter("Dob"))) {
		 * request.setAttribute("Dob", PropertyReader.getValue("error.date",
		 * "Date Of Birth")); pass = false; }
		 */

		return pass;

	}

	protected BaseBean populateBean(HttpServletRequest request) {

		PaymentBean bean = new PaymentBean();

		bean.setId(DataUtility.getLong(request.getParameter("cid")));
		bean.setA_Name(DataUtility.getString(request.getParameter("AName")));
		bean.setAccount(DataUtility.getString(request.getParameter("account")));
		bean.setUpi_id(DataUtility.getString(request.getParameter("Upi_id")));
		bean.setTotal_Amount(DataUtility.getString(request.getParameter("Total_Amount")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setUpi_id(DataUtility.getString(request.getParameter("Upi_id")));

		System.out.println("dobbbb" + bean.getDob());

		populateDTO(bean, request);

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		PaymentModel model = new PaymentModel();
		long id = DataUtility.getLong(request.getParameter("cid"));
		if (id > 0 || op != null) {

			PaymentBean bean;
			try {
				bean = model.findByPK(id);

				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("cid"));

		PaymentModel model = new PaymentModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			PaymentBean bean = (PaymentBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Payment is successfully Updated", request);

				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("Payment is successfully Added", request);

					bean.setId(pk);
				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			PaymentBean bean = (PaymentBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.PAYMENT_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.PAYMENT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PAYMENT_VIEW;
	}

}
