package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.SupplierBean;
import com.rays.pro4.Bean.TransportationBean;
import com.rays.pro4.Util.JDBCDataSource;

public class SupplierModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_supplier");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(SupplierBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_supplier values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getName());
		pstmt.setString(3, bean.getCategory());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setInt(5, bean.getPaymentTerm());

		int i = pstmt.executeUpdate();
		System.out.println("Contact Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(SupplierBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_supplier where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("contact delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(SupplierBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn
				.prepareStatement("update st_supplier set Name = ?, Category= ?, Dob= ?, PaymentTerm = ? where id = ?");

		pstmt.setString(1, bean.getName());
		pstmt.setString(2, bean.getCategory());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setInt(4, bean.getPaymentTerm());
		pstmt.setLong(5, bean.getId());

		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Contact update successfully " + i);

		conn.commit();
		pstmt.close();

	}
	public SupplierBean findByPK(long pk) throws Exception {

		String sql = "select * from st_supplier where id = ?";
		SupplierBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new SupplierBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setCategory(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setPaymentTerm(rs.getInt(5));
		}

		rs.close();

		return bean;
	}
	public List search(SupplierBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_supplier where 1=1");
		if (bean != null) {

			
			
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND Name like '" + bean.getName() + "%'");
			}
			if (bean.getCategory() != null && bean.getCategory().length() > 0) {
				sql.append(" AND Category like '" + bean.getCategory() + "%'");
			}

			
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
			}

			if (bean.getPaymentTerm() != null && bean.getPaymentTerm() > 0) {
				  
				sql.append(" AND PaymentTerm = '" + bean.getPaymentTerm());
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

			bean = new SupplierBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setCategory(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setPaymentTerm(rs.getInt(5));
			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_supplier");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			SupplierBean bean = new SupplierBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setCategory(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setPaymentTerm(rs.getInt(5));
		
			list.add(bean);

		}

		rs.close();

		return list;
}
}
