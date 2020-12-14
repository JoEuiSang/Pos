package pos;

public class SessionInfo {
	static public String sessionId="";
	static public String sessionNick="";
	static public String sessionAuth = "";
	
	static public String custId="";
	static public String custName="";	
	
	public static void printSession() {
		if(!SessionInfo.sessionNick.isEmpty()) {
			System.out.println("\n\n<<<<<<<<<<<맥도리아터치킹>>>>>>>>>>>>");
			if(SessionInfo.sessionAuth.equals("mgr")) {
				System.out.println(SessionInfo.sessionNick + "(관리자)님 접속중...");
			}else {
				System.out.println(SessionInfo.sessionNick + "(일반직원)님 접속중...");
			}	
		}
	}
	
	public static void printMain() {
		if(SessionInfo.sessionNick.isEmpty()) {
			System.out.println("1.매장관리\n2.주문하기\n0.종료");
		}else {
			System.out.println("1.매장관리\n2.주문하기\n0.로그아웃");
		}
	}
}
