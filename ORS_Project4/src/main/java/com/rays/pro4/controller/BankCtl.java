package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "BankCtl", urlPatterns = { "/ctl/BankCtl" })
public class BankCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("Bank_Name"))) {
			request.setAttribute("Bank_Name", PropertyReader.getValue("error.require", "Bank Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Bank_Name"))) {
			request.setAttribute("Bank_Name", "Bank_Name must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Account_NO"))) {
			request.setAttribute("Account_NO", PropertyReader.getValue("error.require", "Account NO"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("Account_NO"))) {
			request.setAttribute("Account_NO", "Account_NO must be 10 Digit ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Customer_Name"))) {
			request.setAttribute("Customer_Name", PropertyReader.getValue("error.require", "Customer Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Customer_Name"))) {
			request.setAttribute("Customer_Name", "Customer_Name must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Address"))) {
			request.setAttribute("Address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		BankBean bean = new BankBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setBank_Name(DataUtility.getString(request.getParameter("Bank_Name")));
		bean.setAccount_NO(DataUtility.getString(request.getParameter("Account_NO")));
		bean.setCustomer_Name(DataUtility.getString(request.getParameter("Customer_Name")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setAddress(DataUtility.getString(request.getParameter("Address")));
             populateDTO(bean, request);
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getStringData(request.getParameter("operation"));
		BankModel model = new BankModel();
		Long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {

			BankBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;

			}

		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));
		
		BankModel model = new BankModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			 BankBean bean = (BankBean)  populateBean(request);
			 
			 try {
				 if (id > 0) {
			 
					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Customer  is successfully Updated", request);
				 } else {
						System.out.println(" U ctl DoPost 33333");
						long pk = model.add(bean);
						ServletUtility.setBean(bean, request);

						ServletUtility.setSuccessMessage("Customer is successfully Added", request);

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

				BankBean bean = (BankBean) populateBean(request);
				try {
					model.delete(bean);

					ServletUtility.redirect(ORSView.BANK_CTL, request, response);
					return;
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				} }else if (OP_CANCEL.equalsIgnoreCase(op)) {
					System.out.println(" U  ctl Do post 77777");

					ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
					return;
				}
			ServletUtility.forward(getView(), request, response);
			}
			


	@Override
	protected String getView() {
		return ORSView.BANK_VIEW;
	}

}
