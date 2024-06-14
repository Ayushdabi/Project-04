package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.DoctarBean;
import com.rays.pro4.Bean.LeadBean;
import com.rays.pro4.Bean.PatientBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Util.JDBCDataSource;

public class LeadModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_lead");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}
	public long add(LeadBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_lead values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setDate(2, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(3, bean.getContactName());
		pstmt.setString(4, bean.getMobile());
		pstmt.setString(5, bean.getStatus());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}
	public void delete(LeadBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_lead where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}
	public void update(LeadBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn
				.prepareStatement("update st_lead set Dob = ?, ContactName= ?,Mobile = ?, Status = ? where id = ?");

		pstmt.setDate(1, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(2, bean.getContactName());
		pstmt.setString(3, bean.getMobile());
		pstmt.setString(4, bean.getStatus());

		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Lead update successfully " + i);

		conn.commit();
		pstmt.close();

	}
	public LeadBean findByPK(long pk) throws Exception {

		String sql = "select * from st_lead where id = ?";
		LeadBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new LeadBean();
			bean.setId(rs.getLong(1));
			bean.setDob(rs.getDate(2));
			bean.setContactName(rs.getString(3));
			bean.setMobile(rs.getString(4));
			bean.setStatus(rs.getString(5));

		}

		rs.close();

		return bean;
	}

	public List search(LeadBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_lead where 1=1");
		if (bean != null) {

			
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
			}
			if (bean.getContactName() != null && bean.getContactName().length() > 0) {
				sql.append(" AND ContactName like '" + bean.getContactName() + "%'");
			}

			if (bean.getMobile() != null && bean.getMobile().length() > 0) {
				// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND Mobile like '" + bean.getMobile() + "%'");
			}
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND Status like '" + bean.getStatus() + "%'");
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

			bean = new LeadBean();
			bean.setId(rs.getLong(1));
			bean.setDob(rs.getDate(2));
			bean.setContactName(rs.getString(3));
			bean.setMobile(rs.getString(4));
			bean.setStatus(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}
	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_lead");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			LeadBean bean = new LeadBean();
			bean.setId(rs.getLong(1));
			bean.setDob(rs.getDate(2));
			bean.setContactName(rs.getString(3));
			bean.setMobile(rs.getString(4));
			bean.setStatus(rs.getString(5));
			list.add(bean);

		}

		rs.close();

		return list;
	}


	}



