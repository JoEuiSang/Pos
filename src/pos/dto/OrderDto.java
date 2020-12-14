package pos.dto;

public class OrderDto {
	private int menuId;
	private String kind;	
	private String menuName;
	private int price;
	

	public OrderDto(int menuId, String kind, String menuName, int price) {
		super();
		this.menuId = menuId;
		this.kind = kind;
		this.menuName = menuName;
		this.price = price;
	}
	
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	@Override
	public String toString() {
		return "번호 : " + menuId +" \t종류 : " + kind + " \t메뉴명 : "+ menuName + " \t가격 : "+ price;
	}
	
	
	
	
}
