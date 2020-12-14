package pos.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pos.SessionInfo;
import pos.dao.MaterialsDao;
import pos.dao.MenusDao;
import pos.dao.RecipesDao;
import pos.dto.MaterialDto;
import pos.dto.MenuDto;

public class MenusSvc {
	MenusDao menuDao = new MenusDao();
	MaterialsDao mtrDao = new MaterialsDao();
	RecipesDao rcpDao = new RecipesDao();
	ResultSet rs = null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	ArrayList<MenuDto> menuList = new ArrayList<MenuDto>();

	public void menuMgrStart() {
		while (true) {
			SessionInfo.printSession();
			String sel = "";
			System.out.println("1.모든메뉴보기\n2.메뉴추가\n3.메뉴삭제\n4.메뉴가격수정\n0.돌아가기");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("모든메뉴보기");
				rs = menuDao.selectAllMenu();
				selectAllMenu(rs);
			} else if (sel.equals("2")) {
				System.out.println("메뉴추가");
				insertMenu();
			} else if (sel.equals("3")) {
				System.out.println("메뉴삭제");
				deleteMenu();
			} else if (sel.equals("4")) {
				System.out.println("메뉴가격수정");
				UpdateMenu();
			} else if (sel.equals("0")) {
				System.out.println("돌아갑니다.");
				break;
			}
		}
	}

	private void UpdateMenu() {
		String menuId = "";
		String price = "";
		int count = 0;
		try {
			System.out.println("수정할 메뉴명을 입력하세요");
			System.out.println("메뉴명 입력");
			menuId = br.readLine();
			System.out.println("수정할 가격 입력");
			price = br.readLine();
			count = menuDao.updateMenu(menuId, price);
		} catch (IOException e) {
			System.out.println("수정 실패");
		}

		if (count == 0) {
			System.out.println("없는 ID 입니다.");
		} else {
			System.out.println(menuId + "이 수정되었습니다.");
		}
	}

	private void deleteMenu() {
		String menuNum = "";
		int count = 0;
		try {
			System.out.println("메뉴을 삭제합니다");
			System.out.println(menuList);
			System.out.println("메뉴번호 입력");
			menuNum = br.readLine();
			rcpDao.deleteRecipe(Integer.parseInt(menuNum));
			count = menuDao.deleteMenu(menuNum);

		} catch (IOException e) {
			System.out.println("삭제 실패");
		}
		if (count == 0) {
			System.out.println("없는 메뉴 입니다.");
		} else {
			System.out.println(menuNum + "이 삭제되었습니다.");
		}
	}

	public void selectAllMenu(ResultSet rs) {
		if (rs != null) {
			String menuName = "";
			int price = 0;
			String kind = "";
			int menuid = 0;

			try {
				menuList.clear();
				while (rs.next()) {
					kind = rs.getString(1);
					menuName = rs.getString(2);
					price = rs.getInt(3);
					menuid = rs.getInt(4);
					menuList.add(new MenuDto(menuName,price,kind,menuid));
					System.out.println(
							"번호 : " + menuid + " \t종류 : " + kind + " \t메뉴명 : " + menuName + " \t가격 : " + price);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("메뉴 정보가 없습니다.");
		}
	}

	private void insertMenu() {
		String menuName = "";
		String price = "";
		String kind = "";
		int count = 0;

		ArrayList<MaterialDto> materList = new ArrayList<MaterialDto>();
//		ArrayList<Integer> useMaterList = new ArrayList<Integer>();
		String useMaterList = "";
		try {
			rs = mtrDao.selectAllMtr();
			while (rs.next()) {
				materList.add(new MaterialDto(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("메뉴을 추가합니다");
			System.out.println("메뉴종류 입력");
			kind = br.readLine();
			System.out.println("메뉴명 입력");
			menuName = br.readLine();
			System.out.println("가격 입력");
			price = br.readLine();

			while (true) {
				System.out.println(materList);
				System.out.println("메뉴에 사용할 재료 선택(최대 8개)  종료 : 0");
				String sel = br.readLine();
				if (sel.equals("0")) {
					break;
				}
				useMaterList += ","+sel;
				System.out.println("현재 선택");
				System.out.println(useMaterList);
			}

			count = menuDao.insertMenu(kind, menuName, price);
			rs = menuDao.selectMenu(menuName);
			try {
				rs.next();
				RecipesDao rcpDao = new RecipesDao();
				//메뉴id와, 사용하는 재료를 넘겨준다
				rcpDao.insertRecipe(rs.getInt(1), useMaterList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println(menuName + "이 추가되었습니다.");
		} catch (IOException e) {
			System.out.println("추가 실패");
		}

	}
}
