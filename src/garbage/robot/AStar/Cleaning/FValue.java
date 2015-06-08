package garbage.robot.AStar.Cleaning;

public class FValue {

	private int fN;
	private int fE;
	private int fS;
	private int fW;

	public FValue() {
		fN = 0;
		fE = 0;
		fW = 0;
		fS = 0;
	}

	public int getfN() {
		return fN;
	}

	public void setfN(int fN) {
		this.fN = fN;
	}

	public int getfE() {
		return fE;
	}

	public void setfE(int fE) {
		this.fE = fE;
	}

	public int getfS() {
		return fS;
	}

	public void setfS(int fS) {
		this.fS = fS;
	}

	public int getfW() {
		return fW;
	}

	public void setfW(int fW) {
		this.fW = fW;
	}

	public int getValue(char direction) {
		switch(direction){
		case 'N':
			return fN;
		case 'S':
			return fS;
		case 'W':
			return fW;
		case 'E':
			return fE;
		default :
			return -2;
		}
	}

}
