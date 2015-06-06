package garbage.robot;

import garbage.robot.AStar.Cleaning.DirtyField;

public class State {
	private int x;
	private int y;
	private int H;
	private int G;
	private int F;
	private char direction;
	private State Parent;

	public State(int x, int y, char direction, State parent) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		Parent = parent;
	}

	public State(int F, int G) {
		this.F = F;
		this.G = G;
	}

	public State(State state) {
		this.x = state.getX();
		this.y = state.getY();
		this.H = state.getH();
		this.G = state.getG();
		this.F = state.getF();
		this.direction = state.getDirection();
		this.Parent = state.getParent();
	}

	public boolean equate(int pX, int pY, char pDirection) {
		if ((this.x == pX) && (this.y == pY) && (this.direction == pDirection))
			return true;
		else
			return false;
	}
	
	public boolean equate(State parState){
		if (this.x == parState.getX() && this.y == parState.getY() && this.direction == parState.getDirection())
			return true;
		else
			return false;
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

	public void setH(Stain stain) {
		H = Math.abs(stain.getXPos() - this.x)
				+ Math.abs(stain.getYPos() - this.y) * 10;
	}
	
	public void setH(DirtyField dirtyField) {
		H = Math.abs(dirtyField.getX() - this.x)
				+ Math.abs(dirtyField.getY() - this.y) * 5;
	}

	public int getG() {
		return G;
	}

	public void setG(int g) {
		G = g;
	}

	public int getF() {
		return F;
	}

	public void setF() {
		F = H + G;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public State getParent() {
		return Parent;
	}

	public void setParent(State parent) {
		Parent = parent;
	}

}
