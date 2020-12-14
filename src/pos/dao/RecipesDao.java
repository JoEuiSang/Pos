package pos.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipesDao {
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

	//각 메뉴에 들어가는 재료코드들 반환
	public String selectRecipeMaterial(String menu) {
		getConn();
		String result="";
		if (conn != null) {
			String sql = "select matCode1,matCode2,matCode3,matCode4,matCode5,matCode6,matCode7,matCode8"
					+ " from recipes, menu where recipes.menuId = ? and recipes.menuId=menu.menuId";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, menu);
				rs = ps.executeQuery();
				if(rs==null) {
					return "";
				}
				rs.next();
				for(int i=1; i<=8; i++) {
					if(rs.getString(i) != null) {
						if(i==1) {
							result += rs.getString(i);
							continue;
						}
						result += ","+rs.getString(i);								
					}
				}
				System.out.println("재료리스트"+result);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return result;
		} else {
			return "";
		}

	}

	public int insertRecipe(int menuId, String _materArr) {
		getConn();
		int count =0;
		String materArr = _materArr.substring(1);
		String[] materList = materArr.split(",");
		ArrayList<String> materList1 = new ArrayList<String>();
		
		String column="(menuId,";
		String value="("+menuId+",";
		
		for(int i=1; i<=materList.length; i++) {
			materList1.add(materList[i-1]);
			if(i==1) {
				column += "matCode1";
				value +="?";
				continue;
			}
			column += ",matcode"+i;
			value+=",?";
		}
		column+=")";
		value+=")";
		 
		System.out.println(column);
		System.out.println(value);
		
		if (conn != null) {
			String sql = "insert into recipes"+column+" values" + value;
			System.out.println(sql);
			try {
				ps = conn.prepareStatement(sql);
				for(int i=0; i< materList1.size(); i++) {
					System.out.println(materList1.get(i));
					ps.setString(i+1, materList1.get(i));	
				}
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


	public int useMtr(String materArr) {
		getConn();
		int count =0;
		System.out.println("재료리스트트트"+materArr);
		if (conn != null) {			
			String sql = "update material m1, material m2 " + 
					"set m1.stock = m2.stock-1 where m1.materialCode in ("+materArr+")"
							+ "and m1.materialcode=m2.materialcode";
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
	
	public void deleteRecipe(int menuId) {
		getConn();
		int count =0;
		if (conn != null) {
			String sql = "delete from recipes where menuID = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setInt(1, menuId);
				count = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}			
		} 
		if(count ==1) {
			System.out.println("레시피삭제성공");
		}
	}
	
}
