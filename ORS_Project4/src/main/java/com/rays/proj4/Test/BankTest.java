package com.rays.proj4.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Model.UserModel;

public class BankTest {
	public static void main(String[] args)  throws Exception{
		//testadd();//
		testdelete();
		testsearch();
		
	}

	private static void testsearch() throws Exception {
		try {
			OrderBean bean = new OrderBean();
			OrderModel model = new OrderModel();
			List list = new ArrayList();
			// bean.setFirstName("Roshani");
			// bean.setLastName("Bandhiye");
			// bean.setLogin("banti@gmail.com");
			// bean.setId(8L);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (OrderBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getOrder_Name());
				System.out.println(bean.getOrder_Status());
				System.out.println(bean.getOrder_Price());
				System.out.println(bean.getDob());
				
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	private static void testdelete()  throws Exception{
BankModel model = new BankModel();
 BankBean Bean = new BankBean();
   Bean.setId(3);
   model.delete(Bean);
   
		
		
		
	}

	private static void testadd()  throws Exception{
		 SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-yyyy");
		 
		
		BankBean bean = new BankBean();
		bean.setBank_Name("SBI");
		bean.setAccount_NO("KKK");
		bean.setCustomer_Name("Abhishek");
		bean.setDob(sdf.parse("12-2-2005"));
		BankModel model = new BankModel();
		 model.add(bean);
		 
	}

}
