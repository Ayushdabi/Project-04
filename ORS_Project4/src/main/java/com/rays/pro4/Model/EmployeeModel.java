package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Util.JDBCDataSource;

public class EmployeeModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_emoloyees");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(EmployeeBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_emoloyees values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getEmployeeName());
		pstmt.setString(3, bean.getDepartment());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(5, bean.getLastName());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(EmployeeBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_emoloyees where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("employee delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(EmployeeBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_emoloyees set employeeName = ?, Department = ?,Dob = ?, LastName = ? where id = ?");

		pstmt.setString(1, bean.getEmployeeName());
		pstmt.setString(2, bean.getDepartment());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(4, bean.getLastName());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("employee update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public EmployeeBean findByPK(long pk) throws Exception {

		String sql = "select * from st_emoloyees where id = ?";
		EmployeeBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new EmployeeBean();
			bean.setId(rs.getLong(1));
			bean.setEmployeeName(rs.getString(2));
			bean.setDepartment(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setLastName(rs.getString(5));

		}
		rs.close();
		return bean;
	}

	public List search(EmployeeBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select *from st_emoloyees  where 1=1");
		if (bean != null) {

			if (bean.getEmployeeName() != null && bean.getEmployeeName().length() > 0) {
				sql.append(" AND EmployeeName like '" + bean.getEmployeeName() + "%'");
			}

			if (bean.getDepartment() != null && bean.getDepartment().length() > 0) {
				// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND Department like '" + bean.getDepartment() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
			}

			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LastName like '" + bean.getLastName() + "%'");
			}

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new EmployeeBean();
			bean.setId(rs.getLong(1));
			bean.setEmployeeName(rs.getString(2));
			bean.setDepartment(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setLastName(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_emoloyees");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			EmployeeBean bean = new EmployeeBean();
			bean.setId(rs.getLong(1));
			bean.setEmployeeName(rs.getString(2));
			bean.setDepartment(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setLastName(rs.getString(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}

}
