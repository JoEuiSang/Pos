package pos.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenusDao {
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

	public ResultSet selectAllMenu() {
		getConn();
		if (conn != null) {
			String sql = "select kind, menuName, price, menuid from menu order by kind, price";
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

	public int insertMenu(String kind, String menuName, String price) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "insert into menu (kind, menuName, price) values (?, ?, ?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, kind);
				ps.setString(2, menuName);
				ps.setInt(3, Integer.parseInt(price));
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

	public int deleteMenu(String menuNum) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "delete from menu where menuId = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, menuNum);
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

	public int updateMenu(String menuName, String price) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "update menu\r\n" + 
					"set price = ? where menuName = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(price));
				ps.setString(2, menuName);
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

	public ResultSet selectMenu(String menuName) {
		getConn();
		if (conn != null) {
			String sql = "select menuid from menu where menuName = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, menuName);
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
}
