package pos.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MembersDao {
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

	public int updatePoint(String phone, int sumPrice) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "update member m1, member m2\r\n" + 
					"set m1.point = m2.point + ?\r\n" + 
					"where m1.phone = ?";
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setDouble(1, (sumPrice*0.1));
				ps.setString(2, phone);
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
	
	public ResultSet selectAllMem() {
		getConn();
		if (conn != null) {
			String sql = "select memberId, name, phone, point from member";
			
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

	public int insertMem(String name, String phone) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "insert into member (name, phone) values\r\n" + 
					"		(?,?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, phone);
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

	public int deleteMem(String memId) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "delete from member where memberId = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, memId);
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

	public int updateMem(String memId,String name, String phone) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "update member\r\n" + 
					"set name = ?, phone = ?\r\n" + 
					"where memberId = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, name);				
				ps.setString(2, phone);
				ps.setInt(3, Integer.parseInt(memId));
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

	public int selectPhone(String phone) {
		getConn();
		int buyer=0;
		if (conn != null) {
			String sql = "select memberId from member where phone = ?";
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, phone);
				rs = ps.executeQuery();
				rs.next();
				buyer = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return buyer;
		} else {
			return (Integer) null;
		}
		
	}
}
