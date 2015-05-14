package garbage.robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class PathMaker {
	private static int robotX;
	private static int robotY;
	private static char robotDirection;
	private static LinkedList<State> openedStates;
	private static Queue<State> closedStates;
	private static Stain currentStain;
	private static Stack<State> stateStack;
	private static LinkedList<Move> moves;
	// private static State[][] tabStates;

	private final static char N = 'N';
	private final static char W = 'W';
	private final static char E = 'E';
	private final static char S = 'S';

	public static LinkedList<Move> makePath(int parRobotX, int parRobotY,
			char parRobotDirection, int endX, int endY) {
		stateStack = new Stack<State>();
		moves = new LinkedList<Move>();
		State openedState;
		currentStain = new Stain();
		currentStain.setXPos(endX);
		currentStain.setYPos(endY);
		robotX = parRobotX;
		robotY = parRobotY;
		robotDirection = parRobotDirection;
		openedStates = new LinkedList<State>();
		closedStates = new LinkedList<State>();
		State startState = new State(robotX, robotY, robotDirection, null);
		startState.setH(0);
		startState.setG(0);
		startState.setF();
		openedStates.add(startState);
		State currentState = null;
		boolean finish = false;
		while (!openedStates.isEmpty() && !finish) {
			currentState = openedStates.poll();
			closedStates.add(currentState);
			if ((currentState.getX() == currentStain.getXPos())
					&& (currentState.getY() == currentStain.getYPos())) {
				generatePathStack(currentState);
				generateMoves();
//				testMoves();
				return moves;
			}

			for (State state : successors(currentState)) {
				if (openedStates.isEmpty()) {
					openedStates.add(state);
				} else {
					if ((openedState = isOpened(state.getX(), state.getY(),
							state.getDirection())) != null) {
						if (openedState.getG() < state.getG()) {
							state = openedState;
						}
					}
					boolean added = false;
					for (int i = 0; i < openedStates.size(); i++) {
						if (openedStates.get(i).getF() >= state.getF()) {
							openedStates.add(i, state);
							added = true;
							break;
						}
					}
					if (!added)
						openedStates.add(state);
				}
			}
		}
		return null;
	}

	private static void testMoves() {
		for (Move move : moves) {
			if (move == Move.GO)
				System.out.println("GO!");
			if (move == Move.LEFT)
				System.out.println("LEFT!");
			if (move == Move.RIGHT)
				System.out.println("RIGHT!");
		}
	}

	private static void generateMoves() {
		State first, second;

		first = stateStack.pop();
		while (!stateStack.isEmpty()) {
			second = stateStack.pop();
			moves.add(makeMoveFromStates(first, second));
			first = second;
		}
	}

	private static Move makeMoveFromStates(State first, State second) {
		Move ret = null;
		if ((first.getX() == second.getX()) && (first.getY() == second.getY())) {
			switch (first.getDirection()) {
			case N: {
				if (second.getDirection() == W)
					ret = Move.LEFT;
				if (second.getDirection() == E)
					ret = Move.RIGHT;
				break;
			}
			case W: {
				if (second.getDirection() == S)
					ret = Move.LEFT;
				if (second.getDirection() == N)
					ret = Move.RIGHT;
				break;
			}
			case S: {
				if (second.getDirection() == E)
					ret = Move.LEFT;
				if (second.getDirection() == W)
					ret = Move.RIGHT;
				break;
			}
			case E: {
				if (second.getDirection() == N)
					ret = Move.LEFT;
				if (second.getDirection() == S)
					ret = Move.RIGHT;
				break;
			}
			}
		} else {
			ret = Move.GO;
		}
		return ret;

	}

	private static void generatePathStack(State currentState) {

		if (currentState.getParent() != null) {
			stateStack.push(currentState);
			generatePathStack(currentState.getParent());
		} else {
			stateStack.push(currentState);
		}
	}

	private static List<State> successors(State currentState) {
		ArrayList<State> ret = new ArrayList<State>();

		switch (currentState.getDirection()) {
		case N: {
			// lewo
			if (!isClosed(currentState.getX(), currentState.getY(), W)) {
				State state = new State(currentState.getX(),
						currentState.getY(), W, currentState);
				state.setG(currentState.getG() + 2);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			// prawo
			if (!isClosed(currentState.getX(), currentState.getY(), E)) {
				State state = new State(currentState.getX(),
						currentState.getY(), E, currentState);
				state.setG(currentState.getG() + 2);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			// prosto
			if (!isClosed(currentState.getX(), currentState.getY() - 1, N)) {
				State state = new State(currentState.getX(),
						currentState.getY() - 1, N, currentState);
				state.setG(currentState.getG() + 10);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			break;
		}
		case W: {
			// lewo
			if (!isClosed(currentState.getX(), currentState.getY(), S)) {
				State state = new State(currentState.getX(),
						currentState.getY(), S, currentState);
				state.setG(currentState.getG() + 2);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			// prawo
			if (!isClosed(currentState.getX(), currentState.getY(), N)) {
				State state = new State(currentState.getX(),
						currentState.getY(), N, currentState);
				state.setG(currentState.getG() + 2);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			// prosto
			if (!isClosed(currentState.getX() - 1, currentState.getY(), W)) {
				State state = new State(currentState.getX() - 1,
						currentState.getY(), W, currentState);
				state.setG(currentState.getG() + 10);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			break;
		}
		case S: {
			// lewo
			if (!isClosed(currentState.getX(), currentState.getY(), E)) {
				State state = new State(currentState.getX(),
						currentState.getY(), E, currentState);
				state.setG(currentState.getG() + 2);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			// prawo
			if (!isClosed(currentState.getX(), currentState.getY(), W)) {
				State state = new State(currentState.getX(),
						currentState.getY(), W, currentState);
				state.setG(currentState.getG() + 2);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			// prosto
			if (!isClosed(currentState.getX(), currentState.getY() + 1, S)) {
				State state = new State(currentState.getX(),
						currentState.getY() + 1, S, currentState);
				state.setG(currentState.getG() + 10);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			break;
		}
		case E: {
			// lewo
			if (!isClosed(currentState.getX(), currentState.getY(), N)) {
				State state = new State(currentState.getX(),
						currentState.getY(), N, currentState);
				state.setG(currentState.getG() + 2);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			// prawo
			if (!isClosed(currentState.getX(), currentState.getY(), S)) {
				State state = new State(currentState.getX(),
						currentState.getY(), S, currentState);
				state.setG(currentState.getG() + 2);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			// prosto
			if (!isClosed(currentState.getX() + 1, currentState.getY(), E)) {
				State state = new State(currentState.getX() + 1,
						currentState.getY(), E, currentState);
				state.setG(currentState.getG() + 10);
				state.setH(currentStain);
				state.setF();
				ret.add(state);
			}
			break;
		}
		}
		return ret;
	}

	private static boolean isClosed(int x, int y, char direction) {

		if (GarbageRobot.getMapTab()[y][x] == '1')
			return true;
		else {
			for (State state : closedStates) {
				if (state.equate(x, y, direction)) {
					return true;
				}
			}
			return false;
		}
	}

	private static State isOpened(int x, int y, char direction) {
		int i = 0;
		for (State state : openedStates) {
			if (state.equate(x, y, direction)) {
				return openedStates.remove(i);
			}
			i++;
		}
		return null;
	}

}
