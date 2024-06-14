<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.PaymentCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment page</title>
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PAYMENT_CTL%>" method="post">

			<%
				List l = (List) request.getAttribute("roleList");
				List rlist = (List) request.getAttribute("Upi");
			%>
			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Payment </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Payment </font></th>
					</tr>
					<%
						}
					%>
				</h1>
				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<input type="hidden" name="cid" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>
				<tr>
					<th align="left">Customer Name<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="AName"
						placeholder="Enter Customer Name" size="25"
						value="<%=DataUtility.getStringData(bean.getA_Name())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("AName", request)%></font></td>

				</tr>


				<tr>
					<th align="left">Payment Mode<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="account"
						placeholder="Enter Payment Mode" size="25"
						value="<%=DataUtility.getStringData(bean.getAccount())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("account", request)%></font></td>
				</tr>


				<%-- <tr>
					<th align="left">UPI ID<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="Upi_id"
						placeholder="Enter UPi ID" size="25"
						value="<%=DataUtility.getStringData(bean.getUpi_id())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Upi_id", request)%></font></td>
				</tr> --%>
				 <tr>
					<th align="left">Upi_id <span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("Upi_id", String.valueOf(bean.getUpi_id()), rlist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("Upi_id", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr> 
 
				<tr>
					<th align="left">Total Amount<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Total_Amount"
						placeholder="Enter Total_Amount" size="25"
						value="<%=DataUtility.getStringData(bean.getTotal_Amount())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Total_Amount", request)%></font></td>
				</tr>



				<tr>
					<th align="left">Payment Date<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Dob"
						placeholder="Enter Date Of Birth" size="25" id="Udate"
						value="<%=DataUtility.getStringData(bean.getDob())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Dob", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=PaymentCtl.OP_UPDATE%>"> &nbsp;
						<input type="submit" name="operation"
						value="<%=PaymentCtl.OP_CANCEL%>"> &nbsp; <%
 	} else {
 %>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=PaymentCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=PaymentCtl.OP_RESET%>">


						<%
							}
						%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>

</body>
</html>
