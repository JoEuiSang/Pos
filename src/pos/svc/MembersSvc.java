package pos.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import pos.SessionInfo;
import pos.dao.MembersDao;

public class MembersSvc {
	MembersDao memDao = new MembersDao();
	ResultSet rs = null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void memMgrStart() {
		while (true) {
			SessionInfo.printSession();
			String sel = "";
			System.out.println("1.���ȸ������\n2.ȸ���߰�\n3.ȸ������\n4.ȸ����������\n0.���ư���");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("���ȸ������");
				rs = memDao.selectAllMem();
				selectAllMem(rs);
			} else if (sel.equals("2")) {
				System.out.println("ȸ���߰�");				
				insertMem();
			} else if (sel.equals("3")) {
				System.out.println("ȸ������");
				deleteMem();
			} else if (sel.equals("4")) {
				System.out.println("ȸ����������");
				UpdateMem();
			} else if (sel.equals("0")) {
				System.out.println("���ư��ϴ�.");
				break;
			}
		}
	}

	private void UpdateMem() {
		String memId="";
		String name="";
		String phone="";
		int count=0;
		try {
			System.out.println("������ ȸ���� ���̵� �Է��ϼ���");
			System.out.println("ȸ�� ID �Է�");
			memId = br.readLine();
			System.out.println("������ �̸� �Է�");
			name = br.readLine();
			System.out.println("������ ��ȭ��ȣ �Է�");
			phone = br.readLine();
			count = memDao.updateMem(memId, name, phone);
		} catch (IOException e) {
			System.out.println("���� ����");
		}
		
		if(count == 0) {
			System.out.println("���� ID �Դϴ�.");
		}else {
			System.out.println(memId +"�� �����Ǿ����ϴ�.");
		}
	}

	private void deleteMem() {
		String memId="";
		int count=0;
		try {
			System.out.println("ȸ���� �����մϴ�");
			System.out.println("ȸ�� ID �Է�");
			memId = br.readLine();
			count = memDao.deleteMem(memId);
			
		} catch (IOException e) {
			System.out.println("���� ����");
		}
		if(count == 0) {
			System.out.println("���� ID �Դϴ�.");
		}else {
			System.out.println(memId +"�� ȸ���� �����Ǿ����ϴ�.");
		}
	}

	private void selectAllMem(ResultSet rs) {
		if (rs != null) {
			String memId="";
			String memName = "";
			String memPhone = "";
			String memPoint="";
			try {
				while (rs.next()) {
					memId = rs.getString(1);
					memName = rs.getString(2);
					memPhone = rs.getString(3);
					memPoint = rs.getString(4);
					
					System.out.println("ȸ����ȣ : "+ memId +" \t�̸� : "+memName + " \t��ȭ��ȣ : "+memPhone +"\t����Ʈ : "+memPoint);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ȸ�� ������ �����ϴ�.");
		}
	}
	
	private void insertMem() {
		String name="";
		String phone="";
		int count=0;
		try {
			System.out.println("ȸ���� �߰��մϴ�");
			System.out.println("�̸� �Է�");
			name = br.readLine();
			System.out.println("��ȭ��ȣ �Է�");
			phone = br.readLine();			
			count = memDao.insertMem(name, phone);
			System.out.println(name +"�� �߰��Ǿ����ϴ�.");
		} catch (IOException e) {
			System.out.println("�߰� ����");
		}
		
	}
}
