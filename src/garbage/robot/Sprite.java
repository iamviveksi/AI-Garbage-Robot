package garbage.robot;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite {
	private Animation sprite, up, down, left, right; // sprites
	private char direction;
	private float stepVal;
	private int xMap;
	private int yMap;
	private float xDisp;
	private float yDisp;
	private Move currentMove;
	private int xBase;
	private int yBase;
	private boolean isMoving = false;
	private boolean isWalking = false;
	private boolean isInBase = false;

	public void init(int xMap, int yMap) throws SlickException {
		this.xBase = xMap;
		this.yBase = yMap;
		this.xMap = xMap;
		this.yMap = yMap;
		xDisp = 32 * xMap;
		yDisp = 32 * yMap;
		this.setAnimations(100, 100);
	}

	private void setAnimations(int duration1, int duration2)
			throws SlickException {
		Image[] movementUp = { new Image("data/wmg1_bk1.png"),
				new Image("data/wmg1_bk2.png") };
		Image[] movementDown = { new Image("data/wmg1_fr1.png"),
				new Image("data/wmg1_fr2.png") };
		Image[] movementLeft = { new Image("data/wmg1_lf1.png"),
				new Image("data/wmg1_lf2.png") };
		Image[] movementRight = { new Image("data/wmg1_rt1.png"),
				new Image("data/wmg1_rt2.png") };

		// draw image1 every 100ms and image2 every 100ms too
		int[] duration = { duration1, duration2 };

		// set sprites (images, durations, auto-manual-mode-refreshing)
		this.up = new Animation(movementUp, duration, false);
		this.down = new Animation(movementDown, duration, false);
		this.left = new Animation(movementLeft, duration, false);
		this.right = new Animation(movementRight, duration, false);
		this.sprite = down; // main orientation
		this.direction = 'S';
	}

	public Move getCurrentMove() {
		return currentMove;
	}

	public void setCurrentMove(Move currentMove) {
		this.currentMove = currentMove;
	}

	public void setSpriteUp() {
		sprite = up;
		this.direction = 'N';
	}

	public void setSpriteDown() {
		sprite = down;
		this.direction = 'S';
	}

	public void setSpriteRight() {
		sprite = right;
		this.direction = 'E';
	}

	public void setSpriteLeft() {
		sprite = left;
		this.direction = 'W';
	}

	public char getDirection() {
		return direction;
	}

	public Animation getSprite() {
		return sprite;
	}

	public void setSprite(Animation sprite) {
		this.sprite = sprite;
	}

	public Animation getUp() {
		return up;
	}

	public void setUp(Animation up) {
		this.up = up;
	}

	public Animation getDown() {
		return down;
	}

	public void setDown(Animation down) {
		this.down = down;
	}

	public Animation getLeft() {
		return left;
	}

	public void setLeft(Animation left) {
		this.left = left;
	}

	public Animation getRight() {
		return right;
	}

	public void setRight(Animation right) {
		this.right = right;
	}

	public float getStepVal() {
		return stepVal;
	}

	public void setStepVal(float stepVal) {
		this.stepVal = stepVal;
	}

	public int getXMap() {
		return xMap;
	}

	public void setXMap(int xMap) {
		this.xMap = xMap;
	}

	public int getYMap() {
		return yMap;
	}

	public void setYMap(int yMap) {
		this.yMap = yMap;
	}

	public float getXDisp() {
		return xDisp;
	}

	public void setXDisp(float xDisp) {
		this.xDisp = xDisp;
	}

	public float getYDisp() {
		return yDisp;
	}

	public void setYDisp(float yDisp) {
		this.yDisp = yDisp;
	}

	public int getXBase() {
		return xBase;
	}

	public int getYBase() {
		return yBase;
	}

	public boolean isWalking() {
		return isWalking;
	}

	public void setWalking(boolean isWalking) {
		this.isWalking = isWalking;
	}

	public boolean isInBase() {
		return isInBase;
	}

	public void setInBase(boolean isInBase) {
		this.isInBase = isInBase;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public void turnLeft() {
		switch (this.direction) {
		case 'N': {
			this.setSpriteLeft();
			break;
		}
		case 'W': {
			this.setSpriteDown();
			break;
		}
		case 'S': {
			this.setSpriteRight();
			break;
		}
		case 'E': {
			this.setSpriteUp();
			break;
		}
		}
	}

	public void turnRight() {
		switch (this.direction) {
		case 'N': {
			this.setSpriteRight();
			break;
		}
		case 'W': {
			this.setSpriteUp();
			break;
		}
		case 'S': {
			this.setSpriteLeft();
			break;
		}
		case 'E': {
			this.setSpriteDown();
			break;
		}
		}
	}
}
