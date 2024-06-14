package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.List;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.PaymentBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class OrderModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM St_Order";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(OrderBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO St_Order VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getOrder_Name());
			pstmt.setString(3, bean.getOrder_Status());
			pstmt.setString(4, bean.getOrder_Price());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));

			int a = pstmt.executeUpdate();
			System.out.println("ho gyua re" + a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();

				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void delete(OrderBean bean) throws ApplicationException {

		String sql = "DELETE FROM St_Order WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();
			System.out.println(i + "data deleted");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public void update(OrderBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE St_Order SET Order_Name=?,Order_Status=?,Order_Price=?,DOB=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getOrder_Name());
			pstmt.setString(2, bean.getOrder_Status());
			pstmt.setString(3, bean.getOrder_Price());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();
			int i = pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(BankBean bean) throws ApplicationException {
		return search(bean);
	}

	public List search(OrderBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT *FROM St_Order WHERE 1=1");
		if (bean != null) {
			if (bean != null && bean.getId() > 0) {

				sql.append(" AND id = " + bean.getId());


			}
			if (bean.getOrder_Name() != null && bean.getOrder_Name().length() > 0) {
				sql.append(" AND Order_Name like '" + bean.getOrder_Name() + "%'");
			}

			if (bean.getOrder_Status() != null && bean.getOrder_Status().length() > 0) {
				sql.append(" AND Order_Status like '" + bean.getOrder_Status() + "%'");
			}

			if (bean.getOrder_Price() != null && bean.getOrder_Price().length() > 0) {
				sql.append(" AND Order_Price like '" + bean.getOrder_Price() + "%'");
			}

			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getDate());
				sql.append(" AND DOB like '" + new java.sql.Date(bean.getDob().getTime()) + "%'");
			}
			

			if (pageSize > 0) {

				pageNo = (pageNo - 1) * pageSize;

				sql.append(" Limit " + pageNo + ", " + pageSize);

			}
		}

		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrder_Name(rs.getString(2));
				bean.setOrder_Status(rs.getString(3));
				bean.setOrder_Price(rs.getString(4));
				bean.setDob(rs.getDate(5));

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

	public OrderBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM St_order WHERE ID=?";
		OrderBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrder_Name(rs.getString(2));
				bean.setOrder_Status(rs.getString(3));
				bean.setOrder_Price(rs.getString(4));
				bean.setDob(rs.getDate(5));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in getting Payment by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from St_Order");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderBean bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrder_Name(rs.getString(2));
				bean.setOrder_Status(rs.getString(3));
				bean.setOrder_Price(rs.getString(4));
				bean.setDob(rs.getDate(5));
				

				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
