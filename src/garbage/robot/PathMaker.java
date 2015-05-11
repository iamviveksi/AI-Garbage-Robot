package garbage.robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PathMaker {
	int robotX;
	int robotY;
	private List<Stain> stainList;
	private Queue<Stain> stainQueue;
	
	public PathMaker(int robotX, int robotY, List<Stain> stainList) {
		super();
		this.robotX = robotX;
		this.robotY = robotY;
		this.stainList = new ArrayList<Stain>();
		for(Stain stain  : stainList){
			this.stainList.add(stain);
		}
		this.sortStains();
	}
	
	private void sortStains(){
		stainQueue = new LinkedList<Stain>();
		int actX = robotX;
		int actY = robotY;
		int minIndex;
		float minDistance;
		while(!stainList.isEmpty()){
			minIndex = 0;
			minDistance = distance(actX, actY, stainList.get(0).getXPos(), stainList.get(0).getYPos());
			for(int i = 1; i<stainList.size(); i++){
				if(minDistance > distance(actX, actY, stainList.get(i).getXPos(), stainList.get(i).getYPos())){
					minDistance = distance(actX, actY, stainList.get(i).getXPos(), stainList.get(i).getYPos());
					minIndex = i;
				}
			}
			actX = stainList.get(minIndex).getXPos();
			actY = stainList.get(minIndex).getYPos();
			stainQueue.add(stainList.remove(minIndex));
		}
		for(Stain stain : stainQueue){
			System.out.println("x: " + stain.getXPos() + " y: " + stain.getYPos());
		}
	}
	
	private void successor(){
		//TODO
	}
	
	private float distance(int x1, int y1, int x2, int y2){
		return (float) Math.sqrt( (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

}
