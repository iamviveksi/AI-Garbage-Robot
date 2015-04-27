package garbage.robot;

public class FieldOnMap {
	private int wetness;
	private int colorIntensity;
	private int smellIntensity;
	private boolean isSticky;
	private int size;
	private boolean isDried;
	private boolean isGreasy;
	private int softness;
	
	
	//constructor
	public FieldOnMap(int wetness, int colorIntensity, int smellIntensity,
			boolean isSticky, int size, boolean isDried, boolean isGreasy,
			int softness) {
		this.wetness = wetness;
		this.colorIntensity = colorIntensity;
		this.smellIntensity = smellIntensity;
		this.isSticky = isSticky;
		this.size = size;
		this.isDried = isDried;
		this.isGreasy = isGreasy;
		this.softness = softness;
	}
	
	//getters and setters
	public int getWetness() {
		return wetness;
	}
	public void setWetness(int wetness) {
		this.wetness = wetness;
	}
	public int getColorIntensity() {
		return colorIntensity;
	}
	public void setColorIntensity(int colorIntensity) {
		this.colorIntensity = colorIntensity;
	}
	public int getSmellIntensity() {
		return smellIntensity;
	}
	public void setSmellIntensity(int smellIntensity) {
		this.smellIntensity = smellIntensity;
	}
	public boolean isSticky() {
		return isSticky;
	}
	public void setSticky(boolean isSticky) {
		this.isSticky = isSticky;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isDried() {
		return isDried;
	}
	public void setDried(boolean isDried) {
		this.isDried = isDried;
	}
	public boolean isGreasy() {
		return isGreasy;
	}
	public void setGreasy(boolean isGreasy) {
		this.isGreasy = isGreasy;
	}
	public int getSoftness() {
		return softness;
	}
	public void setSoftness(int softness) {
		this.softness = softness;
	}
	
	
	
}
