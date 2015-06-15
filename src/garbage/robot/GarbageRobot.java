package garbage.robot;

import garbage.robot.AStar.Cleaning.Cleaner;
import garbage.robot.AStar.Cleaning.StainGrid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.io.*;
//need to extend BasicGame

public class GarbageRobot extends BasicGame {
	private Image floor, obstacle, carpet, carpetCotton, wood;
	private static Sprite robot;
	private static final int TILE_SIZE = 32;
	private static int tilesX;
	private static int tilesY;
	private static char[][] mapTab;
	private float shiftY = 0;
	private float shiftX = 0;
	private static int numberOfStains;
	private static List<Stain> stainList;
	private Image stainPic;
	private static List<Stain> unvisitedStains;
	private static LinkedList<Move> movesList;
	private Weka weka = null;
	private Weka wekaDetergent = null;
	private Weka wekaEquipment = null;
	private String tools = "";
	private String detergents = "";
	private String backpack = "";
	private String trash = "";

	public GarbageRobot() {
		// text in the main window
		super("Garbage Robot AI");
	}

	// start the GAME!

	public static void main(String[] arguments) throws SlickException {

		LinkedList<Move> moves = new LinkedList<Move>();
		StainGrid staingrid1 = new StainGrid();
		System.out.println();
		moves = Cleaner.clean(staingrid1);
		for(Move move : moves){
			if(move == Move.LEFT) System.out.println("Left");
			if(move == Move.RIGHT) System.out.println("Right");
			if(move == Move.GO) System.out.println("GO!");
			if(move == Move.COS) System.out.println("Cos");
		}
		
		System.out.println();
		System.out.println();
		staingrid1.displayGrid();
		System.out.println();
		System.out.println();
		
		

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
	
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		// floor

		for (int i = 0; i < 1024; i += 32)
			for (int j = 0; j < 640; j += 32)
				g.drawImage(wood, i, j);

		for (int i = 32; i <= 320; i += 32)
			for (int j = 32; j <= 256; j += 32)
				g.drawImage(carpetCotton, i, j);

		for (int i = 0; i <= 320; i += 32)
			for (int j = 352; j <= 576; j += 32)
				g.drawImage(carpet, i, j);

		for (int i = 512; i <= 960; i += 32)
			for (int j = 384; j <= 576; j += 32)
				g.drawImage(floor, i, j);

		for (int i = 0; i < tilesX; i++)
			for (int j = 0; j < tilesY; j++) {
				if (mapTab[j][i] == '0') {
					g.drawImage(wood, i * 32, j * 32);
				}
				if (mapTab[j][i] == '1') {
					g.drawImage(obstacle, i * 32, j * 32);
				}
				if (mapTab[j][i] == '2') {
					g.drawImage(carpet, i * 32, j * 32);
				}
				if (mapTab[j][i] == '3') {
					g.drawImage(carpetCotton, i * 32, j * 32);
				}
				if (mapTab[j][i] == '4') {
					g.drawImage(floor, i * 32, j * 32);
				}
				if (mapTab[j][i] == 'S') {
					int a = i * 32;
					int b = j * 32;
					Stain stain = getStainByPosition(i, j);
					if (a >= 512 && a <= 960 && b >= 384 && b <= 576)
						stain.setBase("floor");
					else if (a >= 0 && a <= 320 && b >= 352 && b <= 576)
						stain.setBase("carpet");
					else if (a >= 32 && a <= 320 && b >= 32 && b <= 256)
						stain.setBase("carpetCotton");
					else
						stain.setBase("wood");
					Image image = new Image(stain.getImage());
					g.drawImage(image, i * 32, j * 32);
				}
			}
		robot.getSprite();
		robot.getXDisp();
		robot.getYDisp();
		robot.getSprite().draw(robot.getXDisp(), robot.getYDisp());

		g.drawString("PosX: " + robot.getXDisp(), 1050f, 10f);
		g.drawString("PosY: " + robot.getYDisp(), 1050f, 30f);
		g.drawString("----------------------------", 1050f, 50f);
		g.setColor(Color.green);
//		g.drawString("BACKPACK: ", 1050f, 570f);
//		g.setColor(Color.white);
//		g.drawString(backpack, 1050f, 590f);
		if (mapTab[robot.getYMap()][robot.getXMap()] == 'S') {

			Stain actStain = getStainByPosition(robot.getXMap(),
					robot.getYMap());
			try {
				if (!robot.isMoving()) {
					String type = weka.predictItem(actStain,
							"poligon/stain/data-one.arff");
					actStain.setType(type);
					actStain.setImage("data/" + type + ".png");
					
					//TODO KASIA  Sprzatanie

					String detergent = wekaDetergent.predictDetergent(actStain,
							"poligon/detergent/data-one.arff");
					actStain.setDetergent(detergent);
					if (!detergents.contains(detergent))
						detergents = detergents + detergent + "\n";

					String equipment = wekaEquipment.predictEquipment(actStain,
							"poligon/equipment/data-one.arff");
					actStain.setTool(equipment);
					if (!tools.contains(equipment))
						tools = tools + equipment + "\n";
					/*					
					String[]callAndArgs = {"python", "net.py", type, detergent, equipment}; //arguments
		            Process p = Runtime.getRuntime().exec(callAndArgs);    
		            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		            //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		            
		            String s = stdInput.readLine();
		            System.out.println(s);*/
					String s = null;
			        try {
			            String[]callAndArgs = {"python", "net.py", type, detergent, equipment}; //arguments
			            Process p = Runtime.getRuntime().exec(callAndArgs);    
			            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			            //output
			            while ((s = stdInput.readLine()) != null) {
//			                System.out.println(s);
			                trash = s;
			            }

			            while ((s = stdError.readLine()) != null) {
//			                System.out.println(s);
			                trash = s;
			            }
			        }

			        catch (IOException e) {
			            System.out.println("exception occured");
			            e.printStackTrace();
			            System.exit(-1);
			        }

					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			g.setColor(Color.white);
			g.drawString("Wetness: " + actStain.getWetness(), 1050f, 70f);
			g.drawString("ColorIntensity: " + actStain.getColorIntensity(),
					1050f, 90f);
			g.drawString("SmellIntensity: " + actStain.getSmellIntensity(),
					1050f, 110f);
			g.drawString("Is Sticky?: " + actStain.isSticky(), 1050f, 130f);
			g.drawString("Is Dried?: " + actStain.isDried(), 1050f, 150f);
			g.drawString("Is Greasy?: " + actStain.isGreasy(), 1050f, 170f);
			g.drawString("Roughness: " + actStain.getRoughness(), 1050f, 190f);
			g.drawString(
					"Dangerous Bacteries: " + actStain.getDangerousBacteries(),
					1050f, 210f);
			g.drawString("Is Fruity?: " + actStain.isFruity(), 1050f, 230f);
			g.drawString("Density: " + actStain.getDensity(), 1050f, 250f);
			g.setColor(Color.red);
			g.drawString("TYPE: " + actStain.getType(), 1050f, 270f);

			g.setColor(Color.white);
			g.drawString("Age: " + actStain.getAge(), 1050f, 310f);
			g.drawString("Type: " + actStain.getType(), 1050f, 330f);
			g.drawString("Base: " + actStain.getBase(), 1050f, 350f);
			g.drawString("Base state: " + actStain.getBaseState(), 1050f, 370f);
			g.drawString("Chemicals: " + actStain.getChemicals(), 1050f, 390f);
			g.drawString("Is Poisonous?: " + actStain.isPoisonous(), 1050f,
					410f);
			g.setColor(Color.red);
			g.drawString("DETERGENT: " + actStain.getDetergent(), 1050f, 430f);

			g.setColor(Color.white);
			g.drawString("Is Tall?: " + actStain.isTall(), 1050f, 470f);
			g.drawString("Size: " + actStain.getSize(), 1050f, 490f);
			g.drawString("Detergent: " + actStain.getDetergent(), 1050f, 510f);
			g.setColor(Color.red);
			g.drawString("EQUIPMENT: " + actStain.getTool(), 1050f, 530f);

			g.setColor(Color.green);
			g.drawString("TRASH: ", 1050f, 570f);
			g.setColor(Color.white);
			g.drawString(trash, 1050f, 590f);

		} else {
			g.drawString("Needed detergents: ", 1050f, 70f);
			g.drawString(detergents, 1050f, 90f);
			g.drawString("Needed tools: ", 1050f, 210f);
			g.drawString(tools, 1050f, 230f);
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		try {

			weka = new Weka("poligon/stain/data-learning.arff",
					"poligon/stain/data-test.arff");
			weka.writePredictions("poligon/stain/data-predicted.arff");
			weka.writeTree("poligon/stain/tree.txt");

			wekaDetergent = new Weka("poligon/detergent/data-learning.arff");
			wekaDetergent.writeTree("poligon/detergent/tree.txt");

			wekaEquipment = new Weka("poligon/equipment/data-learning.arff");
			wekaEquipment.writeTree("poligon/equipment/tree.txt");

		} catch (Exception e) {
			e.printStackTrace();
		}
		robot.init(4, 4);

		floor = new Image("data/floor.png");
		obstacle = new Image("data/wall.png");
		stainPic = new Image("data/stain.png");
		carpet = new Image("data/carpet.png");
		carpetCotton = new Image("data/carpetCotton.png");
		wood = new Image("data/wood.png");
		// mapTab[yMap][xMap] = 2;
	}

	// method update needed informations
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();

		if (robot.isMoving()) {
			if (!robot.isWalking()) {
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
						robot.setWalking(true);
					}
				} else {
					robot.setMoving(false);
				}
			} else { // when robot isWalking
				switch (robot.getDirection()) {
				case 'N': {
					shiftY -= robot.getStepVal();
					robot.setXDisp(robot.getXMap() * TILE_SIZE);
					robot.setYDisp(robot.getYMap() * TILE_SIZE + shiftY);
					if (shiftY <= (-1 * TILE_SIZE)) {
						shiftY = 0;
						robot.setYMap(robot.getYMap() - 1);
						robot.setWalking(false);
					}
					break;
				}
				case 'W': {
					shiftX -= robot.getStepVal();
					robot.setXDisp(robot.getXMap() * TILE_SIZE + shiftX);
					robot.setYDisp(robot.getYMap() * TILE_SIZE);
					if (shiftX <= (-1 * TILE_SIZE)) {
						shiftX = 0;
						robot.setXMap(robot.getXMap() - 1);
						robot.setWalking(false);
					}
					break;
				}
				case 'S': {
					shiftY += robot.getStepVal();
					robot.setXDisp(robot.getXMap() * TILE_SIZE);
					robot.setYDisp(robot.getYMap() * TILE_SIZE + shiftY);
					if (shiftY >= TILE_SIZE) {
						shiftY = 0;
						robot.setYMap(robot.getYMap() + 1);
						robot.setWalking(false);
					}
					break;
				}
				case 'E': {
					shiftX += robot.getStepVal();
					robot.setXDisp(robot.getXMap() * TILE_SIZE + shiftX);
					robot.setYDisp(robot.getYMap() * TILE_SIZE);
					if (shiftX >= TILE_SIZE) {
						shiftX = 0;
						robot.setXMap(robot.getXMap() + 1);
						robot.setWalking(false);
					}
					break;
				}
				}
				robot.getSprite().update(delta);
			}

		} else if (input.isKeyDown(Input.KEY_G)) {
			if (unvisitedStains.isEmpty()) {
				if (!robot.isInBase()) {
					robot.setMoving(true);
					movesList = PathMaker.makePath(robot.getXMap(),
							robot.getYMap(), robot.getDirection(),
							robot.getXBase(), robot.getYBase());
					robot.setInBase(true);
				} else
					robot.setMoving(false);
			} else {
				robot.setInBase(false);
				robot.setMoving(true);
				Stain endStain = getNearestStain();
				// Stain endStain = unvisitedStains.get(0);
				if (endStain != null)
					movesList = PathMaker.makePath(robot.getXMap(),
							robot.getYMap(), robot.getDirection(),
							endStain.getXPos(), endStain.getYPos());
			}

		} else if (input.isKeyPressed(Input.KEY_N)) {
			// todo: CLEANING
			readMapFromFile();
			generateStains();
			tools = "";
			detergents = "";

		} else if (input.isKeyDown(Input.KEY_UP)) {
			robot.setSpriteUp();// set sprite
			robot.getSprite().update(delta);
			if (!(mapTab[robot.getYMap() - 1][robot.getXMap()] == '1')) {
				shiftY -= robot.getStepVal();
				robot.setXDisp(robot.getXMap() * TILE_SIZE);
				robot.setYDisp(robot.getYMap() * TILE_SIZE + shiftY);
				if (shiftY <= (-1 * TILE_SIZE)) {
					shiftY = 0;
					robot.setYMap(robot.getYMap() - 1);
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
					robot.setYMap(robot.getYMap() + 1);
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
					robot.setXMap(robot.getXMap() - 1);
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
					robot.setXMap(robot.getXMap() + 1);
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