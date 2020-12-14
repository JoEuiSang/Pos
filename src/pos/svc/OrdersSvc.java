package pos.svc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pos.SessionInfo;
import pos.dao.MembersDao;
import pos.dao.MenusDao;
import pos.dao.OrdersDao;
import pos.dto.OrderDto;

public class OrdersSvc {
	OrdersDao orderDao = new OrdersDao();
	MenusDao menuDao = new MenusDao();
	MenusSvc menuSvc = new MenusSvc();
	MembersDao memDao = new MembersDao();
	ResultSet rs = null;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	ArrayList<OrderDto> menuList = new ArrayList<OrderDto>(); // 전체 메뉴리스트
	ArrayList<OrderDto> orderList = new ArrayList<OrderDto>();// 주문한 메뉴 리스트
	int sumPrice = 0;

	public void orderMenu() {
		rs = menuDao.selectAllMenu();
		setMenuList(rs);
		String orderMenu = "";
		while (true) {
			SessionInfo.printSession();
			getMenuList(menuList);
			int sel;

			try {
				System.out.println("메뉴를 선택하세요");
				sel = Integer.parseInt(br.readLine());
				setOrderList(sel);
				orderMenu = orderMenu + "," + sel;
			} catch (IOException e) {
				e.printStackTrace();
			}

			//지금 내가 선택한 메뉴 보여주기
			getOrderList();

			int YN = 0;
			try {
				System.out.println("1.추가주문 2.주문완료");
				YN = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (YN == 2) {
				System.out.println("오더메뉴"+orderMenu);
				String pointYN = "";
				System.out.println("포인트 적립 하시겠습니까?");
				try {
					pointYN = br.readLine();
					if (pointYN.equals("Y")) {
						System.out.println("당신의 휴대폰번호 입력");
						String phone = br.readLine();
						orderDao.insertOrder(phone, orderMenu, sumPrice);
						System.out.println("주문이 완료되었습니다.");
						break;
					} else {
						orderDao.insertOrder(orderMenu, sumPrice);
						System.out.println("주문이 완료되었습니다.");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setMenuList(ResultSet rs) {
		try {
			while (rs.next()) {
				menuList.add(new OrderDto(rs.getInt(4), rs.getString(1), rs.getString(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setOrderList(int menuId) {
		for (OrderDto od : menuList) {
			if (od.getMenuId() == menuId) {
				orderList.add(od);
				sumPrice += od.getPrice();
			}
		}
	}

	public void getOrderList() {
		System.out.println("======= 현재 주문목록 =======");
		for (OrderDto od : orderList) {
			System.out.println(od.getMenuName() +",\t" + od.getPrice()+"원");
		}
		System.out.println("======= 총액: "+sumPrice+" =======");
	}

	public void getMenuList(ArrayList<OrderDto> menuList) {
		for (OrderDto od : menuList) {
			System.out.println(od);
		}
	}

	private void UpdateOrder() {
		String orderId = "";
		String name = "";
		String pw = "";
		String position = "";
		int count = 0;
		try {
			System.out.println("수정할 메뉴의 아이디 입력하세요");
			System.out.println("메뉴 ID 입력");
			orderId = br.readLine();
			System.out.println("수정할 PW 입력");
			pw = br.readLine();
			System.out.println("수정할 직책코드 입력(1.점장 2.부점장 3.매니져 4.알바)");
			position = br.readLine();
			count = orderDao.updateOrder(orderId, pw, position);
		} catch (IOException e) {
			System.out.println("수정 실패");
		}

		if (count == 0) {
			System.out.println("없는 ID 입니다.");
		} else {
			System.out.println(orderId + "이 수정되었습니다.");
		}
	}

	private void deleteOrder() {
		int count = 0;
		int orderId=0;
		try {
			System.out.println("주문을 삭제합니다");
			System.out.println("주문 ID 입력");
			orderId = Integer.parseInt(br.readLine());
			count = orderDao.deleteOrder(orderId);
		} catch (IOException e) {
			System.out.println("삭제 실패");
		}
		if (count == 0) {
			System.out.println("없는 주문내역 입니다.");
		} else {
			System.out.println(orderId + "이 삭제되었습니다.");
		}
	}

	private void selectAllOrder() {
		rs = orderDao.selectAllOrder();
		String orderNum;
		String buyerId;
		String orderMenu;
		String purDate;
		String sumPrice;
		try {
			while(rs.next()) {
				orderNum = rs.getString(1);
				buyerId = (rs.getString(2)!=null)?rs.getString(2):"없음";
				orderMenu = rs.getString(3);
				purDate = rs.getString(4);
				sumPrice = rs.getString(5);
				System.out.println("주문번호: "+ orderNum +"\t 구매자ID: "+buyerId+"\t주문메뉴:"+orderMenu+"\t구매일: "+purDate+"\t금액: "+sumPrice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void selectEachOrder() {
		int buyerId=0;
		String orderNum;
		String orderMenu;
		String purDate;
		try {
			System.out.println("검색할 손님의 ID를 입력하세요");
			buyerId = Integer.parseInt(br.readLine());
			rs = orderDao.selectEachOrder(buyerId);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
		
		try {
			System.out.println("구매자 : "+buyerId);
			while(rs.next()) {
				orderNum = rs.getString(1);
				orderMenu = rs.getString(3);
				purDate = rs.getString(4);
				System.out.println("주문번호: "+ orderNum + "\t주문메뉴:"+orderMenu+"\t구매일: "+purDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void odMgrStart() {
		while (true) {
			SessionInfo.printSession();
			getMenuList(menuList);
			int sel=0;

			try {
				System.out.println("1.주문내역보기 2.주문내역삭제 3.손님별주문검색 0.돌아가기");
				sel = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel == 1) {				
				System.out.println("주문내역보기");
				selectAllOrder();
				
			}else if(sel==2) {
				System.out.println("주문내역삭제");
				deleteOrder();
			}else if(sel==3) {
				System.out.println("손님별주문검색");
				selectEachOrder();
			}else if(sel==0) {
				System.out.println("돌아갑니다");
				break;
			}
		}
	}
}
