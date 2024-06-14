<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.BankCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bank page</title>
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.BankBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.BANK_CTL%>" method="post">

			<%
				List list = (List) request.getAttribute("roleList");
			%>
			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Bank </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Bank </font></th>
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

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>
				<tr>
					<th align="left">Bank Name<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="Bank_Name"
						placeholder="Enter Bank Name" size="25"
						value="<%=DataUtility.getStringData(bean.getBank_Name())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("Bank_Name", request)%></font></td>

				</tr>


				<tr>
					<th align="left">Account NO<span style="color: red">*</span> :
					</th>
					<td><input type="number" name="Account_NO"
						placeholder="Enter Account NO" style="width: 195px" maxlength="10"
						value="<%=DataUtility.getStringData(bean.getAccount_NO())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Account_NO", request)%></font></td>
				</tr>


				<tr>
					<th align="left">Customer Name<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Customer_Name"
						placeholder="Enter Customer Name" size="25"
						value="<%=DataUtility.getStringData(bean.getCustomer_Name())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Customer_Name", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Date of Birth<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Dob"
						placeholder="Enter Date Of Birth" size="25" id="udate"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Dob", request)%></font></td>
				</tr>

				<%-- <tr>
					<th align="left">Address<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="Address"
						placeholder="Enter Address" size="25"
						value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Address", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
 --%>
				<tr>
					<th align="left">Address <span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("indore", "indore");
							map.put("Dewas", "Dewas");
							map.put("Bhopal", "Bhopal");

							String hlist = HTMLUtility.getList("Address", String.valueOf(bean.getAddress()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Address", request)%></font></td>
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
						name="operation" value="<%=BankCtl.OP_UPDATE%>"> &nbsp; <input
						type="submit" name="operation" value="<%=BankCtl.OP_CANCEL%>">
						&nbsp; <%
 	} else {
 %>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=BankCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=BankCtl.OP_RESET%>">


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