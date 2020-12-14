package pos.dto;

public class MaterialDto {
	private int matNum;
	private String matName;
	
	
	public MaterialDto(int matNum, String matName) {
		super();
		this.matNum = matNum;
		this.matName = matName;
	}
	
	public int getMatNum() {
		return matNum;
	}
	public void setMatNum(int matNum) {
		this.matNum = matNum;
	}
	public String getMatName() {
		return matName;
	}
	public void setMatName(String matName) {
		this.matName = matName;
	}

	@Override
	public String toString() {
		return matNum + " : " + matName;
	}
	
	
	
}
