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
			System.out.println("1.�����������\n2.�����߰�\n3.��������\n4.������������\n0.���ư���");
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel.equals("1")) {
				System.out.println("�����������");
				rs = empDao.selectAllEmp();
				selectAllEmp(rs);
			} else if (sel.equals("2")) {
				System.out.println("�����߰�");				
				insertEmp();
			} else if (sel.equals("3")) {
				System.out.println("��������");
				deleteEmp();
			} else if (sel.equals("4")) {
				System.out.println("������������");
				UpdateEmp();
			} else if (sel.equals("0")) {
				System.out.println("���ư��ϴ�.");
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
			System.out.println("������ ������ ���̵� �Է��ϼ���");
			System.out.println("���� ID �Է�");
			empId = br.readLine();
			System.out.println("������ PW �Է�");
			pw = br.readLine();
			System.out.println("������ ��å�ڵ� �Է�(1.���� 2.������ 3.�Ŵ��� 4.�˹�)");
			position = br.readLine();
			count = empDao.updateEmp(empId, pw, position);
		} catch (IOException e) {
			System.out.println("���� ����");
		}
		
		if(count == 0) {
			System.out.println("���� ID �Դϴ�.");
		}else {
			System.out.println(empId +"�� �����Ǿ����ϴ�.");
		}
	}

	private void deleteEmp() {
		String empId="";
		int count=0;
		try {
			System.out.println("������ �����մϴ�");
			System.out.println("���� ID �Է�");
			empId = br.readLine();
			count = empDao.deleteEmp(empId);
			
		} catch (IOException e) {
			System.out.println("���� ����");
		}
		if(count == 0) {
			System.out.println("���� ID �Դϴ�.");
		}else {
			System.out.println(empId +"�� �����Ǿ����ϴ�.");
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
					System.out.println("������ȣ : "+ empId +" \t�̸� : "+empName + " \t��å : "+empPosition);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("���� ������ �����ϴ�.");
		}
	}
	
	private void insertEmp() {
		String name="";
		String pw="";
		String position="";
		int count=0;
		try {
			System.out.println("������ �߰��մϴ�");
			System.out.println("�̸� �Է�");
			name = br.readLine();
			System.out.println("PW �Է�");
			pw = br.readLine();
			System.out.println("��å�ڵ� �Է�(1.���� 2.������ 3.�Ŵ��� 4.�˹�)");
			position = br.readLine();
			count = empDao.insertEmp(name, pw, position);
			System.out.println(name +"�� �߰��Ǿ����ϴ�.");
		} catch (IOException e) {
			System.out.println("�߰� ����");
		}
		
	}
}
