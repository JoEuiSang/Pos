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

	public void initStart() {
		while (true) {
			String sel = "";
			System.out.println("!!!!!맛있는 맥도리아터치킹!!!!!");
			System.out.println("1.주문하기\n0.돌아가기");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("주문하기");
				OrdersSvc orderSvc = new OrdersSvc();
				orderSvc.orderMenu();
			}  else if (sel.equals("0")) {
				System.out.println("돌아갑니다.");
				break;
			}
		}
	}
}
