<%@page import="com.rays.pro4.controller.CarListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.CarBean"%>
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

<title>Product List</title>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.CarBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.CAR_LIST_CTL%>" method="post">

		<center>

			<div align="center">
				<h1>Car List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
				List proList = (List) request.getAttribute("proList");
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<CarBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
			
			
			
			 <td align="center"><label>Purchase Date</font> :
				</label> <input type="" name="Dob" id="udate"
					placeholder="Enter Puerchase Date"
					value="<%=ServletUtility.getParameter("Dob", request)%>">  
					 

				<th></th>
				<td align="center"><label>Owner Name</font> :
				</label> <input type="text" name="OwnerName" placeholder="Enter Owner Name"
					value="<%=ServletUtility.getParameter("OwnerName", request)%>">
					
				 <td align="center"><label>Car Name</font> :
				</label> <input type="text" name="CarName" placeholder="Enter Car Name"
					value="<%=ServletUtility.getParameter("CarName", request)%>">
					
					<td align="center"><label>CarPrice</font> :
				</label> <input type="text" name="CarPrice" placeholder="Enter Car Price"
					value="<%=ServletUtility.getParameter("CarPrice", request)%>">
 
 

					
					 
					  &emsp; <label>Purchase Date</font> :
				</label> <%=HTMLUtility.getList("proList", String.valueOf(bean.getDob()), proList)%> 
					&nbsp; <input type="submit" name="operation"
					value="<%=CarListCtl.OP_SEARCH%>"> &nbsp; <input
					type="submit" name="operation" value="<%=CarListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: Silver">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Owner Name</th>
					<th>Car Name</th>
					<th>Car price</th>
					<th>Purchase Date</th>


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
					<td><%=bean.getOwnerName()%></td>
					<td><%=bean.getCarName()%></td>
					<td><%=bean.getCarPrice()%></td>
					<td><%=bean.getDob()%></td>
					<td><a href="CarCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=CarListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=CarListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=CarListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=CarListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=CarListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=CarListCtl.OP_BACK%>"></td>
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