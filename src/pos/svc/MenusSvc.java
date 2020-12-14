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
			System.out.println("1.���޴�����\n2.�޴��߰�\n3.�޴�����\n4.�޴����ݼ���\n0.���ư���");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("���޴�����");
				rs = menuDao.selectAllMenu();
				selectAllMenu(rs);
			} else if (sel.equals("2")) {
				System.out.println("�޴��߰�");
				insertMenu();
			} else if (sel.equals("3")) {
				System.out.println("�޴�����");
				deleteMenu();
			} else if (sel.equals("4")) {
				System.out.println("�޴����ݼ���");
				UpdateMenu();
			} else if (sel.equals("0")) {
				System.out.println("���ư��ϴ�.");
				break;
			}
		}
	}

	private void UpdateMenu() {
		String menuId = "";
		String price = "";
		int count = 0;
		try {
			System.out.println("������ �޴����� �Է��ϼ���");
			System.out.println("�޴��� �Է�");
			menuId = br.readLine();
			System.out.println("������ ���� �Է�");
			price = br.readLine();
			count = menuDao.updateMenu(menuId, price);
		} catch (IOException e) {
			System.out.println("���� ����");
		}

		if (count == 0) {
			System.out.println("���� ID �Դϴ�.");
		} else {
			System.out.println(menuId + "�� �����Ǿ����ϴ�.");
		}
	}

	private void deleteMenu() {
		String menuNum = "";
		int count = 0;
		try {
			System.out.println("�޴��� �����մϴ�");
			System.out.println(menuList);
			System.out.println("�޴���ȣ �Է�");
			menuNum = br.readLine();
			rcpDao.deleteRecipe(Integer.parseInt(menuNum));
			count = menuDao.deleteMenu(menuNum);

		} catch (IOException e) {
			System.out.println("���� ����");
		}
		if (count == 0) {
			System.out.println("���� �޴� �Դϴ�.");
		} else {
			System.out.println(menuNum + "�� �����Ǿ����ϴ�.");
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
							"��ȣ : " + menuid + " \t���� : " + kind + " \t�޴��� : " + menuName + " \t���� : " + price);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("�޴� ������ �����ϴ�.");
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
			System.out.println("�޴��� �߰��մϴ�");
			System.out.println("�޴����� �Է�");
			kind = br.readLine();
			System.out.println("�޴��� �Է�");
			menuName = br.readLine();
			System.out.println("���� �Է�");
			price = br.readLine();

			while (true) {
				System.out.println(materList);
				System.out.println("�޴��� ����� ��� ����(�ִ� 8��)  ���� : 0");
				String sel = br.readLine();
				if (sel.equals("0")) {
					break;
				}
				useMaterList += ","+sel;
				System.out.println("���� ����");
				System.out.println(useMaterList);
			}

			count = menuDao.insertMenu(kind, menuName, price);
			rs = menuDao.selectMenu(menuName);
			try {
				rs.next();
				RecipesDao rcpDao = new RecipesDao();
				//�޴�id��, ����ϴ� ��Ḧ �Ѱ��ش�
				rcpDao.insertRecipe(rs.getInt(1), useMaterList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println(menuName + "�� �߰��Ǿ����ϴ�.");
		} catch (IOException e) {
			System.out.println("�߰� ����");
		}

	}
}
