package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.ShoppingBean;
import com.rays.pro4.Util.JDBCDataSource;

public class ShoppingModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_Shopping");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}


	public long add(ShoppingBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_shopping values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getProductName());
		pstmt.setString(3, bean.getShopName());
		pstmt.setString(4, bean.getProductPrice());
		pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
		

		int i = pstmt.executeUpdate();
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(ShoppingBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_shopping where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("shopping delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(ShoppingBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_Shopping set ProductName = ?, ShopName = ?, ProductPrice = ?, Dob = ? where id = ?");

		pstmt.setString(1, bean.getProductName());
		pstmt.setString(2, bean.getShopName());
		pstmt.setString(3, bean.getProductPrice());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("employee update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public ShoppingBean findByPK(long pk) throws Exception {

		String sql = "select * from st_Shopping where id = ?";
		ShoppingBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new ShoppingBean();
			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setShopName(rs.getString(3));
			bean.setProductPrice(rs.getString(4));
			bean.setDob(rs.getDate(5));

		}
		rs.close();
		return bean;
	}

	public List search(ShoppingBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select *from st_Shopping  where 1=1");
		if (bean != null) {

			if (bean.getProductName() != null && bean.getProductName().length() > 0) {
				sql.append(" AND ProductName like '" + bean.getProductName() + "%'");
			}

			if (bean.getShopName() != null && bean.getShopName().length() > 0) {
				// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND ShopName like '" + bean.getShopName() + "%'");
			}

			if (bean.getProductPrice() != null && bean.getProductPrice().length() > 0) {
				sql.append(" AND ProductPrice like '" + bean.getProductPrice() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
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

			bean = new ShoppingBean();
			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setShopName(rs.getString(3));
			bean.setProductPrice(rs.getString(4));
			bean.setDob(rs.getDate(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_Shopping");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			ShoppingBean bean = new ShoppingBean();
			bean.setId(rs.getLong(1));
			bean.setProductName(rs.getString(2));
			bean.setShopName(rs.getString(3));
			bean.setProductPrice(rs.getString(4));
			bean.setDob(rs.getDate(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}

}
