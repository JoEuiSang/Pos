package pos.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import pos.SessionInfo;
import pos.dao.EmployeesDao;

public class EmployeesSvc {
	EmployeesDao empDao = new EmployeesDao();
	ResultSet rs = null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void empMgrStart() {
		while (true) {
			SessionInfo.printSession();
			String sel = "";
			System.out.println("1.모든직원보기\n2.직원추가\n3.직원삭제\n4.직원정보수정\n0.돌아가기");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("모든직원보기");
				rs = empDao.selectAllEmp();
				selectAllEmp(rs);
			} else if (sel.equals("2")) {
				System.out.println("직원추가");				
				insertEmp();
			} else if (sel.equals("3")) {
				System.out.println("직원삭제");
				deleteEmp();
			} else if (sel.equals("4")) {
				System.out.println("직원정보수정");
				UpdateEmp();
			} else if (sel.equals("0")) {
				System.out.println("돌아갑니다.");
				break;
			}
		}
	}

	private void UpdateEmp() {
		String empId="";
		String name="";
		String pw="";
		String position="";
		int count=0;
		try {
			System.out.println("수정할 직원의 아이디 입력하세요");
			System.out.println("직원 ID 입력");
			empId = br.readLine();
			System.out.println("수정할 PW 입력");
			pw = br.readLine();
			System.out.println("수정할 직책코드 입력(1.점장 2.부점장 3.매니져 4.알바)");
			position = br.readLine();
			count = empDao.updateEmp(empId, pw, position);
		} catch (IOException e) {
			System.out.println("수정 실패");
		}
		
		if(count == 0) {
			System.out.println("없는 ID 입니다.");
		}else {
			System.out.println(empId +"이 수정되었습니다.");
		}
	}

	private void deleteEmp() {
		String empId="";
		int count=0;
		try {
			System.out.println("직원을 삭제합니다");
			System.out.println("직원 ID 입력");
			empId = br.readLine();
			count = empDao.deleteEmp(empId);
			
		} catch (IOException e) {
			System.out.println("삭제 실패");
		}
		if(count == 0) {
			System.out.println("없는 ID 입니다.");
		}else {
			System.out.println(empId +"이 삭제되었습니다.");
		}
	}

	private void selectAllEmp(ResultSet rs) {
		if (rs != null) {
			String empId="";
			String empName = "";
			String empPosition = "";
			try {
				while (rs.next()) {
					empName = rs.getString(1);
					empPosition = rs.getString(2);
					empId = rs.getString(3);
					System.out.println("직원번호 : "+ empId +" \t이름 : "+empName + " \t직책 : "+empPosition);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("직원 정보가 없습니다.");
		}
	}
	
	private void insertEmp() {
		String name="";
		String pw="";
		String position="";
		int count=0;
		try {
			System.out.println("직원을 추가합니다");
			System.out.println("이름 입력");
			name = br.readLine();
			System.out.println("PW 입력");
			pw = br.readLine();
			System.out.println("직책코드 입력(1.점장 2.부점장 3.매니져 4.알바)");
			position = br.readLine();
			count = empDao.insertEmp(name, pw, position);
			System.out.println(name +"이 추가되었습니다.");
		} catch (IOException e) {
			System.out.println("추가 실패");
		}
		
	}
}
