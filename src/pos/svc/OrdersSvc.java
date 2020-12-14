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
	ArrayList<OrderDto> menuList = new ArrayList<OrderDto>(); // ��ü �޴�����Ʈ
	ArrayList<OrderDto> orderList = new ArrayList<OrderDto>();// �ֹ��� �޴� ����Ʈ
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
				System.out.println("�޴��� �����ϼ���");
				sel = Integer.parseInt(br.readLine());
				setOrderList(sel);
				orderMenu = orderMenu + "," + sel;
			} catch (IOException e) {
				e.printStackTrace();
			}

			//���� ���� ������ �޴� �����ֱ�
			getOrderList();

			int YN = 0;
			try {
				System.out.println("1.�߰��ֹ� 2.�ֹ��Ϸ�");
				YN = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (YN == 2) {
				System.out.println("�����޴�"+orderMenu);
				String pointYN = "";
				System.out.println("����Ʈ ���� �Ͻðڽ��ϱ�?");
				try {
					pointYN = br.readLine();
					if (pointYN.equals("Y")) {
						System.out.println("����� �޴�����ȣ �Է�");
						String phone = br.readLine();
						orderDao.insertOrder(phone, orderMenu, sumPrice);
						System.out.println("�ֹ��� �Ϸ�Ǿ����ϴ�.");
						break;
					} else {
						orderDao.insertOrder(orderMenu, sumPrice);
						System.out.println("�ֹ��� �Ϸ�Ǿ����ϴ�.");
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
		System.out.println("======= ���� �ֹ���� =======");
		for (OrderDto od : orderList) {
			System.out.println(od.getMenuName() +",\t" + od.getPrice()+"��");
		}
		System.out.println("======= �Ѿ�: "+sumPrice+" =======");
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
			System.out.println("������ �޴��� ���̵� �Է��ϼ���");
			System.out.println("�޴� ID �Է�");
			orderId = br.readLine();
			System.out.println("������ PW �Է�");
			pw = br.readLine();
			System.out.println("������ ��å�ڵ� �Է�(1.���� 2.������ 3.�Ŵ��� 4.�˹�)");
			position = br.readLine();
			count = orderDao.updateOrder(orderId, pw, position);
		} catch (IOException e) {
			System.out.println("���� ����");
		}

		if (count == 0) {
			System.out.println("���� ID �Դϴ�.");
		} else {
			System.out.println(orderId + "�� �����Ǿ����ϴ�.");
		}
	}

	private void deleteOrder() {
		int count = 0;
		int orderId=0;
		try {
			System.out.println("�ֹ��� �����մϴ�");
			System.out.println("�ֹ� ID �Է�");
			orderId = Integer.parseInt(br.readLine());
			count = orderDao.deleteOrder(orderId);
		} catch (IOException e) {
			System.out.println("���� ����");
		}
		if (count == 0) {
			System.out.println("���� �ֹ����� �Դϴ�.");
		} else {
			System.out.println(orderId + "�� �����Ǿ����ϴ�.");
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
				buyerId = (rs.getString(2)!=null)?rs.getString(2):"����";
				orderMenu = rs.getString(3);
				purDate = rs.getString(4);
				sumPrice = rs.getString(5);
				System.out.println("�ֹ���ȣ: "+ orderNum +"\t ������ID: "+buyerId+"\t�ֹ��޴�:"+orderMenu+"\t������: "+purDate+"\t�ݾ�: "+sumPrice);
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
			System.out.println("�˻��� �մ��� ID�� �Է��ϼ���");
			buyerId = Integer.parseInt(br.readLine());
			rs = orderDao.selectEachOrder(buyerId);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}		
		
		try {
			System.out.println("������ : "+buyerId);
			while(rs.next()) {
				orderNum = rs.getString(1);
				orderMenu = rs.getString(3);
				purDate = rs.getString(4);
				System.out.println("�ֹ���ȣ: "+ orderNum + "\t�ֹ��޴�:"+orderMenu+"\t������: "+purDate);
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
				System.out.println("1.�ֹ��������� 2.�ֹ��������� 3.�մԺ��ֹ��˻� 0.���ư���");
				sel = Integer.parseInt(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (sel == 1) {				
				System.out.println("�ֹ���������");
				selectAllOrder();
				
			}else if(sel==2) {
				System.out.println("�ֹ���������");
				deleteOrder();
			}else if(sel==3) {
				System.out.println("�մԺ��ֹ��˻�");
				selectEachOrder();
			}else if(sel==0) {
				System.out.println("���ư��ϴ�");
				break;
			}
		}
	}
}
