package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.InventoryBean;
import com.rays.pro4.Bean.TransportationBean;
import com.rays.pro4.Util.JDBCDataSource;

public class InventoryModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_inventory");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(InventoryBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_inventory values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getSupplierName());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(4, bean.getQuantity());
		pstmt.setString(5, bean.getProduct());

		int i = pstmt.executeUpdate();
		System.out.println("Contact Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(InventoryBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_inventory where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("contact delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(InventoryBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_inventory set SupplierName = ?, Dob= ?, Quantity= ?, Product = ? where id = ?");

		pstmt.setString(1, bean.getSupplierName());
		pstmt.setDate(2, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(3, bean.getQuantity());
		pstmt.setString(4, bean.getProduct());
		;
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Contact update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public InventoryBean findByPK(long pk) throws Exception {

		String sql = "select * from st_inventory where id = ?";
		InventoryBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new InventoryBean();
			bean.setId(rs.getLong(1));
			bean.setSupplierName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setQuantity(rs.getString(4));
			bean.setProduct(rs.getString(5));
		}

		rs.close();

		return bean;
	}

	public List search(InventoryBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select * from st_inventory where 1=1");
		if (bean != null) {

			if (bean.getSupplierName() != null && bean.getSupplierName().length() > 0) {
				sql.append(" AND SupplierName like '" + bean.getSupplierName() + "%'");
			}
		}

		if (bean.getDob() != null && bean.getDob().getTime() > 0) {
			Date d = new Date(bean.getDob().getTime());
			sql.append(" AND Dob = '" + d + "'");
			System.out.println("done");
		}
		if (bean.getQuantity() != null && bean.getQuantity().length() > 0) {
			sql.append(" AND Quantity like '" + bean.getQuantity() + "%'");
		}
			if (bean.getProduct() != null && bean.getProduct().length() > 0) {
				// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND Product like '" + bean.getProduct() + "%'");
			}

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
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

			bean = new InventoryBean();
			bean.setId(rs.getLong(1));
			bean.setSupplierName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setQuantity(rs.getString(4));
			bean.setProduct(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_inventory");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			InventoryBean bean = new InventoryBean();
			bean.setId(rs.getLong(1));
			bean.setSupplierName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setQuantity(rs.getString(4));
			bean.setProduct(rs.getString(5));
			list.add(bean);

		}

		rs.close();

		return list;
	}
}
