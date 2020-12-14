package pos.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pos.SessionInfo;
import pos.dao.MaterialsDao;

public class MaterialsSvc {
	MaterialsDao mtrDao = new MaterialsDao();
	RecipesSvc rcpSvc = new RecipesSvc();
	ResultSet rs = null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void mtrMgrStart() {
		while (true) {
			SessionInfo.printSession();
			String sel = "";
			System.out.println("1.모든재료보기\n2.재료추가\n3.재료삭제\n4.재료수량수정\n5.모든재료 +10\n0.돌아가기");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("모든재료보기");
				rs = mtrDao.selectAllMtr();
				selectAllMtr(rs);
			} else if (sel.equals("2")) {
				System.out.println("재료추가");				
				insertMtr();
			} else if (sel.equals("3")) {
				System.out.println("재료삭제");
				deleteMtr();
			} else if (sel.equals("4")) {
				System.out.println("재료정보수정");
				UpdateMtr();
			}  else if (sel.equals("5")) {
				System.out.println("모든재료 + 10");				
				allMtrPlus();
			} else if (sel.equals("0")) {
				System.out.println("돌아갑니다.");
				break;
			}
		}
	}

	private void UpdateMtr() {
		String mtrId="";				
		String stock="";
		int count=0;
		try {
			System.out.println("수정할 재료의 아이디 입력하세요");
			System.out.println("재료 ID 입력");
			mtrId = br.readLine();
			System.out.println("수정할 수량 입력");
			stock = br.readLine();			
			count = mtrDao.updateMtr(mtrId, stock);
		} catch (IOException e) {
			System.out.println("수정 실패");
		}
		
		if(count == 0) {
			System.out.println("없는 ID 입니다.");
		}else {
			System.out.println(mtrId +"이 수정되었습니다.");
		}
	}

	private void deleteMtr() {
		String mtrId="";
		int count=0;
		try {
			System.out.println("재료을 삭제합니다");
			System.out.println("재료 ID 입력");
			mtrId = br.readLine();
			count = mtrDao.deleteMtr(mtrId);
			
		} catch (IOException e) {
			System.out.println("삭제 실패");
		}
		if(count == 0) {
			System.out.println("없는 ID 입니다.");
		}else {
			System.out.println(mtrId +"이 삭제되었습니다.");
		}
	}

	private void selectAllMtr(ResultSet rs) {
		if (rs != null) {
			int mtrId=0;
			String mtrName = "";
			int mtrStock = 1;
			try {
				while (rs.next()) {
					mtrId = rs.getInt(1);
					mtrName = rs.getString(2);
					mtrStock = rs.getInt(3);
					System.out.println("재료번호 : "+ mtrId +" \t재료명 : "+mtrName + " \t재고수량 : "+mtrStock);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("재료 정보가 없습니다.");
		}
	}
	
	private void allMtrPlus() {
		int count=0;
		count = mtrDao.allMtrPlus();
		
		if(count == 1) {			
			System.out.println("모든 재료의 재고가 10 추가되었습니다.");
		}
	}
	
	private void insertMtr() {
		String mtrName="";
		String mtrCode="";
		String stock="";
		int count=0;
		try {
			System.out.println("재료을 추가합니다");
			System.out.println("재료코드 입력");
			mtrCode = br.readLine();
			System.out.println("재료명 입력");
			mtrName = br.readLine();
			System.out.println("재고수량 입력");
			stock = br.readLine();
			count = mtrDao.insertMtr(mtrCode, mtrName, stock);	
		} catch (IOException e) {
			System.out.println("추가 실패");
		}
		if(count == 0) {
			System.out.println("없는 ID 입니다.");
		}else {
			System.out.println(mtrName +"이 추가되었습니다.");
		}
		
	}

	//메뉴에 들어가는 재료들 수량 감소;
	public void useMtr(String orderMenu) {
		String menuArr = orderMenu.substring(1);
		System.out.println("메뉴어레이"+menuArr);
		rcpSvc.selectMaterial(menuArr);
	}
}
