<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.ClientCtl"%>
<%@page import="com.rays.pro4.controller.ClientCtl"%>
<%@page import="com.mysql.cj.xdevapi.Client"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Product Page</title>

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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ClientBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.CLIENT_CTL%>" method="post">

			<div align="center">
				<h1>
					<%
						List rlist = (List) request.getAttribute("rlist");
					%>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update client </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add client </font></th>
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
					<th align="left">Client Name<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="ClientName"
						placeholder="Enter Client Name" size="25"
						value="<%=DataUtility.getStringData(bean.getClientName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("ClientName", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>


				<tr>
					<th align="left">Address<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="Address"
						placeholder="Enter  Address" size="25"
						value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Address", request)%></font></td>
				</tr>


				
				<tr>
					<th align="left">Phone <span style="color: red">*</span> :
					</th>

					<td><input type="number" name="Phone"
						placeholder="Enter Phone" maxlength="10" style="width: 195px"
						value="<%=DataUtility.getStringData(bean.getPhone())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("Phone", request)%></font></td>

					</th>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">Priortiy<span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();

							map.put("2nd", "2nd");
							map.put("3rd", "3rd");
							map.put("1st", "1st");

							String hlist = HTMLUtility.getList("Priortiy", String.valueOf(bean.getPriortiy()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Priortiy", request)%></font></td>
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
						name="operation" value="<%=ClientCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ClientCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ClientCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ClientCtl.OP_RESET%>"></td>

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