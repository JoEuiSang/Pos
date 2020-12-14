package pos.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeesDao {
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

	public ResultSet selectAllEmp() {
		getConn();
		if (conn != null) {
			String sql = "select name, positionName, empId\r\n" + "from emp e,positiontb p\r\n"
					+ "where e.positionCode = p.positionCode";
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

	public int insertEmp(String name, String pw, String position) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "insert into emp (name, positionCode, pw) values\r\n" + 
					"		(?,?,?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setInt(2, Integer.parseInt(position));
				ps.setString(3, pw);
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

	public int deleteEmp(String empId) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "delete from emp where empId = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, empId);
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

	public int updateEmp(String empId,String pw, String position) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "update emp\r\n" + 
					"set pw = ?, positionCode = ?\r\n" + 
					"where empid = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, pw);				
				ps.setInt(2, Integer.parseInt(position));
				ps.setInt(3, Integer.parseInt(empId));
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
