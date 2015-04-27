package garbage.robot;

public class FieldOnMap {
	private int wetness;
	private int colorIntensity;
	private int smellIntensity;
	private boolean isSticky;
	private int size;
	private boolean isDried;
	public FieldOnMap(int wetness, int colorIntensity, int smellIntensity,
			boolean isSticky, int size, boolean isDried) {
		super();
		this.wetness = wetness;
		this.colorIntensity = colorIntensity;
		this.smellIntensity = smellIntensity;
		this.isSticky = isSticky;
		this.size = size;
		this.isDried = isDried;
	}
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
	
	
}
