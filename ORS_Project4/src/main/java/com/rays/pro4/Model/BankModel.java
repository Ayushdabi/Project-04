package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class BankModel {

	public int nextPK() throws DatabaseException {

		String sql = "SELECT MAX(ID) FROM Bank";
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

	public long add(BankBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "INSERT INTO BANK VALUES(?,?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getBank_Name());
			pstmt.setString(3, bean.getAccount_NO());
			pstmt.setString(4, bean.getCustomer_Name());
			pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(6, bean.getAddress());

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

	public void delete(BankBean bean) throws ApplicationException {

		String sql = "DELETE FROM BANK WHERE ID=?";
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

	public void update(BankBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "UPDATE BANK SET Bank_Name=?,Account_NO=?,Customer_Name=?,DOB=?,ADDRESS=? WHERE ID=?";
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getBank_Name());
			pstmt.setString(2, bean.getAccount_NO());
			pstmt.setString(3, bean.getCustomer_Name());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getAddress());
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

	public List search(BankBean bean) throws ApplicationException {
		return search(bean);
	}

	public List search(BankBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT *FROM BANK WHERE 1=1");
		if (bean != null) {
			if (bean != null && bean.getId() > 0) {

				sql.append(" AND id = " + bean.getId());

			}
			if (bean.getBank_Name() != null && bean.getBank_Name().length() > 0) {
				sql.append(" AND Bank_Name like '" + bean.getBank_Name() + "%'");
			}

			if (bean.getAccount_NO() != null && bean.getAccount_NO().length() > 0) {
				sql.append(" AND Account_NO like '" + bean.getAccount_NO() + "%'");
			}

			if (bean.getCustomer_Name() != null && bean.getCustomer_Name().length() > 0) {
				sql.append(" AND Customer_Name like '" + bean.getCustomer_Name() + "%'");
			}

			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getDate());
				sql.append(" AND DOB like '" + new java.sql.Date(bean.getDob().getTime()) + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
			System.out.println(">>>>>>>>"+bean.getAddress());
				sql.append(" AND Address like '" + bean.getAddress() + "%'");
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
				bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBank_Name(rs.getString(2));
				bean.setAccount_NO(rs.getString(3));
				bean.setCustomer_Name(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setAddress(rs.getString(6));

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
	public BankBean findByPK(long pk) throws ApplicationException {

		String sql = "SELECT * FROM BANK WHERE ID=?";
		BankBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBank_Name(rs.getString(2));
				bean.setAccount_NO(rs.getString(3));
				bean.setCustomer_Name(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setAddress(rs.getString(6));

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
		StringBuffer sql = new StringBuffer("select * from Bank");

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
				BankBean bean = new BankBean();
				bean.setId(rs.getLong(1));
				bean.setBank_Name(rs.getString(2));
				bean.setAccount_NO(rs.getString(3));
				bean.setCustomer_Name(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setAddress(rs.getString(6));


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
