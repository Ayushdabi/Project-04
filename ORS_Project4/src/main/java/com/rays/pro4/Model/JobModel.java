package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Bean.JobBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Util.JDBCDataSource;

public class JobModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_job");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(JobBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_job values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getTitle());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(4, bean.getExperience());
		pstmt.setString(5, bean.getStatus());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(JobBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_job where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("employee delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(JobBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn
				.prepareStatement("update st_job set Title = ?,Dob = ?, Experience = ? , Status=? where id = ?");

		pstmt.setString(1, bean.getTitle());
		pstmt.setDate(2, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(3, bean.getExperience());
		pstmt.setString(4, bean.getStatus());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("employee update successfully " + i);

		conn.commit();
		pstmt.close();

	}
	public JobBean findByPK(long pk) throws Exception {

		String sql = "select * from st_job where id = ?";
		JobBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new JobBean();
			bean.setId(rs.getLong(1));
			bean.setTitle(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setExperience(rs.getString(4));
			bean.setStatus(rs.getString(5));

		}
		rs.close();
		return bean;
	}
	public List search(JobBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select *from st_job  where 1=1");
		if (bean != null) {

			if (bean.getTitle() != null && bean.getTitle().length() > 0) {
				sql.append(" AND Title like '" + bean.getTitle() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
			}

			if (bean.getExperience() != null && bean.getExperience().length() > 0) {
				// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND Experience like '" + bean.getExperience() + "%'");
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

			bean = new JobBean();
			bean.setId(rs.getLong(1));
			bean.setTitle(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setExperience(rs.getString(4));
			bean.setStatus(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}
	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_job");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			JobBean bean = new JobBean();
			bean.setId(rs.getLong(1));
			bean.setTitle(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setExperience(rs.getString(4));
			bean.setStatus(rs.getString(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}
}
