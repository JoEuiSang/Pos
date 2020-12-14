package pos.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import pos.SessionInfo;
import pos.dao.ManagementDao;

public class ManagementSvc {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	ManagementDao mgtDao = new ManagementDao();
	ResultSet rs = null;
	String session = "";

	public void managementLogin() {
		System.out.println("<<<<<<<<<<<�Ƶ�������ġŷ>>>>>>>>>>>>");
		System.out.println("-------������ ID�� �α����� �ϼ���-------");
		String id = "";
		String pw = "";
		try {
			System.out.println("���̵�");
			id = br.readLine();
			System.out.println("�н�����");
			pw = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		rs = mgtDao.selectMgr(id, pw);
		if (rs != null) {
			try {
				if (rs.next()) {
					SessionInfo.sessionNick = rs.getString(1);
					if (rs.getInt(2) == 1 || rs.getInt(2) == 2) {
						SessionInfo.sessionAuth = "mgr";
						System.out.println(SessionInfo.sessionNick + "�� �α��� �ϼ̽��ϴ�.");
					} else {
						System.out.println(SessionInfo.sessionNick + "�� �α��� �ϼ̽��ϴ�.");
						System.out.println("�����ڰ� �ƴմϴ�");
					}

				} else {
					System.out.println("id �Ǵ� pw�� Ȯ�����ּ���");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("�߸��� �����Դϴ�");
		}
	}

	public void managementStart() {
		while (true) {
			SessionInfo.printSession();
			String sel = "";
			System.out.println("1.��������\n2.�մ԰���\n3.������\n4.�޴�����\n5.�ֹ�����\n0.���ư���");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("��������");
				EmployeesSvc empSvc = new EmployeesSvc();
				empSvc.empMgrStart();
			} else if (sel.equals("2")) {
				System.out.println("�մ԰���");
				MembersSvc memSvc = new MembersSvc();
				memSvc.memMgrStart();
			} else if (sel.equals("3")) {
				System.out.println("������");
				MaterialsSvc mtrSvc = new MaterialsSvc();
				mtrSvc.mtrMgrStart();
			} else if (sel.equals("4")) {
				System.out.println("�޴�����");
				MenusSvc menuSvc = new MenusSvc();
				menuSvc.menuMgrStart();
			} else if (sel.equals("5")) {
				System.out.println("�ֹ�����");
				OrdersSvc odSvc = new OrdersSvc();
				odSvc.odMgrStart();
			} else if (sel.equals("0")) {
				System.out.println("���ư��ϴ�.");
				break;
			}
		}
	}
}
