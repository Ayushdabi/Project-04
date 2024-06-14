<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.ShoppingCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Shopping Page</title>

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
			yearRange : '1980:2020',
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ShoppingBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.SHOPPING_CTL%>" method="post">

			<div align="center">
				<h1>
<%    List rlist=(List) request.getAttribute("rlist");%>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Shopping </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Shopping </font></th>
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
					<th align="left">Product Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="ProductName"
						placeholder="Enter Product Name" size="25"
						value="<%=DataUtility.getStringData(bean.getProductName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("ProductName", request)%></font></td>

				</tr>

				
				
				<tr>
					<th align="left">Shop Name<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="ShopName"
						placeholder="Enter  Shop Name" size="25"
						value="<%=DataUtility.getStringData(bean.getShopName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("ShopName", request)%></font></td>
					</tr>
					
					
				 
					
					
			<%-- 		<td>
						<%
							HashMap map = new HashMap();
							map.put("1500", "1500");
							map.put("1600", "1600");
							map.put("2000", "2000");

							String hlist = HTMLUtility.getList("ProductPrice", String.valueOf(bean.getProductPrice()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("ProductPrice", request)%></font></td> --%>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				
				<tr>
					<th align="left">Purchase Date <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dob" id="udatee"
						placeholder="Enter Purchase Date" size="25"
						value="<%=DataUtility.getStringData(bean.getDob())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Product Price <span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("ProductPrice", String.valueOf(bean.getProductPrice()), rlist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("ProductPrice", request)%></font></td>
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
						name="operation" value="<%=ShoppingCtl.OP_UPDATE%>">
						&nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=ShoppingCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ShoppingCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ShoppingCtl.OP_RESET%>"></td>

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