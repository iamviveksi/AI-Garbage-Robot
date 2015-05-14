package garbage.robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

//need to extend BasicGame

public class GarbageRobot extends BasicGame {
	private Image floor;
	private Image obstacle;
	private static Sprite robot;
	private static final int TILE_SIZE = 32;
	private static int tilesX;
	private static int tilesY;
	private static char[][] mapTab;
	private float shiftY = 0;
	private float shiftX = 0;
	private Image stainPic;
	private static int numberOfStains;
	private static List<Stain> stainList;

	private static List<Stain> unvisitedStains;
	private static LinkedList<Move> movesList;
	private static boolean isMoving = false;
	private static boolean isWalking = false;
	private Weka weka = null; 
	public GarbageRobot() {
		// text in the main window
		super("Garbage Robot AI");
	}

	// start the GAME!


	public static void main(String[] arguments) throws SlickException {
		robot = new Sprite();

		readMapFromFile();
		generateStains();

		try {
			// set AppGameConteiner and start it
			AppGameContainer app = new AppGameContainer(new GarbageRobot());
			app.setDisplayMode(1324, 640, false);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	// method put stains on room.
	public static void generateStains() throws SlickException {
		Stain tempStain;
		stainList = new ArrayList<Stain>();
		unvisitedStains = new ArrayList<Stain>();
		int xStain, yStain;
		boolean isOk = false;
		Random generator = new Random();
		for (int i = 1; i <= numberOfStains; i++) {
			isOk = false;
			while (!isOk) {
				xStain = generator.nextInt(tilesX);
				yStain = generator.nextInt(tilesY);
				if (mapTab[yStain][xStain] != '1'
						&& mapTab[yStain][xStain] != 'S') {
					mapTab[yStain][xStain] = 'S';
					isOk = true;
					tempStain = new Stain();
					tempStain.setXPos(xStain);
					tempStain.setYPos(yStain);
					stainList.add(tempStain);
					unvisitedStains.add(tempStain);
				}
			}
		}
	}

	// method read params from conf.prp and map from file
	public static void readMapFromFile() {
		PropertiesSupport propertiesSupport = new PropertiesSupport();
		propertiesSupport.load();
		tilesX = Integer.parseInt(propertiesSupport.getProperty("map_x"));
		tilesY = Integer.parseInt(propertiesSupport.getProperty("map_y"));
		robot.setStepVal(Float.parseFloat(propertiesSupport
				.getProperty("stepVal")));
		numberOfStains = Integer.parseInt(propertiesSupport
				.getProperty("stainsOnRoom"));
		MapReader mapReader = new MapReader(tilesX, tilesY);
		mapReader.loadMapFromFile(propertiesSupport.getProperty("map_path"));

		mapTab = mapReader.getMapTab();
	}

	// method draw elements on screen
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		// floor

		for (int i = 0; i < 1024; i += 128)
			for (int j = 0; j < 640; j += 128)
				g.drawImage(floor, i, j);

		for (int i = 0; i < tilesX; i++)
			for (int j = 0; j < tilesY; j++) {
				if (mapTab[j][i] == '1') {
					g.drawImage(obstacle, i * 32, j * 32);
				}
				if (mapTab[j][i] == 'S') {
					Stain stain = getStainByPosition(i, j);
					Image image = new Image(stain.getImage());
					g.drawImage(image, i * 32, j * 32); 
				}
			}
		robot.getSprite();
		robot.getXDisp();
		robot.getYDisp();
		robot.getSprite().draw(robot.getXDisp(), robot.getYDisp());


		g.drawString("PosX: " + robot.getXDisp(), 1050f, 30f);
		g.drawString("PosY: " + robot.getYDisp(), 1050f, 50f);
		g.drawString("-------------", 1050f, 70f);
		if (mapTab[robot.getYMap()][robot.getXMap()] == 'S') {

			Stain actStain = getStainByPosition(robot.getXMap(), robot.getYMap());
			try {
				String classItem = weka.predictItem(actStain);
				actStain.setType(classItem);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			g.drawString("Wetness: " + actStain.getWetness(), 1050f, 90f);
			g.drawString("ColorIntensity: " + actStain.getColorIntensity(),
					1050f, 110f);
			g.drawString("SmellIntensity: " + actStain.getSmellIntensity(),
					1050f, 130f);
			g.drawString("Is Sticky?: " + actStain.isSticky(), 1050f, 150f);
			g.drawString("Size: " + actStain.getSize(), 1050f, 170f);
			g.drawString("Is Dried?: " + actStain.isDried(), 1050f, 190f);
			g.drawString("Is Greasy?: " + actStain.isGreasy(), 1050f, 210f);
			g.drawString("Roughness: " + actStain.getRoughness(), 1050f, 230f);
			g.drawString(
					"Dangerous Bacteries: " + actStain.getDangerousBacteries(),
					1050f, 250f);
			g.drawString("Height: " + actStain.getHeight(), 1050f, 270f);
			g.drawString("Is Fruity?: " + actStain.isFruity(), 1050f, 290f);
			g.drawString("Density: " + actStain.getDensity(), 1050f, 310f);
			g.drawString("Type: " + actStain.getType(), 1050f, 330f);
			
			//smth todo
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		try {
			weka = new Weka("poligon/data-learning.arff", "poligon/data-test.arff");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("INIT");
		robot.init(4, 4);

		// test Path
		// Stain testStain = unvisitedStains.get(0);
		Stain testStain = new Stain();
		testStain.setXPos(4);
		testStain.setYPos(10);
		System.out.println("CEL: x-" + testStain.getXPos() + " y-"
				+ testStain.getYPos());
		PathMaker.makePath(robot.getXMap(), robot.getYMap(),
				robot.getDirection(), testStain.getXPos(), testStain.getYPos());

		// /

		floor = new Image("data/grass.png");
		obstacle = new Image("data/smallRocks.png");
		stainPic = new Image("data/stain.png");
		// mapTab[yMap][xMap] = 2;
	}

	// method update needed informations
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();

		if (isMoving) {
			if (!isWalking) {
				if (!movesList.isEmpty()) {
					robot.setCurrentMove(movesList.poll());
					if (robot.getCurrentMove() == Move.LEFT) {
						robot.turnLeft();
						robot.getSprite().update(delta);
					}
					if (robot.getCurrentMove() == Move.RIGHT) {
						robot.turnRight();
						robot.getSprite().update(delta);
					}
					if (robot.getCurrentMove() == Move.GO) {
						isWalking = true;
					}
				} else {
					isMoving = false;
				}
			} else { // when robot isWalking
				switch (robot.getDirection()) {
				case 'N': {
					shiftY -= robot.getStepVal();
					robot.setXDisp(robot.getXMap() * TILE_SIZE);
					robot.setYDisp(robot.getYMap() * TILE_SIZE + shiftY);
					if (shiftY <= (-1 * TILE_SIZE)) {
						shiftY = 0;
						robot.setyMap(robot.getYMap() - 1);
						isWalking = false;
					}
					break;
				}
				case 'W': {
					shiftX -= robot.getStepVal();
					robot.setXDisp(robot.getXMap() * TILE_SIZE + shiftX);
					robot.setYDisp(robot.getYMap() * TILE_SIZE);
					if (shiftX <= (-1 * TILE_SIZE)) {
						shiftX = 0;
						robot.setxMap(robot.getXMap() - 1);
						isWalking = false;
					}
					break;
				}
				case 'S': {
					shiftY += robot.getStepVal();
					robot.setXDisp(robot.getXMap() * TILE_SIZE);
					robot.setYDisp(robot.getYMap() * TILE_SIZE + shiftY);
					if (shiftY >= TILE_SIZE) {
						shiftY = 0;
						robot.setyMap(robot.getYMap() + 1);
						isWalking = false;
					}
					break;
				}
				case 'E': {
					shiftX += robot.getStepVal();
					robot.setXDisp(robot.getXMap() * TILE_SIZE + shiftX);
					robot.setYDisp(robot.getYMap() * TILE_SIZE);
					if (shiftX >= TILE_SIZE) {
						shiftX = 0;
						robot.setxMap(robot.getXMap() + 1);
						isWalking = false;
					}
					break;
				}
				}
				robot.getSprite().update(delta);
			}

		} else if (input.isKeyDown(Input.KEY_G)) {
			isMoving = true;

			Stain endStain = getNearestStain();
			// Stain endStain = unvisitedStains.get(0);
			if (endStain != null)
				movesList = PathMaker.makePath(robot.getXMap(),
						robot.getYMap(), robot.getDirection(),
						endStain.getXPos(), endStain.getYPos());

		} else if (input.isKeyDown(Input.KEY_UP)) {
			robot.setSpriteUp();// set sprite
			robot.getSprite().update(delta);
			if (!(mapTab[robot.getYMap() - 1][robot.getXMap()] == '1')) {
				shiftY -= robot.getStepVal();
				robot.setXDisp(robot.getXMap() * TILE_SIZE);
				robot.setYDisp(robot.getYMap() * TILE_SIZE + shiftY);
				if (shiftY <= (-1 * TILE_SIZE)) {
					shiftY = 0;
					robot.setyMap(robot.getYMap() - 1);
				}
			}

		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			robot.setSpriteDown();
			robot.getSprite().update(delta);
			if (!(mapTab[robot.getYMap() + 1][robot.getXMap()] == '1')) {
				shiftY += robot.getStepVal();
				robot.setXDisp(robot.getXMap() * TILE_SIZE);
				robot.setYDisp(robot.getYMap() * TILE_SIZE + shiftY);
				if (shiftY >= TILE_SIZE) {
					shiftY = 0;
					robot.setyMap(robot.getYMap() + 1);
				}
			}

		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			robot.setSpriteLeft();
			robot.getSprite().update(delta);
			if (!(mapTab[robot.getYMap()][robot.getXMap() - 1] == '1')) {
				shiftX -= robot.getStepVal();
				robot.setXDisp(robot.getXMap() * TILE_SIZE + shiftX);
				robot.setYDisp(robot.getYMap() * TILE_SIZE);
				if (shiftX <= (-1 * TILE_SIZE)) {
					shiftX = 0;
					robot.setxMap(robot.getXMap() - 1);
				}
			}
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			robot.setSpriteRight();
			robot.getSprite().update(delta);
			if (!(mapTab[robot.getYMap()][robot.getXMap() + 1] == '1')) {
				shiftX += robot.getStepVal();
				robot.setXDisp(robot.getXMap() * TILE_SIZE + shiftX);
				robot.setYDisp(robot.getYMap() * TILE_SIZE);
				if (shiftX >= TILE_SIZE) {
					shiftX = 0;
					robot.setxMap(robot.getXMap() + 1);
				}
			}
		} else {
			if (shiftX != 0) {
				robot.setXDisp(robot.getXDisp() - shiftX);
				shiftX = 0;
			}
			if (shiftY != 0) {
				robot.setYDisp(robot.getYDisp() - shiftY);
				shiftY = 0;
			}
		}
	}

	private Stain getNearestStain() {
		int index;
		int distance;
		Stain stain;
		if (!unvisitedStains.isEmpty()) {
			stain = unvisitedStains.get(0);
			index = 0;
			distance = (Math.abs(stain.getXPos() - robot.getXMap()) + Math
					.abs(stain.getYPos() - robot.getYMap())) * 10;
			for (int i = 1; i < unvisitedStains.size(); i++) {
				stain = unvisitedStains.get(i);
				if (distance > (Math.abs(stain.getXPos() - robot.getXMap()) + Math
						.abs(stain.getYPos() - robot.getYMap())) * 10) {
					index = i;
					distance = (Math.abs(stain.getXPos() - robot.getXMap()) + Math
							.abs(stain.getYPos() - robot.getYMap())) * 10;
				}
			}
			return unvisitedStains.remove(index);
		} else
			return null;
	}

	// method returns Stain by position.
	private Stain getStainByPosition(int xPos, int yPos) {
		Stain ret = null;
		for (Stain stain : stainList) {
			if (stain.getXPos() == xPos && stain.getYPos() == yPos) {
				ret = stain;
			}
		}

		return ret;
	}

	public static int getTilesX() {
		return tilesX;
	}

	public static void setTilesX(int tilesX) {
		GarbageRobot.tilesX = tilesX;
	}

	public static int getTilesY() {
		return tilesY;
	}

	public static void setTilesY(int tilesY) {
		GarbageRobot.tilesY = tilesY;
	}

	public static char[][] getMapTab() {
		return mapTab;
	}

	public static void setMapTab(char[][] mapTab) {
		GarbageRobot.mapTab = mapTab;
	}

}