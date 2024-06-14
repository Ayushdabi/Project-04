package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.PriscriptionBean;
import com.rays.pro4.Bean.UsersBean;
import com.rays.pro4.Util.JDBCDataSource;

public class UsersModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from users");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(UsersBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into users values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getUserName());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(4, bean.getLogin());
		pstmt.setString(5, bean.getPass());

		int i = pstmt.executeUpdate();
		System.out.println("Capicity Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(UsersBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from users where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(UsersBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn
				.prepareStatement("update users set UserName=?,Dob=?,login=?,pass=? where id = ?");

		pstmt.setString(1, bean.getUserName());
		pstmt.setDate(2, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(3, bean.getLogin());
		pstmt.setString(4, bean.getPass());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Capicity update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public UsersBean findByPK(long pk) throws Exception {

		String sql = "select * from users where id = ?";
		UsersBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new UsersBean();
			bean.setId(rs.getLong(1));
			bean.setUserName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setLogin(rs.getString(4));
			bean.setPass(rs.getString(5));

		}

		rs.close();

		return bean;
	}

	public List search(UsersBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from users where 1=1");
		if (bean != null) {

			if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" AND UserName like '" + bean.getUserName() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND Login like '" + bean.getLogin() + "%'");
			}
			if (bean.getPass() != null && bean.getPass().length() > 0) {
				sql.append(" AND Pass like '" + bean.getPass() + "%'");
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

			System.out.println("in while loop");

			bean = new UsersBean();
			bean.setId(rs.getLong(1));
			bean.setUserName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setLogin(rs.getString(4));
			bean.setPass(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from users");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			UsersBean bean = new UsersBean();

			bean.setId(rs.getLong(1));
			bean.setUserName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setLogin(rs.getString(4));
			bean.setPass(rs.getString(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}

}
