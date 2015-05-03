package garbage.robot;

import java.util.Random;

public class Stain {
	private int xPos;
	private int yPos;
	private int wetness;
	private int colorIntensity;
	private int smellIntensity;
	private boolean isSticky;
	private int size;
	private boolean isDried;
	private boolean isGreasy;
	private int softness;
	private int dangerousBacteries;
	private int height;
	private boolean isFruity;
	private int density;
	
	
	
	//constructor
	
	public Stain(){
		drawParams();
	}
	
	
	public Stain(int xPos, int yPos, int wetness, int colorIntensity,
			int smellIntensity, boolean isSticky, int size, boolean isDried,
			boolean isGreasy, int softness, int dangerousBacteries, int height,
			boolean isFruity, int density) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.wetness = wetness;
		this.colorIntensity = colorIntensity;
		this.smellIntensity = smellIntensity;
		this.isSticky = isSticky;
		this.size = size;
		this.isDried = isDried;
		this.isGreasy = isGreasy;
		this.softness = softness;
		this.dangerousBacteries = dangerousBacteries;
		this.height = height;
		this.isFruity = isFruity;
		this.density = density;
	}
	
	//method draw params for pain
	private void drawParams(){
		Random generator = new Random();
		this.wetness = generator.nextInt(100);
		this.colorIntensity =  generator.nextInt(100);
		this.smellIntensity =  generator.nextInt(100);
		this.isSticky =  generator.nextBoolean();
		this.size =  generator.nextInt(100);
		this.isDried = generator.nextBoolean();
		this.isGreasy = generator.nextBoolean();
		this.softness =  generator.nextInt(100);
		this.dangerousBacteries =  generator.nextInt(100);
		this.height =  generator.nextInt(100);
		this.isFruity = generator.nextBoolean();
		this.density = generator.nextInt(100);
	}
	

	
	@Override
	public String toString() {
		return "Stain [xPos=" + xPos + ", yPos=" + yPos + ", wetness="
				+ wetness + ", colorIntensity=" + colorIntensity
				+ ", smellIntensity=" + smellIntensity + ", isSticky="
				+ isSticky + ", size=" + size + ", isDried=" + isDried
				+ ", isGreasy=" + isGreasy + ", softness=" + softness
				+ ", dangerousBacteries=" + dangerousBacteries + ", height="
				+ height + ", isFruity=" + isFruity + ", density=" + density
				+ "]";
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
	
	public int getDensity() {
		return density;
	}
	public void setDensity(int density) {
		this.density = density;
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
	public int getDangerousBacteries() {
		return dangerousBacteries;
	}
	public void setDangerousBacteries(int dangerousBacteries) {
		this.dangerousBacteries = dangerousBacteries;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isFruity() {
		return isFruity;
	}
	public void setFruity(boolean isFruity) {
		this.isFruity = isFruity;
	}
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	
}
