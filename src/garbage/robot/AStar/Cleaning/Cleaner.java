package garbage.robot.AStar.Cleaning;

import garbage.robot.State;

import java.util.LinkedList;
import java.util.Queue;

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
			openedStates.push(tempState);
			boolean finish = false;
			while(!openedStates.isEmpty() && !finish){
				State currentState = openedStates.poll();
				closedStates.push(currentState);
				if(grid.getField(currentState.getX(), currentState.getY()) == '1'){
					uncleaned--;
					grid.cleanField(currentState.getX(), currentState.getY());
					//TODO dodac ruchy do kontenera je przechowujacego  :)
				}
			}
		}

	}
	
	
	
}
