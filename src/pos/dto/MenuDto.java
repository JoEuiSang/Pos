package pos.dto;

public class MenuDto {
	private String menuName;
	private int price;
	private String kind;
	private int menuid;
	
	
	
	@Override
	public String toString() {
		return menuid + " : " + menuName + " price=" + price+"\n";
	}
	public MenuDto(String menuName, int price, String kind, int menuid) {
		super();
		this.menuName = menuName;
		this.price = price;
		this.kind = kind;
		this.menuid = menuid;
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
	public int getMenuid() {
		return menuid;
	}
	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}
	
	
}
