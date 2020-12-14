package pos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	static String URL = "jdbc:mysql://localhost:3306/pos?serverTimezone=UTC";
	static String USER = "";
	static String PASSWORD = "";
	static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	// Á¢¼Ó
	public static Connection getConnection() throws Exception {
		Connection conn = null;
		Class.forName(DRIVER_NAME);
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		return conn;
	}

	// Á¢¼Ó Á¾·á
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Connection conn, PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(conn);
	}

	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(conn, ps);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getConnection());
	}
}
