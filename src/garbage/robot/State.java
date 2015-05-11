package garbage.robot;

public class State {
	int x;
	int y;
	float distance;
	char direction;
	private final String NORTH = "NORTH";
	private final String WEST = "WEST";
	private final String EAST = "EAST";
	private final String SOUTH = "SOUTH";
	
	
	
	
	public State(int x, int y, char direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	} 
	
	
	public void calculateDistance(int parX, int parY){
		 distance = (float) Math.sqrt( (parX - x) * (parX - x) + (parY - y) * (parY - y));
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
	public float getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public char getDirection() {
		return direction;
	}
	public void setDirection(char direction) {
		this.direction = direction;
	}
	
}
