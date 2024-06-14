<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.FollowUpListCtl"%>
<%@page import="com.rays.pro4.Bean.FollowUpBean"%>
<%@page import="java.util.Iterator"%>
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

<title>FollowUp List</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

<link rel="stylesheet" href="/resources/demos/style.css">
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

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.FollowUpBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.FOLLOWUP_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>FollowUp List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
				List rlist = (List) request.getAttribute("Clist");
			// Initialize the lists if they are null
				
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<FollowUpBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>
					<td align="right"><label>Date of birth</font> :
					</label> <input type="text" name="Dob" id="udate" readonly="readonly"
						placeholder="Enter Date of Birth"
						value="<%=ServletUtility.getParameter("Dob", request)%>">
					<td align="left"><label>Fess</font> :
					</label> <input type="number" name="Fess" placeholder="Enter Fess"
						value="<%=ServletUtility.getParameter("Fess", request)%>">




						&emsp; <label>Patient</font> :
					</label> <%=HTMLUtility.getList("Patient", String.valueOf(bean.getPatient()), rlist)%>
					
					
					
						&emsp; <label>Doctor</font> :	</label>
						<%
							HashMap map = new HashMap();
						map.put("Dr.Vikash", "Dr.Vikash");
						map.put("Dr.Kanak", "Dr.Kanak");
						map.put("Dr.Raj", "Dr.Raj");
						map.put("Dr.Abhi", "Dr.Abhi");

							String hlist = HTMLUtility.getList("Doctor", String.valueOf(bean.getDoctor()), map);
						%> <%=hlist%></td>
					
				
				<td align="right">
						<input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_RESET%>"></td>

				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: Pink">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Patient</th>
					<th>Doctor</th>
					<th>Dob</th>
					<th>Fess</th>
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
					<td><%=bean.getPatient()%></td>
					<td><%=bean.getDoctor()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=bean.getFess()%></td>
					<td><a href="FollowUpCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=FollowUpListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=FollowUpListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=FollowUpListCtl.OP_BACK%>"></td>
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