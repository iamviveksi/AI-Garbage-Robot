package garbage.robot;

import garbage.robot.AStar.Cleaning.StainGrid;

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
	private int roughness;
	private int dangerousBacteries;
	private boolean isTall;
	private boolean isFruity;
	private int density;
	private String type;
	private String base;
	private int age;
	private int baseState;
	private int chemicals;
	private boolean isPoisonous;
	private String tool;
	private String detergent;
	private String image;
	private StainGrid dirtyGrid;

	// constructor

	public Stain() {
		drawParams();
		dirtyGrid = new StainGrid();
	}

	public Stain(int xPos, int yPos, int wetness, int colorIntensity,
			int smellIntensity, boolean isSticky, int size, boolean isDried,
			boolean isGreasy, int roughness, int dangerousBacteries,
			boolean isTall, boolean isFruity, int density, String type, String base, int age, int chemicals, int baseState, boolean isPoisonous, String tool, String detergent, String image) {
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
		this.roughness = roughness;
		this.dangerousBacteries = dangerousBacteries;
		this.isTall = isTall;
		this.isFruity = isFruity;
		this.density = density;
		this.type = type;
		this.base = base;
		this.age = age;
		this.chemicals = chemicals;
		this.baseState = baseState;
		this.isPoisonous = isPoisonous;
		this.tool = tool;
		this.detergent = detergent;
		this.image = image;
	}


	// method draw params for pain
	private void drawParams() {
		Random generator = new Random();
		this.wetness = generator.nextInt(100);
		this.colorIntensity = generator.nextInt(100);
		this.smellIntensity = generator.nextInt(100);
		this.isSticky = generator.nextBoolean();
		this.size = generator.nextInt(100);
		this.isDried = generator.nextBoolean();
		this.isGreasy = generator.nextBoolean();
		this.roughness = generator.nextInt(100);
		this.dangerousBacteries = generator.nextInt(100);
		this.isTall = generator.nextBoolean();
		this.isFruity = generator.nextBoolean();
		this.density = generator.nextInt(100);
		this.base = "floor";
		this.age = generator.nextInt(100);
		this.type = "???";
		this.tool = "???";
		this.detergent = "???";
		this.baseState = generator.nextInt(100);
		this.chemicals = generator.nextInt(100);
		this.isPoisonous = generator.nextBoolean();
		this.image = "data/stain.png";
	}
	
	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public String getDetergent() {
		return detergent;
	}

	public void setDetergent(String detergent) {
		this.detergent = detergent;
	}

	@Override
	public String toString() {
		return "Stain [xPos=" + xPos + ", yPos=" + yPos + ", wetness="
				+ wetness + ", colorIntensity=" + colorIntensity
				+ ", smellIntensity=" + smellIntensity + ", isSticky="
				+ isSticky + ", size=" + size + ", isDried=" + isDried
				+ ", isGreasy=" + isGreasy + ", roughness=" + roughness
				+ ", dangerousBacteries=" + dangerousBacteries + ", isTall="
				+ isTall + ", isFruity=" + isFruity + ", density=" + density
				+ "]";
	}

	// getters and setters
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

	public int getRoughness() {
		return roughness;
	}

	public void setRoughness(int roughness) {
		this.roughness = roughness;
	}

	public int getDangerousBacteries() {
		return dangerousBacteries;
	}

	public void setDangerousBacteries(int dangerousBacteries) {
		this.dangerousBacteries = dangerousBacteries;
	}

	public boolean isTall() {
		return isTall;
	}

	public void setTall(boolean isTall) {
		this.isTall = isTall;
	}

	public boolean isFruity() {
		return isFruity;
	}

	public void setFruity(boolean isFruity) {
		this.isFruity = isFruity;
	}

	public int getXPos() {
		return xPos;
	}

	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public int getBaseState() {
		return baseState;
	}

	public void setBaseState(int baseState) {
		this.baseState = baseState;
	}

	public int getChemicals() {
		return chemicals;
	}

	public void setChemicals(int chemicals) {
		this.chemicals = chemicals;
	}

	public boolean isPoisonous() {
		return isPoisonous;
	}

	public void setPoisonous(boolean isPoisonous) {
		this.isPoisonous = isPoisonous;
	}

	public String getType() {
		return type;
	}

	public void setType(String t) {
		this.type = t;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String i) {
		this.image = i;
	}
}
