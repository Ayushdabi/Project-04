package com.rays.pro4.Model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rays.pro4.Bean.PaymentBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class PaymentModel {
	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM PAYMENT";
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

	public long add(PaymentBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO PAYMENT VALUES(?,?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getA_Name());
			pstmt.setString(3, bean.getAccount());
			pstmt.setString(4, bean.getUpi_id());
			pstmt.setString(5, bean.getTotal_Amount());
			pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));

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

	public void delete(PaymentBean bean) throws ApplicationException {

		String sql = "DELETE FROM PAYMENT WHERE ID=?";
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

	public void update(PaymentBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE PAYMENT SET A_Name=?,ACCOUNT=?,UPI_ID=?,TOTAL_AMMOUNT=?,DOB=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getA_Name());
			pstmt.setString(2, bean.getAccount());
			pstmt.setString(3, bean.getUpi_id());
			pstmt.setString(4, bean.getTotal_Amount());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setLong(6, bean.getId());

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

	public List search(PaymentBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(PaymentBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT *FROM PAYMENT WHERE 1=1");
		if (bean != null) {
			if (bean != null && bean.getId() > 0) {

				sql.append(" AND id = " + bean.getId());

			}
			if (bean.getA_Name() != null && bean.getA_Name().length() > 0) {
				sql.append(" AND A_name like '" + bean.getA_Name() + "%'");
			}

			if (bean.getAccount() != null && bean.getAccount().length() > 0) {
				sql.append(" AND Account like '" + bean.getAccount() + "%'");
			}

			if (bean.getUpi_id() != null && bean.getUpi_id().length() > 0) {
				sql.append(" AND Upi_id like '" + bean.getUpi_id() + "%'");
			}

			if (bean.getTotal_Amount() != null && bean.getTotal_Amount().length() > 0) {
				sql.append(" AND Total_Ammount like '" + bean.getTotal_Amount() + "%'");
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
				bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setA_Name(rs.getString(2));
				bean.setAccount(rs.getString(3));
				bean.setUpi_id(rs.getString(4));
				bean.setTotal_Amount(rs.getString(5));
				bean.setDob(rs.getDate(6));

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

	public PaymentBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM PAYMENT WHERE ID=?";
		PaymentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setA_Name(rs.getString(2));
				bean.setAccount(rs.getString(3));
				bean.setUpi_id(rs.getString(4));
				bean.setTotal_Amount(rs.getString(5));
				bean.setDob(rs.getDate(6));

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
		StringBuffer sql = new StringBuffer("select * from Payment");

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
				PaymentBean bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setA_Name(rs.getString(2));
				bean.setAccount(rs.getString(3));
				bean.setUpi_id(rs.getString(4));
				bean.setTotal_Amount(rs.getString(5));
				bean.setDob(rs.getDate(6));

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
