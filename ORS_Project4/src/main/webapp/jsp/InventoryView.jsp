<%@page import="com.rays.pro4.controller.InventoryCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Inventory Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.InventoryBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.INVENTORY_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Inventory </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Inventory </font></th>
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

			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
				<tr>
					<th align="left">SupplierName<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="SupplierName"
						placeholder="Enter SupplierName" size="25"
						value="<%=DataUtility.getStringData(bean.getSupplierName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("SupplierName", request)%></font></td>

				</tr>




				<tr>
					<th align="left">LastUpdate Date<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Dob"
						placeholder="Enter Date of Birth" size="25" id="udatee"
					
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("Dob", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>





				<tr>
					<th align="left">Quantity<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="Quantity" style="width: 195px"
						placeholder="Enter  Quantity"
						value="<%=DataUtility.getStringData(bean.getQuantity())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Quantity", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Product<span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Book", "Book");
							map.put("Leptop", "Leptop");
							map.put("Bag", "Bag");

							String hlist = HTMLUtility.getList("Product", String.valueOf(bean.getProduct()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Product", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>





				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=InventoryCtl.OP_UPDATE%>">
						&nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=InventoryCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=InventoryCtl.OP_SAVE%>">
						&nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=InventoryCtl.OP_RESET%>"></td>

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