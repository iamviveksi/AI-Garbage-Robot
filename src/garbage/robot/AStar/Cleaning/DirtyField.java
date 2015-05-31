package garbage.robot.AStar.Cleaning;

public class DirtyField {
	private int x;
	private int y;
	private int H;
	
	public DirtyField(int x, int y, int h) {
		this.x = x;
		this.y = y;
		H = h;
	}
	
	public DirtyField() {
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getH() {
		return H;
	}
	public void setH(int h) {
		H = h;
	}
	
	
	
}
