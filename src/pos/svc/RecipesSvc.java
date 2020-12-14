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

	// 레시피 조회
	public void selectMaterial(String menuArr) {
		System.out.println("메뉴번호 리스트" + menuArr);
		String[] menu = menuArr.split(",");
		ArrayList<String> menuList = new ArrayList<String>();
		for (int i = 0; i < menu.length; i++) {
			if (!menu[i].isEmpty()) {
				menuList.add(menu[i]);
			}
		}

		for (String m : menuList) {
			// 주문한 각메뉴의 재료들 뽑아오기
			System.out.println("현재메뉴" + m);
			String materArr = rcpDao.selectRecipeMaterial(m);
			if (!materArr.equals("")) {
				// 사용한 재료들 -1
				rcpDao.useMtr(materArr);
			}
		}

		return;
	}

	// 메뉴에 들어가는 재료들 수량 감소;
	public void useMtr(String orderMenu) {
		String menuArr = orderMenu.substring(1);
		mtrDao.useMtr(menuArr);

	}

}
