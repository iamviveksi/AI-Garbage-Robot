package garbage.robot.AStar.Cleaning;

import garbage.robot.AStar.Cleaning.*;
import garbage.robot.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class Cleaner {

	private static int robotX;
	private static int robotY;
	private static char robotDirection;
	private static LinkedList<State> openedStates;
	// private static LinkedList<State> closedStates;
	private static StainGrid grid;
	private static DirtyField purpose;
	private static LinkedList<Move> moves;
	private static Stack<Move> tempStack;
	private static FValue[][] tabFValues;

	private final static char N = 'N';
	private final static char W = 'W';
	private final static char E = 'E';
	private final static char S = 'S';

	private Cleaner() {

	}

	public static LinkedList<Move> clean(StainGrid parGrid) {

		grid = parGrid;
		tabFValues = new FValue[grid.getSIZE_Y()][grid.getSIZE_X()];

		// PrintWriter zapis = null;
		// try {
		// zapis = new PrintWriter("log.txt");
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		robotX = grid.getSIZE_X() / 2;
		robotY = grid.getSIZE_Y() / 2;
		robotDirection = N;
		openedStates = new LinkedList<State>();
		// closedStates = new LinkedList<State>();
		moves = new LinkedList<Move>();
		State currentState = null;

		int uncleaned = grid.getDirtyFields();

		while (uncleaned > 0) {

			clearFValuesTab();
			openedStates.clear();
			// closedStates.clear();
			tempStack = new Stack<Move>();
			State tempState = new State(robotX, robotY, robotDirection, null);

			purpose = findPurpose();
			tempState.setG(0);
			tempState.setH(purpose);
			tempState.setF();
			// uncleaned--;
			// zapis.println("robotX: " + robotX + " robotY: " + robotY
			// + " direction: " + robotDirection);
			// zapis.println("purposeX: " + purpose.getX() + " purposeY: "
			// + purpose.getY() + "\n");
			openedStates.push(tempState);
			setFValue(tempState);
			boolean finish = false;

			while (!openedStates.isEmpty() && !finish) {
				currentState = openedStates.poll();
				// closedStates.push(currentState);
				setFValueToClose(currentState);
				// znalezlismy
				if (currentState.getX() == purpose.getX()
						&& currentState.getY() == purpose.getY()) {
					uncleaned--;
					grid.cleanField(currentState.getX(), currentState.getY()); 
					robotX = currentState.getX();
					robotY = currentState.getY();
					robotDirection = currentState.getDirection();
					addMovesToStack(currentState.getParent(), currentState);
					stackToQueue();
					finish = true;
				} else {
					ArrayList<State> successors = makeSuccessors(currentState);
					for (State state : successors) {
						addStateToOpenList(state);
					}
				}
			}
			if (!finish) {
				uncleaned--;
				robotX = tempState.getX();
				robotY = tempState.getY();
				robotDirection = tempState.getDirection();
				// zapis.println("NIE ZNALAZLEM!!!!!!!!!!!!!!!");
				grid.setField(purpose.getX(), purpose.getY(), '3');

			}
		}
		// zapis.close();
		return moves;
	}

	private static void addStateToOpenList(State state) {

		int placeToPush = -1; // wynik
		int pb = 0; // wskaznik na poczatek
		int pe = openedStates.size() - 1; // wskaznik na koniec
		int pc; // wskaznik na srodek

		// nie ma do tej pory stanu w li�cie otwartych
		if (getFValue(state.getX(), state.getY(), state.getDirection()) == 0) {
			while (pb <= pe) {
				pc = (pb + pe) / 2;
				if ((pc + 1) < openedStates.size())
					if (state.getF() <= openedStates.get(pc).getF()
							&& state.getF() >= openedStates.get(pc + 1).getF()) {
						placeToPush = pc;
						break;
					}

				if (state.getF() < openedStates.get(pc).getF())
					pe = pc - 1;
				else
					pb = pc + 1;
			}
			if (placeToPush == -1)
				openedStates.add(state);
			else
				openedStates.add(placeToPush, state);

			// stan jest juz w otwartych i trzeba go znalezc i ew zaktualizowac
		} else {
			while (pb <= pe) {
				pc = (pb + pe) / 2;

				if (state.getF() == openedStates.get(pc).getF()) {
					placeToPush = pc;
					break;
				}

				if (state.getF() < openedStates.get(pc).getF())
					pe = pc - 1;
				else
					pb = pc + 1;
			}
			int i = 0;
			boolean finish = false;
			while ((openedStates.get(placeToPush + i).getF() == state.getF())
					&& ((placeToPush - i) >= 0)) {
				if (openedStates.get(placeToPush + i).equate(state)) {
					if (openedStates.get(placeToPush + i).getG() > state.getG()) {
						openedStates.set(placeToPush + i, state);
					}
					finish = true;
					break;
				}
				i--;
			}
			i = 1;
			while ((openedStates.get(placeToPush + i).getF() == state.getF() && (placeToPush + i) < openedStates
					.size()) && !finish) {
				if (openedStates.get(placeToPush + i).equate(state)) {
					if (openedStates.get(placeToPush + i).getG() > state.getG()) {
						openedStates.set(placeToPush + i, state);

					}
					break;
				}
				i++;
			}
		}
	}

	private static void setFValue(State parState) {
		switch (parState.getDirection()) {
		case 'N': {
			tabFValues[parState.getY()][parState.getX()].setfN(parState.getF());
			break;
		}
		case 'S': {
			tabFValues[parState.getY()][parState.getX()].setfS(parState.getF());
			break;
		}
		case 'W': {
			tabFValues[parState.getY()][parState.getX()].setfW(parState.getF());
			break;
		}
		case 'E': {
			tabFValues[parState.getY()][parState.getX()].setfE(parState.getF());
			break;
		}
		}
	}

	private static void setFValueToClose(State parState) {
		switch (parState.getDirection()) {
		case 'N': {
			tabFValues[parState.getY()][parState.getX()].setfN(-1);
			break;
		}
		case 'S': {
			tabFValues[parState.getY()][parState.getX()].setfS(-1);
			break;
		}
		case 'W': {
			tabFValues[parState.getY()][parState.getX()].setfW(-1);
			break;
		}
		case 'E': {
			tabFValues[parState.getY()][parState.getX()].setfE(-1);
			break;
		}
		}
	}

	private static void clearFValuesTab() {
		for (int i = 0; i < grid.getSIZE_Y(); i++) {
			for (int j = 0; j < grid.getSIZE_X(); j++) {
				tabFValues[i][j] = new FValue();
			}
		}

	}

	private static void stackToQueue() {
		while (!tempStack.empty()) {
			moves.add(tempStack.pop());
		}
	}

	private static void addMovesToStack(State penultState, State lastState) {
		if (penultState == null) // niepotrzebne
			return;
		if (penultState.getParent() == null) {
			tempStack.push(generateMove(penultState, lastState));
			return;
		}
		tempStack.push(generateMove(penultState, lastState));
		addMovesToStack(penultState.getParent(), penultState);
	}

	private static Move generateMove(State firstState, State secondState) {
		if (firstState.getDirection() == secondState.getDirection()) {
			return Move.GO;
		} else {
			if (firstState.getDirection() == N) {
				if (secondState.getDirection() == W) {
					return Move.LEFT;
				} else {
					if (secondState.getDirection() == E)
						return Move.RIGHT;
					else
						return Move.COS;
				}
			}
			if (firstState.getDirection() == S) {
				if (secondState.getDirection() == E) {
					return Move.LEFT;
				} else {
					if (secondState.getDirection() == W)
						return Move.RIGHT;
					else
						return Move.COS;
				}
			}
			if (firstState.getDirection() == W) {
				if (secondState.getDirection() == S) {
					return Move.LEFT;
				} else {
					if (secondState.getDirection() == N)
						return Move.RIGHT;
					else
						return Move.COS;
				}
			}
			if (firstState.getDirection() == E) {
				if (secondState.getDirection() == N) {
					return Move.LEFT;
				} else {
					if (secondState.getDirection() == S)
						return Move.RIGHT;
					else
						return Move.COS;
				}
			}
		}
		return null;
	}

	private static ArrayList<State> makeSuccessors(State currentState) {
		ArrayList<State> result = new ArrayList<State>();

		switch (currentState.getDirection()) {
		case N: {
			// lewo
			if (!isClosed(currentState.getX(), currentState.getY(), W)) {
				State state = new State(currentState.getX(),
						currentState.getY(), W, currentState);
				state.setG(currentState.getG() + 3);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			// prawo
			if (!isClosed(currentState.getX(), currentState.getY(), E)) {
				State state = new State(currentState.getX(),
						currentState.getY(), E, currentState);
				state.setG(currentState.getG() + 3);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			// prosto
			if (!isClosed(currentState.getX(), currentState.getY() - 1, N)) {
				State state = new State(currentState.getX(),
						currentState.getY() - 1, N, currentState);
				state.setG(currentState.getG() + 5);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			break;
		}
		case W: {
			// lewo
			if (!isClosed(currentState.getX(), currentState.getY(), S)) {
				State state = new State(currentState.getX(),
						currentState.getY(), S, currentState);
				state.setG(currentState.getG() + 3);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			// prawo
			if (!isClosed(currentState.getX(), currentState.getY(), N)) {
				State state = new State(currentState.getX(),
						currentState.getY(), N, currentState);
				state.setG(currentState.getG() + 3);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			// prosto
			if (!isClosed(currentState.getX() - 1, currentState.getY(), W)) {
				State state = new State(currentState.getX() - 1,
						currentState.getY(), W, currentState);
				state.setG(currentState.getG() + 5);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			break;
		}
		case S: {
			// lewo
			if (!isClosed(currentState.getX(), currentState.getY(), E)) {
				State state = new State(currentState.getX(),
						currentState.getY(), E, currentState);
				state.setG(currentState.getG() + 3);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			// prawo
			if (!isClosed(currentState.getX(), currentState.getY(), W)) {
				State state = new State(currentState.getX(),
						currentState.getY(), W, currentState);
				state.setG(currentState.getG() + 3);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			// prosto
			if (!isClosed(currentState.getX(), currentState.getY() + 1, S)) {
				State state = new State(currentState.getX(),
						currentState.getY() + 1, S, currentState);
				state.setG(currentState.getG() + 5);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			break;
		}
		case E: {
			// lewo
			if (!isClosed(currentState.getX(), currentState.getY(), N)) {
				State state = new State(currentState.getX(),
						currentState.getY(), N, currentState);
				state.setG(currentState.getG() + 3);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			// prawo
			if (!isClosed(currentState.getX(), currentState.getY(), S)) {
				State state = new State(currentState.getX(),
						currentState.getY(), S, currentState);
				state.setG(currentState.getG() + 3);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			// prosto
			if (!isClosed(currentState.getX() + 1, currentState.getY(), E)) {
				State state = new State(currentState.getX() + 1,
						currentState.getY(), E, currentState);
				state.setG(currentState.getG() + 5);
				state.setH(purpose);
				state.setF();
				result.add(state);
			}
			break;
		}
		}
		return result;
	}

	private static boolean isClosed(int x, int y, char direction) {

		if (x < 0 || y < 0 || x >= grid.getSIZE_X() || y >= grid.getSIZE_Y())
			return true;
		if (grid.getField(x, y) == '2')
			return true;

		// sprawdza czy pole stan jest na liscie zamknietych
		if (getFValue(x, y, direction) == -1) {
			return true;
		}
		return false;
	}

	private static int getFValue(int x, int y, char direction) {
		return tabFValues[y][x].getValue(direction);
	}

	private static int calculateDistance(int xA, int yA, int xB, int yB) {
		return Math.abs(xA - xB) + Math.abs(yA - yB);
	}

	private static DirtyField findPurpose() {
		Vector<DirtyField> dirtyFields = new Vector<DirtyField>();
		DirtyField tempField = new DirtyField();
		boolean isStainFound = false;
		int dist = 1;
		if (grid.getField(robotX, robotY) == '1') {
			tempField = new DirtyField();
			tempField.setX(robotX);
			tempField.setY(robotY);
			tempField.setH(0);
			return tempField;
		}
		while (!isStainFound) {
			for (int i = (robotX - dist >= 0 ? robotX - dist : 0); i <= (robotX
					+ dist < grid.getSIZE_X() - 1 ? robotX + dist : grid
					.getSIZE_X() - 1); i++) {
				int edge = (robotY - dist) >= 0 ? (robotY - dist) : 0;
				try {
					if (grid.getField(i, edge) == '1') {
						tempField = new DirtyField();
						tempField.setX(i);
						tempField.setY(edge);
						tempField.setH(calculateDistance(robotX, robotY, i,
								edge));
						dirtyFields.add(tempField);
						isStainFound = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("blad gora x:" + i + " y: " + edge);
				}
			}
			for (int i = (robotY - dist >= 0 ? robotY - dist : 0); i <= (robotY
					+ dist < grid.getSIZE_Y() - 1 ? robotY + dist : grid
					.getSIZE_Y() - 1); i++) {
				int edge = (robotX + dist) < grid.getSIZE_X() ? (robotX + dist)
						: grid.getSIZE_X() - 1;
				try {
					if (grid.getField(edge, i) == '1') {
						tempField = new DirtyField();
						tempField.setX(edge);
						tempField.setY(i);
						tempField.setH(calculateDistance(robotX, robotY, edge,
								i));
						dirtyFields.add(tempField);
						isStainFound = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("blad prawo x:" + (edge) + " y: " + i);
				}
			}
			for (int i = (robotX - dist >= 0 ? robotX - dist : 0); i <= (robotX
					+ dist < grid.getSIZE_X() - 1 ? robotX + dist : grid
					.getSIZE_X() - 1); i++) {
				int edge = (robotY + dist) < grid.getSIZE_Y() ? (robotY + dist)
						: grid.getSIZE_Y() - 1;
				try {
					if (grid.getField(i, edge) == '1') {
						tempField = new DirtyField();
						tempField.setX(i);
						tempField.setY(edge);
						tempField.setH(calculateDistance(robotX, robotY, i,
								edge));
						dirtyFields.add(tempField);
						isStainFound = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("blad dol x:" + i + " y: " + edge);
				}
			}
			for (int i = (robotY - dist >= 0 ? robotY - dist : 0); i <= (robotY
					+ dist < grid.getSIZE_Y() - 1 ? robotY + dist : grid
					.getSIZE_Y() - 1); i++) {
				int edge = (robotX - dist) >= 0 ? (robotX - dist) : 0;
				try {

					if (grid.getField(edge, i) == '1') {
						tempField = new DirtyField();
						tempField.setX(edge);
						tempField.setY(i);
						tempField.setH(calculateDistance(robotX, robotY, edge,
								i));
						dirtyFields.add(tempField);
						isStainFound = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("blad lewo x:" + (edge) + " y: " + i);
				}

			}
			if (!isStainFound)
				dist++;
		}
		int minH = dirtyFields.get(0).getH();
		int minIndex = 0;

		for (int i = 1; i < dirtyFields.size(); i++) {
			if (dirtyFields.get(i).getH() < minH) {

				minH = dirtyFields.get(i).getH();
				minIndex = i;
			}
		}

		DirtyField result = dirtyFields.get(minIndex);
		dirtyFields.clear();
		return result;
	}
}
