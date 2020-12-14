package pos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import pos.svc.InitSvc;
import pos.svc.ManagementSvc;

public class PosMain {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);

		while (true) {
			SessionInfo.printSession();
			SessionInfo.printMain();			
			String sel = "";
			try {
				sel = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//�������
			if (sel.equals("1")) {
				ManagementSvc mgtSvc = new ManagementSvc();
				if(SessionInfo.sessionNick.isEmpty()) {
					mgtSvc.managementLogin();	
				}else {
					if(SessionInfo.sessionAuth.equals("mgr")) {
						mgtSvc.managementStart();	
					}
					else {
						System.out.println("�����ڰ� �ƴմϴ�");
					}
				}
				
			}
			//�ֹ����
			else if (sel.equals("2")) {
				InitSvc initSvc = new InitSvc();
				initSvc.initStart();
			} else if (sel.equals("0")) {
				if(SessionInfo.sessionNick.isEmpty()) {
					System.out.println("�ý����� �����մϴ�.");
					break;
				}else {
					SessionInfo.sessionNick="";
					SessionInfo.sessionAuth="";
					System.out.println("�α׾ƿ� �Ǿ����ϴ�.");
				}
				
			}

		}
	}
	
	
}
