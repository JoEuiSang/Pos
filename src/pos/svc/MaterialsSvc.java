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
			System.out.println("1.�����Ẹ��\n2.����߰�\n3.������\n4.����������\n5.������ +10\n0.���ư���");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("�����Ẹ��");
				rs = mtrDao.selectAllMtr();
				selectAllMtr(rs);
			} else if (sel.equals("2")) {
				System.out.println("����߰�");				
				insertMtr();
			} else if (sel.equals("3")) {
				System.out.println("������");
				deleteMtr();
			} else if (sel.equals("4")) {
				System.out.println("�����������");
				UpdateMtr();
			}  else if (sel.equals("5")) {
				System.out.println("������ + 10");				
				allMtrPlus();
			} else if (sel.equals("0")) {
				System.out.println("���ư��ϴ�.");
				break;
			}
		}
	}

	private void UpdateMtr() {
		String mtrId="";				
		String stock="";
		int count=0;
		try {
			System.out.println("������ ����� ���̵� �Է��ϼ���");
			System.out.println("��� ID �Է�");
			mtrId = br.readLine();
			System.out.println("������ ���� �Է�");
			stock = br.readLine();			
			count = mtrDao.updateMtr(mtrId, stock);
		} catch (IOException e) {
			System.out.println("���� ����");
		}
		
		if(count == 0) {
			System.out.println("���� ID �Դϴ�.");
		}else {
			System.out.println(mtrId +"�� �����Ǿ����ϴ�.");
		}
	}

	private void deleteMtr() {
		String mtrId="";
		int count=0;
		try {
			System.out.println("����� �����մϴ�");
			System.out.println("��� ID �Է�");
			mtrId = br.readLine();
			count = mtrDao.deleteMtr(mtrId);
			
		} catch (IOException e) {
			System.out.println("���� ����");
		}
		if(count == 0) {
			System.out.println("���� ID �Դϴ�.");
		}else {
			System.out.println(mtrId +"�� �����Ǿ����ϴ�.");
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
					System.out.println("����ȣ : "+ mtrId +" \t���� : "+mtrName + " \t������ : "+mtrStock);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("��� ������ �����ϴ�.");
		}
	}
	
	private void allMtrPlus() {
		int count=0;
		count = mtrDao.allMtrPlus();
		
		if(count == 1) {			
			System.out.println("��� ����� ��� 10 �߰��Ǿ����ϴ�.");
		}
	}
	
	private void insertMtr() {
		String mtrName="";
		String mtrCode="";
		String stock="";
		int count=0;
		try {
			System.out.println("����� �߰��մϴ�");
			System.out.println("����ڵ� �Է�");
			mtrCode = br.readLine();
			System.out.println("���� �Է�");
			mtrName = br.readLine();
			System.out.println("������ �Է�");
			stock = br.readLine();
			count = mtrDao.insertMtr(mtrCode, mtrName, stock);	
		} catch (IOException e) {
			System.out.println("�߰� ����");
		}
		if(count == 0) {
			System.out.println("���� ID �Դϴ�.");
		}else {
			System.out.println(mtrName +"�� �߰��Ǿ����ϴ�.");
		}
		
	}

	//�޴��� ���� ���� ���� ����;
	public void useMtr(String orderMenu) {
		String menuArr = orderMenu.substring(1);
		System.out.println("�޴����"+menuArr);
		rcpSvc.selectMaterial(menuArr);
	}
}
