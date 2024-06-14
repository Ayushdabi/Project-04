<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.BankListCtl"%>
<%@page import="com.rays.pro4.Bean.BankBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>



<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		//  mindefaultDate : "01-01-1962"
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.BankBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.BANK_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Bank List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<%
				List dlist = (List) request.getAttribute("add");
				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<BankBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
<tr>
				<td align="left"><label>Bank Name</font> :

				</label> <input type="text" name="Bank_Name" placeholder="Enter Bank Name"
					value="<%=ServletUtility.getParameter("Bank_Name", request)%>">
				<label>Date</font> :
				</label> <input type="text" name="Dob" id="udate"
					placeholder="Enter Date of birth"
					value="<%=ServletUtility.getParameter("Dob", request)%>"> <label>Address</label>
					<%=HTMLUtility.getList("Address", String.valueOf(bean.getAddress()), dlist)%>
				<label>Account NO </font> :
				</label> <input type="text" name="Account_NO"
					placeholder="Enter Custumer Name"
					value="<%=ServletUtility.getParameter("Account_NO", request)%>">
					
					
					<th>Customer_Name<span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Abhi", "Abhi");
							map.put("Priya", "Priya");
							map.put("Mahi", "Mahi");


							String hlist = HTMLUtility.getList("Customer_Name", String.valueOf(bean.getCustomer_Name()), map);
						%> <%=hlist%>
					</td>
				</tr>
				
				<%-- <td align="center"><label>Customer Name</font> :
				</label> <input type="text" name="Customer_Name"
					placeholder="Enter Customer Name"
					value="<%=ServletUtility.getParameter("Customer_Name", request)%>">
				 --%>	<%---  <td align="center"><label>Address</font> :
				</label> <input type="text" name="Address"
					placeholder="Enter Valid Address"
					value="<%=ServletUtility.getParameter("Address", request)--%> <input
					type="submit" name="operation" value="<%=BankListCtl.OP_RESET%>">
					<input type="submit" name="operation"
					value="<%=BankListCtl.OP_SEARCH%>"></td>






			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Yellow">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>


					<th>S.No.</th>
					<th>Bank Name</th>
					<th>Account NO</th>
					<th>Customer Name</th>
					<th>Dob</th>
					<th>Address</th>
					<th>Edit</th>
				</tr>
				<%
					while (it.hasNext()) {
							bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>

					<td><%=index++%></td>
					<td><%=bean.getBank_Name()%></td>
					<td><%=bean.getAccount_NO()%></td>
					<td><%=bean.getCustomer_Name()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=bean.getAddress()%></td>
					<td><a href="BankCtl?id=<%=bean.getId()%>">Edit</td>
				</tr>
				<%
					}
				%>

				<table width="100%">

					<tr>
						<th></th>
						<%
							if (pageNo == 1) {
						%>
						<td><input type="submit" name="operation" disabled="disabled"
							value="<%=BankListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=BankListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=BankListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=BankListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=BankListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=BankListCtl.OP_BACK%>"></td>


				<%
					}
				%>

				<input type="hidden" name="pageNo" value="<%=pageNo%>">
				<input type="hidden" name="pageSize" value="<%=pageSize%>">

				</form>
				</br>
				</br>
				</br>
				</br>
				</br>
				</br>
				</br>

				</center>
				<%@include file="Footer.jsp"%>
</body>
</html>