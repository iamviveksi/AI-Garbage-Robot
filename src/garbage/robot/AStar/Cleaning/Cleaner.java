package garbage.robot.AStar.Cleaning;

import garbage.robot.State;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Cleaner {

	private static int robotX;
	private static int robotY;
	private static char robotDirection;
	private static LinkedList<State> openedStates;
	private static LinkedList<State> closedStates;
	private static StainGrid grid;

	private final static char N = 'N';
	private final static char W = 'W';
	private final static char E = 'E';
	private final static char S = 'S';

	private Cleaner() {

	}

	public static void clean(StainGrid parGrid) {
		grid = parGrid;
		robotX = grid.getSIZE_X() / 2;
		robotY = grid.getSIZE_Y() / 2;
		robotDirection = N;
		openedStates = new LinkedList<State>();
		closedStates = new LinkedList<State>();

		int uncleaned = grid.getDirtyFields();

		while (uncleaned > 0) {
			State tempState = new State(robotX, robotY, robotDirection, null);
			DirtyField purpose = findPurpose();
			uncleaned--;
			grid.cleanField(purpose.getX(), purpose.getY());
			robotX = purpose.getX();
			robotY = purpose.getY();
			System.out.println("X: " + purpose.getX() + " Y: " + purpose.getY()
					+ " H: " + purpose.getH());
			/*
			 * openedStates.push(tempState); boolean finish = false;
			 * while(!openedStates.isEmpty() && !finish){ State currentState =
			 * openedStates.poll(); closedStates.push(currentState);
			 * if(grid.getField(currentState.getX(), currentState.getY()) ==
			 * '1'){ uncleaned--; grid.cleanField(currentState.getX(),
			 * currentState.getY()); //TODO dodac ruchy do kontenera je
			 * przechowujacego :) } }
			 */
		}

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
				int verge = (robotY - dist) >= 0 ? (robotY - dist) : 0;
				try {
					if (grid.getField(i, verge) == '1') {
						tempField = new DirtyField();
						tempField.setX(i);
						tempField.setY(verge);
						tempField.setH(calculateDistance(robotX, robotY, i,
								verge));
						dirtyFields.add(tempField);
						isStainFound = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("blad gora x:" + i + " y: "
							+ verge);
				}
			}
			for (int i = (robotY - dist >= 0 ? robotY - dist : 0); i <= (robotY
					+ dist < grid.getSIZE_Y() - 1 ? robotY + dist : grid
					.getSIZE_Y() - 1); i++) {
				int verge = (robotX + dist) <grid.getSIZE_X() ? (robotX + dist) : grid.getSIZE_X()-1;
				try {
					if (grid.getField(verge, i) == '1') {
						tempField = new DirtyField();
						tempField.setX(verge);
						tempField.setY(i);
						tempField.setH(calculateDistance(robotX, robotY, verge, i));
						dirtyFields.add(tempField);
						isStainFound = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("blad prawo x:" + (verge)
							+ " y: " + i);
				}
			}
			for (int i = (robotX - dist >= 0 ? robotX - dist : 0); i <= (robotX
					+ dist < grid.getSIZE_X() - 1 ? robotX + dist : grid
					.getSIZE_X() - 1); i++) {
					int verge = (robotY + dist) <grid.getSIZE_Y() ? (robotY + dist) : grid.getSIZE_Y()-1;
				try {
					if (grid.getField(i, verge) == '1') {
						tempField = new DirtyField();
						tempField.setX(i);
						tempField.setY(verge);
						tempField.setH(calculateDistance(robotX, robotY, i,
								verge));
						dirtyFields.add(tempField);
						isStainFound = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("blad dol x:" + i + " y: "
							+ verge);
				}
			}
			for (int i = (robotY - dist >= 0 ? robotY - dist : 0); i <= (robotY
					+ dist < grid.getSIZE_Y() - 1 ? robotY + dist : grid
					.getSIZE_Y() - 1); i++) {
				int verge = (robotX - dist) >= 0 ? (robotX - dist) : 0;
				try {
					
					if (grid.getField(verge, i) == '1') {
						tempField = new DirtyField();
						tempField.setX(verge);
						tempField.setY(i);
						tempField.setH(calculateDistance(robotX, robotY, verge,
								i));
						dirtyFields.add(tempField);
						isStainFound = true;
					}
				} catch (IndexOutOfBoundsException e) {
					System.err.println("blad lewo x:" + (verge) + " y: " + i);
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
