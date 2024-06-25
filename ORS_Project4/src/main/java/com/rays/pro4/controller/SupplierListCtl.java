package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.SupplierBean;
import com.rays.pro4.Bean.TransportationBean;
import com.rays.pro4.Model.SupplierModel;
import com.rays.pro4.Model.TransportationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "SupplierListCtl", urlPatterns = { "/ctl/SupplierListCtl" })

public class SupplierListCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {
		SupplierModel model = new SupplierModel();
		try {
			List rlist = model.list();
			request.setAttribute("prolist", rlist);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		SupplierBean bean = new SupplierBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("Name")));
		bean.setCategory(DataUtility.getString(request.getParameter("Category")));
		bean.setDob(DataUtility.getDate(request.getParameter("Dob")));
		bean.setPaymentTerm(DataUtility.getInt(request.getParameter("PaymentTerm")));
		return bean;
	}
	
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	List list = null;
	List nextList = null;

	int pageNo = 1;
	int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

	SupplierBean bean = (SupplierBean) populateBean(request);

	SupplierModel model = new SupplierModel();

	try {
		list = model.search(bean, pageNo, pageSize);
		nextList = model.search(bean, pageNo + 1, pageSize);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("list" + list);

	request.setAttribute("nextlist", nextList.size());

	if (list == null || list.size() == 0) {
		ServletUtility.setErrorMessage("No record found ", request);
	}

	ServletUtility.setList(list, request);
	ServletUtility.setPageNo(pageNo, request);
	ServletUtility.setPageSize(pageSize, request);
	ServletUtility.forward(getView(), request, response);
}
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	List list;
	List nextList = null;

	int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	pageNo = (pageNo == 0) ? 1 : pageNo;
	pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

	String op = DataUtility.getString(request.getParameter("operation"));
	SupplierBean bean = (SupplierBean) populateBean(request);

	String[] ids = request.getParameterValues("ids");

	SupplierModel model = new SupplierModel();

	if (OP_SEARCH.equalsIgnoreCase(op)) {
		pageNo = 1;
	} else if (OP_NEXT.equalsIgnoreCase(op)) {
		pageNo++;
	} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
		pageNo--;
	} else if (OP_NEW.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.SUPPLIER_CTL, request, response);
		return;
	} else if (OP_RESET.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.SUPPLIER_LIST_CTL, request, response);
		return;
	} else if (OP_DELETE.equalsIgnoreCase(op)) {
		pageNo = 1;
		if (ids != null && ids.length > 0) {
			SupplierBean deletebean = new SupplierBean();
			for (String id : ids) {
				deletebean.setId(DataUtility.getInt(id));

				try {
					model.delete(deletebean);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ServletUtility.setSuccessMessage("Supplier is Deleted Successfully", request);
			}
		} else {
			ServletUtility.setErrorMessage("Select at least one record", request);
		}
	}

	try {

		list = model.search(bean, pageNo, pageSize);

		nextList = model.search(bean, pageNo + 1, pageSize);

		request.setAttribute("nextlist", nextList.size());

	} catch (Exception e) {
		ServletUtility.handleException(e, request, response);
		return;
	}
	if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
		ServletUtility.setErrorMessage("No record found ", request);
	}
	ServletUtility.setList(list, request);
	ServletUtility.setBean(bean, request);
	ServletUtility.setPageNo(pageNo, request);
	ServletUtility.setPageSize(pageSize, request);
	ServletUtility.forward(getView(), request, response);
}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUPPLIER_LIST_VIEW;
	}

}
