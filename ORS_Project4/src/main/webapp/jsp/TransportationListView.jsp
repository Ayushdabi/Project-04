<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.TransportationListCtl"%>
<%@page import="com.rays.pro4.Bean.TransportationBean"%>
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

<title>Contact List</title>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.TransportationBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.TRANSPORTATION_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>Transprotation List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
				List rlist = (List) request.getAttribute("prolist");
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<TransportationBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>

					<%-- <%=HTMLUtility.getList("Mode",String.valueOf(bean.getMode()),proo)%> --%>
					<th></th>
					<td align="center"><label>Date</font> :
					</label> <input type="text" name="Dob" id="udate"
						placeholder="Enter Date of birth" readonly="readonly"
						value="<%=ServletUtility.getParameter("Dob", request)%>">
					<td align="center"><label>Description</font> :
					</label> <input type="text" name="Description"
						placeholder="Enter Description"
						value="<%=ServletUtility.getParameter("Description", request)%>">
					<td align="center"><label>Cost</font> :
					</label> <input type="text" name="Cost" placeholder="Enter Cost"
						value="<%=ServletUtility.getParameter("Cost", request)%>">



						<label>Mode</label> <%=HTMLUtility.getList("Mode", String.valueOf(bean.getMode()), rlist)%>


						<input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=TransportationListCtl.OP_RESET%>"></td>

				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: Pink">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Description</th>
					<th>Mode</th>
					<th>Dob</th>
					<th>Cost</th>
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
					<td><%=bean.getDescription()%></td>
					<td><%=bean.getMode()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=bean.getCost()%></td>
					<td><a href="TransportationCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=TransportationListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=TransportationListCtl.OP_BACK%>"></td>
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