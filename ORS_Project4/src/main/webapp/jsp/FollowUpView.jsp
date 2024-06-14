<%@page import="com.rays.pro4.controller.FollowUpCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>FollowUP Page</title>

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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.FollowUpBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.FOLLOWUP_CTL%>" method="post">

			<div align="center">
				<h1>
					<%
						List plist = (List) request.getAttribute("pro");
					%>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update FollOwUp </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add FollOwUp </font></th>
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
					<th align="left">Patient <span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Sugar", "Sugar");
							map.put("Heart", "Heart");
							map.put("Cancer", "Cancer");
							map.put("Flu", "Flu");

							String hlist = HTMLUtility.getList("Patient", String.valueOf(bean.getPatient()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Patient", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Doctor <span style="color: red">*</span> :
					</th>
				
<td>
						<%
							HashMap map1 = new HashMap();
							map1.put("Dr.Vikash", "Dr.Vikash");
							map1.put("Dr.Kanak", "Dr.Kanak");
							map1.put("Dr.Raj", "Dr.Raj");
							map1.put("Dr.Abhi", "Dr.Abhi");

							String hlist1 = HTMLUtility.getList("Doctor", String.valueOf(bean.getDoctor()), map1);
						%> <%=hlist1%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Doctor", request)%></font></td>
				</tr>

				<%-- <tr>
					<th align="left">Doctor <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("Doctor", String.valueOf(bean.getDoctor()), plist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("Doctor", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
 --%>


				<tr>
					<th align="left">Date of Birth<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Dob"
						placeholder="Enter Date of Birth" size="25" id="udatee"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("Dob", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Fess<span style="color: red">*</span> :
					</th>
					<td><input type="number" name="Fess" placeholder="Enter Fess"
						style="width: 196px" value="<%=DataUtility.getStringData(bean.getFess())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("Fess", request)%></font></td>
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
						name="operation" value="<%=FollowUpCtl.OP_UPDATE%>">
						&nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=FollowUpCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=FollowUpCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=FollowUpCtl.OP_RESET%>"></td>

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