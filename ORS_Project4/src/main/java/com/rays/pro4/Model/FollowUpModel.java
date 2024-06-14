package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.DoctarBean;
import com.rays.pro4.Bean.FollowUpBean;
import com.rays.pro4.Util.JDBCDataSource;

public class FollowUpModel {

	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_followup");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(FollowUpBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_followup values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getPatient());
		pstmt.setString(3, bean.getDoctor());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(5, bean.getFess());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(FollowUpBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_followup where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Follow delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(FollowUpBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn
				.prepareStatement("update st_followup set Patient = ?, Doctor = ?,Dob = ?, Fess = ? where id = ?");

		pstmt.setString(1, bean.getPatient());
		pstmt.setString(2, bean.getDoctor());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(4, bean.getFess());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("product update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public FollowUpBean findByPK(long pk) throws Exception {

		String sql = "select * from st_followup where id = ?";
		FollowUpBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new FollowUpBean();
			bean.setId(rs.getLong(1));
			bean.setPatient(rs.getString(2));
			bean.setDoctor(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setFess(rs.getString(5));

		}

		rs.close();

		return bean;
	}

	public List search(FollowUpBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_followup where 1=1");
		if (bean != null) {

			if (bean.getPatient() != null && bean.getPatient().length() > 0) {
				sql.append(" AND Patient like '" + bean.getPatient() + "%'");
			}
			

			if (bean.getDoctor() != null && bean.getDoctor().length() > 0) {
				// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND Doctor like '" + bean.getDoctor() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
			}
			if (bean.getFess() != null && bean.getFess().length() > 0) {
				sql.append(" AND Fess like '" + bean.getFess() + "%'");
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

			bean = new FollowUpBean();
			bean.setId(rs.getLong(1));
			bean.setPatient(rs.getString(2));
			bean.setDoctor(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setFess(rs.getString(5));


			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_followup");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			FollowUpBean bean = new FollowUpBean();
			bean.setId(rs.getLong(1));
			bean.setPatient(rs.getString(2));
			bean.setDoctor(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setFess(rs.getString(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}
}
