package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "OrderCtl", urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("Order_Name"))) {
			request.setAttribute("Order_Name", PropertyReader.getValue("error.require", "Order Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Order_Name"))) {
			request.setAttribute("Order_Name", "Order Name must contains alphabet only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Order_Status"))) {
			request.setAttribute("Order_Status", PropertyReader.getValue("error.require", "Order Status"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Order_Price"))) {
			request.setAttribute("Order_Price", PropertyReader.getValue("error.require", "Order Price"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Birth of Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		OrderBean bean = new OrderBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setOrder_Name(DataUtility.getString(request.getParameter("Order_Name")));
 System.out.println(">>>>>>>>>>>>>>>>"+bean.getOrder_Name());
		bean.setOrder_Status(DataUtility.getString(request.getParameter("Order_Status")));

		bean.setOrder_Price(DataUtility.getString(request.getParameter("Order_Price")));
		System.out.println(">>>>>>>>>>>>>>>>!!!!!!!!!"+request.getParameter("Dob"));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		System.out.println("dob>>> " + bean.getDob());

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		OrderModel model = new OrderModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("product Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			OrderBean bean;

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

		OrderModel model = new OrderModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

 OrderBean bean = (OrderBean)  populateBean(request);
			 
			 try {
				 if (id > 0) {
			 
					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Order  is successfully Updated", request);
				 } else {
						System.out.println(" U ctl DoPost 33333");
						long pk = model.add(bean);
						//ServletUtility.setBean(bean, request);

						ServletUtility.setSuccessMessage("Order is successfully Added", request);

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

				OrderBean bean = (OrderBean) populateBean(request);
				try {
					model.delete(bean);

					ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
					return;
				} catch (ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				} }else if (OP_CANCEL.equalsIgnoreCase(op)) {
					System.out.println(" U  ctl Do post 77777");

					ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
					return;
				}
			ServletUtility.forward(getView(), request, response);
			}
			

		
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ORDER_VIEW;
	}

}
