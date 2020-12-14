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
			System.out.println("1.모든회원보기\n2.회원추가\n3.회원삭제\n4.회원정보수정\n0.돌아가기");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("모든회원보기");
				rs = memDao.selectAllMem();
				selectAllMem(rs);
			} else if (sel.equals("2")) {
				System.out.println("회원추가");				
				insertMem();
			} else if (sel.equals("3")) {
				System.out.println("회원삭제");
				deleteMem();
			} else if (sel.equals("4")) {
				System.out.println("회원정보수정");
				UpdateMem();
			} else if (sel.equals("0")) {
				System.out.println("돌아갑니다.");
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
			System.out.println("수정할 회원의 아이디 입력하세요");
			System.out.println("회원 ID 입력");
			memId = br.readLine();
			System.out.println("수정할 이름 입력");
			name = br.readLine();
			System.out.println("수정할 전화번호 입력");
			phone = br.readLine();
			count = memDao.updateMem(memId, name, phone);
		} catch (IOException e) {
			System.out.println("수정 실패");
		}
		
		if(count == 0) {
			System.out.println("없는 ID 입니다.");
		}else {
			System.out.println(memId +"이 수정되었습니다.");
		}
	}

	private void deleteMem() {
		String memId="";
		int count=0;
		try {
			System.out.println("회원을 삭제합니다");
			System.out.println("회원 ID 입력");
			memId = br.readLine();
			count = memDao.deleteMem(memId);
			
		} catch (IOException e) {
			System.out.println("삭제 실패");
		}
		if(count == 0) {
			System.out.println("없는 ID 입니다.");
		}else {
			System.out.println(memId +"번 회원이 삭제되었습니다.");
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
					
					System.out.println("회원번호 : "+ memId +" \t이름 : "+memName + " \t전화번호 : "+memPhone +"\t포인트 : "+memPoint);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("회원 정보가 없습니다.");
		}
	}
	
	private void insertMem() {
		String name="";
		String phone="";
		int count=0;
		try {
			System.out.println("회원을 추가합니다");
			System.out.println("이름 입력");
			name = br.readLine();
			System.out.println("전화번호 입력");
			phone = br.readLine();			
			count = memDao.insertMem(name, phone);
			System.out.println(name +"이 추가되었습니다.");
		} catch (IOException e) {
			System.out.println("추가 실패");
		}
		
	}
}
