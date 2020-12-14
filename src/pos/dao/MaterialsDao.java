package pos.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialsDao {
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

	public ResultSet selectAllMtr() {
		getConn();
		if (conn != null) {
			String sql = "select materialCode, materialName, stock from material";
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

	public int insertMtr(String mtrCode, String name, String stock) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "insert into material values (?,?,?)";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setInt(2, Integer.parseInt(stock));
				ps.setInt(3, Integer.parseInt(mtrCode));
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

	public int deleteMtr(String mtrCode) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "delete from material where materialCode = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, mtrCode);
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

	public int updateMtr(String mtrId, String stock) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "update material\r\n" + 
					"set stock = ? where materialCode = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(stock));
				ps.setInt(2, Integer.parseInt(mtrId));
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

	public int useMtr(String menuArr) {
		getConn();
		int count =0;
		System.out.println(menuArr);
		if (conn != null) {			
			String sql = "update material m1, material m2 \r\n" + 
					"set m1.stock = m2.stock-1 where m1.materialCode in ("+menuArr+")";
			try {
				ps = conn.prepareStatement(sql);
//				ps.setString(1, menuArr);
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

	public int allMtrPlus() {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "update material m1, material m2\r\n" + 
					"set m1.stock = m2.stock+10 where m1.materialcode = m2.materialcode";
			try {
				ps = conn.prepareStatement(sql);
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
