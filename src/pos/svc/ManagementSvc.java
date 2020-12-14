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
		System.out.println("<<<<<<<<<<<맥도리아터치킹>>>>>>>>>>>>");
		System.out.println("-------관리자 ID로 로그인을 하세요-------");
		String id = "";
		String pw = "";
		try {
			System.out.println("아이디");
			id = br.readLine();
			System.out.println("패스워드");
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
						System.out.println(SessionInfo.sessionNick + "님 로그인 하셨습니다.");
					} else {
						System.out.println(SessionInfo.sessionNick + "님 로그인 하셨습니다.");
						System.out.println("관리자가 아닙니다");
					}

				} else {
					System.out.println("id 또는 pw를 확인해주세요");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("잘못된 접속입니다");
		}
	}

	public void managementStart() {
		while (true) {
			SessionInfo.printSession();
			String sel = "";
			System.out.println("1.직원관리\n2.손님관리\n3.재고관리\n4.메뉴관리\n5.주문관리\n0.돌아가기");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("직원관리");
				EmployeesSvc empSvc = new EmployeesSvc();
				empSvc.empMgrStart();
			} else if (sel.equals("2")) {
				System.out.println("손님관리");
				MembersSvc memSvc = new MembersSvc();
				memSvc.memMgrStart();
			} else if (sel.equals("3")) {
				System.out.println("재고관리");
				MaterialsSvc mtrSvc = new MaterialsSvc();
				mtrSvc.mtrMgrStart();
			} else if (sel.equals("4")) {
				System.out.println("메뉴관리");
				MenusSvc menuSvc = new MenusSvc();
				menuSvc.menuMgrStart();
			} else if (sel.equals("5")) {
				System.out.println("주문관리");
				OrdersSvc odSvc = new OrdersSvc();
				odSvc.odMgrStart();
			} else if (sel.equals("0")) {
				System.out.println("돌아갑니다.");
				break;
			}
		}
	}
}
