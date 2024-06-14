package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Util.JDBCDataSource;

public class ClientModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_client");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(ClientBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_client values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getClientName());
		pstmt.setString(3, bean.getAddress());
		pstmt.setString(4, bean.getPhone());
		pstmt.setString(5, bean.getPriortiy());

		int i = pstmt.executeUpdate();
		System.out.println("Client Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(ClientBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_client where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("client delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(ClientBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_client set ClientName = ?, Address = ?,Phone = ?, Priortiy = ? where id = ?");

		pstmt.setString(1, bean.getClientName());
		pstmt.setString(2, bean.getAddress());
		pstmt.setString(3, bean.getPhone());
		pstmt.setString(4, bean.getPriortiy());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("client update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public ClientBean findByPK(long pk) throws Exception {

		String sql = "select * from st_client where id = ?";
		ClientBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new ClientBean();
			bean.setId(rs.getLong(1));
			bean.setClientName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setPhone(rs.getString(4));
			bean.setPriortiy(rs.getString(5));

		}

		rs.close();

		return bean;
	}

	public List search(ClientBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select *from st_client where 1=1");
		if (bean != null) {

			if (bean.getClientName() != null && bean.getClientName().length() > 0) {
				sql.append(" AND ClientName like '" + bean.getClientName() + "%'");
			}

			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND Address like '" + bean.getAddress() + "%'");
			}

			if (bean.getPhone() != null && bean.getPhone().length() > 0) {
				sql.append(" AND Phone like '" + bean.getPhone() + "%'");
			}

			if (bean.getPriortiy() != null && bean.getPriortiy().length() > 0) {
				// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND Priortiy like '" + bean.getPriortiy() + "%'");
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

			bean = new ClientBean();
			bean.setId(rs.getLong(1));
			bean.setClientName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setPhone(rs.getString(4));
			bean.setPriortiy(rs.getString(5));
			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_client");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			ClientBean bean = new ClientBean();
			bean.setId(rs.getLong(1));
			bean.setClientName(rs.getString(2));
			bean.setAddress(rs.getString(3));
			bean.setPhone(rs.getString(4));
			bean.setPriortiy(rs.getString(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}

}
