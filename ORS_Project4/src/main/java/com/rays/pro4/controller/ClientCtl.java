package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ClientCtl", urlPatterns = { "/ctl/ClientCtl" })
public class ClientCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {
		ClientModel model = new ClientModel();


		try {

			List rlist = model.list();
			request.setAttribute("rlist", rlist);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected boolean validate(HttpServletRequest request) {
	
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("ClientName"))) {
			request.setAttribute("ClientName", PropertyReader.getValue("error.require", "Client Name"));
			pass = false;
		}
		if (!DataValidator.isName(request.getParameter("ClientName"))) {
			request.setAttribute("ClientName", "ClientName is contain alpabat only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Address"))) {
			request.setAttribute("Address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Phone"))) {
			request.setAttribute("Phone", PropertyReader.getValue("error.require", "Phone"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("Phone"))) {
			request.setAttribute("Phone", "Phone. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("Priortiy"))) {
			request.setAttribute("Priortiy", PropertyReader.getValue("error.require", "Priortiy"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		

		ClientBean bean = new ClientBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setClientName(DataUtility.getString(request.getParameter("ClientName")));

		bean.setAddress(DataUtility.getString(request.getParameter("Address")));
		bean.setPhone(DataUtility.getString(request.getParameter("Phone")));

		bean.setPriortiy(DataUtility.getString(request.getParameter("Priortiy")));
		//bean.setId(DataUtility.getLong(request.getParameter("Priortiy")));

		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));

		ClientModel model = new ClientModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("Client Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			ClientBean bean;

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

		ClientModel model = new ClientModel();

		if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);

		}
		if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CLIENT_CTL
					, request, response);

		}

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ClientBean bean = (ClientBean) populateBean(request);

			if (id > 0) {

				try {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Client is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Client not update");
					e.printStackTrace();
				}

			} else {

				try {
					long pk = model.add(bean);
					
					ServletUtility.setSuccessMessage("Client is successfully Added", request);
					ServletUtility.setBean(bean, request);

					bean.setId(pk);
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					System.out.println("Client not added");
					e.printStackTrace();
				}

			}

		}

	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CLIENT_VIEW;
	}

}
