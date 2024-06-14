package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PriscriptionBean;
import com.rays.pro4.Bean.ProjectBean;
import com.rays.pro4.Model.PriscriptionModel;
import com.rays.pro4.Model.ProjectModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PriscriptionCtl", urlPatterns = { "/ctl/PriscriptionCtl" })

public class PriscriptionCtl extends  BaseCtl{
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("Name"))) {
			request.setAttribute("Name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Name"))) {
			request.setAttribute("Name", "Name must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Decease"))) {
			request.setAttribute("Decease", PropertyReader.getValue("error.require", "Decease"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Decease"))) {
			request.setAttribute("Decease", "Deacease must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "Open Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("capicity"))) {
			request.setAttribute("capicity", PropertyReader.getValue("error.require", "capicity"));
			pass = false;
		}
		
		return pass;
		
	}

	 @Override
	protected BaseBean populateBean(HttpServletRequest request) {
		 PriscriptionBean bean = new PriscriptionBean();
			bean.setId(DataUtility.getLong(request.getParameter("id")));

			bean.setName(DataUtility.getString(request.getParameter("Name")));
			bean.setDecease(DataUtility.getString(request.getParameter("Decease")));
			bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
			bean.setCapicity(DataUtility.getString(request.getParameter("capicity")));

			return bean;
	}
	 
	 @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String op = DataUtility.getString(request.getParameter("operation"));

			PriscriptionModel model = new PriscriptionModel();

			long id = DataUtility.getLong(request.getParameter("id"));

			System.out.println("product Edit Id >= " + id);

			if (id != 0 && id > 0) {

				System.out.println("in id > 0  condition " + id);
				PriscriptionBean bean;

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

			PriscriptionModel model = new PriscriptionModel();

			if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.PRISCRIPTION_LIST_CTL, request, response);

			}
			if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.PRISCRIPTION_CTL, request, response);

			}

			if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

				PriscriptionBean bean = (PriscriptionBean) populateBean(request);

				if (id > 0) {

					try {
						model.update(bean);
						ServletUtility.setBean(bean, request);
						ServletUtility.setSuccessMessage("Priscription is successfully Updated", request);
						ServletUtility.forward(getView(), request, response);
					} catch (Exception e) {
						System.out.println("Priscription not update");
						e.printStackTrace();
					}

				} else {

					try {
						long pk = model.add(bean);

						ServletUtility.setSuccessMessage("Priscription is successfully Added", request);
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
		return ORSView.PRISCRIPTION_VIEW;
	}

}
