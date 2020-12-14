package pos.svc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;

import pos.dao.MaterialsDao;
import pos.dao.RecipesDao;

public class RecipesSvc {
	MaterialsDao mtrDao = new MaterialsDao();
	RecipesDao rcpDao = new RecipesDao();
	ResultSet rs = null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	// ������ ��ȸ
	public void selectMaterial(String menuArr) {
		System.out.println("�޴���ȣ ����Ʈ" + menuArr);
		String[] menu = menuArr.split(",");
		ArrayList<String> menuList = new ArrayList<String>();
		for (int i = 0; i < menu.length; i++) {
			if (!menu[i].isEmpty()) {
				menuList.add(menu[i]);
			}
		}

		for (String m : menuList) {
			// �ֹ��� ���޴��� ���� �̾ƿ���
			System.out.println("����޴�" + m);
			String materArr = rcpDao.selectRecipeMaterial(m);
			if (!materArr.equals("")) {
				// ����� ���� -1
				rcpDao.useMtr(materArr);
			}
		}

		return;
	}

	// �޴��� ���� ���� ���� ����;
	public void useMtr(String orderMenu) {
		String menuArr = orderMenu.substring(1);
		mtrDao.useMtr(menuArr);

	}

}
