package pos.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagementDao {
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

	public ResultSet selectMgr(String id, String pw) {
		this.getConn();
		if (conn != null) {
			String sql = "select name, positionCode from emp where empid = ? and pw = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(id));
				ps.setString(2, pw);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}					
			
			return rs;
		} else {
			return null;
		}
	}

	public void managementStar() {
		System.out.println("\n2.\n\n\n");
	}
}
