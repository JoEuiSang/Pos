package pos.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pos.svc.MaterialsSvc;

public class OrdersDao {
	MembersDao memDao = new MembersDao();
	MaterialsSvc mtrSvc = new MaterialsSvc();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public Connection getConn() {

		try {
			conn = DBUtil.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (conn != null) {
			return conn;
		} else {
			return null;
		}

	}

	public ResultSet selectAllOrder() {
		getConn();
		if (conn != null) {
			String sql = "select * from orderHistory order by orderNum";
			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return rs;
		} else {
			return null;
		}
	}
	
	public ResultSet selectEachOrder(int buyerId) {
		getConn();
		if (conn != null) {
			String sql = "select * from orderHistory where buyerId = ? order by orderNum";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, buyerId);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return rs;
		} else {
			return null;
		}
	}

	//적립 없이 주문
	public int insertOrder(String orderMenu, int sumPrice) {
		getConn();
		int count =0;
		if (conn != null) {
			mtrSvc.useMtr(orderMenu);
			String sql = "insert into orderHistory (orderMenu, purchaseDate, sumPrice) values\r\n" + 
					"		(?,sysdate(), ?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, orderMenu);		
				ps.setInt(2, sumPrice);
				count = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return count;
		} else {
			return 0;
		}	
	}
	
	//적립 하면서 주문
	public int insertOrder(String phone, String orderMenu, int sumPrice) {
		getConn();
		int count =0;
		int buyerId;
		if (conn != null) {
			memDao.updatePoint(phone,sumPrice);
			mtrSvc.useMtr(orderMenu);
			
			MaterialsDao mtrDao = new MaterialsDao();
			mtrDao.selectAllMtr();
			
			buyerId = memDao.selectPhone(phone);
			String sql = "insert into orderHistory (buyerId, orderMenu, purchaseDate, sumPrice) values\r\n" + 
					"		(?,?,sysdate(),?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, buyerId);
				ps.setString(2, orderMenu);
				ps.setInt(3, sumPrice);
				count = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return count;
		} else {
			return 0;
		}	
	}

	public int deleteOrder(int orderId) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "delete from orderHistory where orderNum = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, orderId);
				count = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return count;
		} else {
			return 0;
		}	
	}

	public int updateOrder(String orderId,String pw, String position) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "update order\r\n" + 
					"set pw = ?, positionCode = ?\r\n" + 
					"where orderid = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, pw);				
				ps.setInt(2, Integer.parseInt(position));
				ps.setInt(3, Integer.parseInt(orderId));
				count = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return count;
		} else {
			return 0;
		}	
	}
}
