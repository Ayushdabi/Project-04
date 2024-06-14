<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.PaymentCtl"%>
<%@page import="com.rays.pro4.Bean.PaymentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.PaymentListCtl"%>
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

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

<script>
	$(function() {
		$("#Udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.PaymentBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.PAYMENT_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>Payment List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				List ulist = (List) request.getAttribute("account");
			        List plist = (List)request.getAttribute("Dob");
			        List alist = (List)request.getAttribute("A_Name");
				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<PaymentBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
			
			 <td align="center"><label>Custumer Name</font> :
			 
				</label> <input type="text" name="A_Name" placeholder="Enter Custumer Name"
					value="<%=ServletUtility.getParameter("A_Name", request)%>">
				
			
			
			 <label>Payment Date </font> :

					</label> <%=HTMLUtility.getList("Dob", String.valueOf(bean.getDob()), plist)%> 
<%-- <label>Customer Name</font> :

					</label> <%=HTMLUtility.getList("A_Name", String.valueOf(bean.getA_Name()), alist)%>
 --%>
  
				 
				<%--  <td align="center"><label>Payment Mode</font> :
				</label> <input type="text" name="account" placeholder="Enter Payment Mode"
					value="<%=ServletUtility.getParameter("account", request)%>">
					  --%>

				<%-- <td align="center"><label>UPI_ID</font> :
				</label> <input type="text" name="Upi_id" placeholder="Enter Upi_id"
					value="<%=ServletUtility.getParameter("Upi_id", request)%>"> --%>

				<%-- <td align="center"><label>Total_Amount</font> :
				</label> <input type="text" name="Total_Amount"
					placeholder="Enter Total_Amount"
					value="<%=ServletUtility.getParameter("Total_Amount", request)%>">
					
 --%>
           
 
				<%--  <td align="center"><label>Payment Date </font> :
				</label> <input type="text"  id="Udate"name="Dob" placeholder="Enter Payment Date"
					value="<%=ServletUtility.getParameter("Dob", request)%>">
				  --%>
				<input type="submit" name="operation" value="<%=PaymentListCtl.OP_RESET%>">
					<input type="submit" name="operation"
					value="<%=PaymentListCtl.OP_SEARCH%>"></td>




			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style="background: Skyblue">
				<th><input type="checkbox" id="select_all" name="select">Select
						All</th>
					

					<th>S.No.</th>
					<th>Customer Name</th>
					<th>Payment Mode</th>
					<th>UPI_ID</th>
					<th>Total_Amount</th>
					<th>Payment Date</th>
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
					<td><%=bean.getA_Name()%></td>
					<td><%=bean.getAccount()%></td>
					<td><%=bean.getUpi_id()%></td>
					<td><%=bean.getTotal_Amount()%></td>
					<td><%=bean.getDob()%></td>
					<td><a href="PaymentCtl?cid=<%=bean.getId()%>">edit</td>


				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">

				<tr>
					<th></th>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=PaymentListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=PaymentListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=PaymentListCtl.OP_DELETE%>"></td>
					<td align="center"><input type="submit" name="operation"
						value="<%=PaymentListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=PaymentListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=PaymentListCtl.OP_BACK%>"></td>


			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
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