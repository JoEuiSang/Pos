package pos.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import pos.SessionInfo;
import pos.dao.ManagementDao;

public class InitSvc {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	ManagementDao mgtDao = new ManagementDao();
	ResultSet rs = null;
	String session = "";

	public void memLogin() {
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

	public void initStart() {
		while (true) {
			String sel = "";
			System.out.println("!!!!!���ִ� �Ƶ�������ġŷ!!!!!");
			System.out.println("1.�ֹ��ϱ�\n0.���ư���");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("�ֹ��ϱ�");
				OrdersSvc orderSvc = new OrdersSvc();
				orderSvc.orderMenu();
			}  else if (sel.equals("0")) {
				System.out.println("���ư��ϴ�.");
				break;
			}
		}
	}
}
