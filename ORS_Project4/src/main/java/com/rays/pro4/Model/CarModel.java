package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.CarBean;
import com.rays.pro4.Bean.EmployeeBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Util.JDBCDataSource;

public class CarModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_car");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(CarBean bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_car values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getOwnerName());
		pstmt.setString(3, bean.getCarName());
		pstmt.setString(4, bean.getCarPrice());
		pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
		

		int i = pstmt.executeUpdate();
		System.out.println("Car Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(CarBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_car where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Car delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(CarBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_Car set OwnerName = ?, CarName = ?,CarPrice = ?, Dob = ? where id = ?");

		pstmt.setString(1, bean.getOwnerName());
		pstmt.setString(2, bean.getCarName());
		pstmt.setString(3, bean.getCarPrice());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Car update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public CarBean findByPK(long pk) throws Exception {

		String sql = "select * from st_car where id = ?";
		CarBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new CarBean();
			bean.setId(rs.getLong(1));
			bean.setOwnerName(rs.getString(2));
			bean.setCarName(rs.getString(3));
			bean.setCarPrice(rs.getString(4));
			bean.setDob(rs.getDate(5));

		}
		rs.close();
		return bean;
	}

	public List search(CarBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select *from st_car  where 1=1");
		if (bean != null) {

			if (bean.getOwnerName()!= null && bean.getOwnerName().length() > 0) {
				sql.append(" AND OwnerName like '" + bean.getOwnerName() + "%'");
			}

			if (bean.getCarName() != null && bean.getCarName().length() > 0) {
				//System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND CarName like '" + bean.getCarName() + "%'");
			}
			if (bean.getCarPrice() != null && bean.getCarPrice().length() > 0) {
				sql.append(" AND CarPrice like '" + bean.getCarPrice() + "%'");
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

			bean = new CarBean();
			bean.setId(rs.getLong(1));
			bean.setOwnerName(rs.getString(2));
			bean.setCarName(rs.getString(3));
			bean.setCarPrice(rs.getString(4));
			bean.setDob(rs.getDate(5));



			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_car");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			 CarBean bean = new CarBean();
			bean.setId(rs.getLong(1));
			bean.setOwnerName(rs.getString(2));
			bean.setCarName(rs.getString(3));
			bean.setCarPrice(rs.getString(4));
			bean.setDob(rs.getDate(5));



			list.add(bean);

		}

		rs.close();

		return list;
	}

	
}


	

