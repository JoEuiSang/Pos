package pos;

public class SessionInfo {
	static public String sessionId="";
	static public String sessionNick="";
	static public String sessionAuth = "";
	
	static public String custId="";
	static public String custName="";	
	
	public static void printSession() {
		if(!SessionInfo.sessionNick.isEmpty()) {
			System.out.println("\n\n<<<<<<<<<<<�Ƶ�������ġŷ>>>>>>>>>>>>");
			if(SessionInfo.sessionAuth.equals("mgr")) {
				System.out.println(SessionInfo.sessionNick + "(������)�� ������...");
			}else {
				System.out.println(SessionInfo.sessionNick + "(�Ϲ�����)�� ������...");
			}	
		}
	}
	
	public static void printMain() {
		if(SessionInfo.sessionNick.isEmpty()) {
			System.out.println("1.�������\n2.�ֹ��ϱ�\n0.����");
		}else {
			System.out.println("1.�������\n2.�ֹ��ϱ�\n0.�α׾ƿ�");
		}
	}
}
