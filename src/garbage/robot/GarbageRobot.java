package garbage.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Animation;
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
	private Animation sprite, up, down, left, right; // sprites
	private static final int TILE_SIZE = 32;
	private final float STEP = 0.5f;
	private static int tilesX;
	private static int tilesY;
	private static char[][] mapTab;
	private int xMap = 4;
	private int yMap = 4;
	private float xDisp;
	private float yDisp;
	private float shiftY = 0;
	private float shiftX = 0;
	private Image stainPic;
	private static int numberOfStains;
	private static List<Stain> stainList;

	public GarbageRobot() {
		// text in the main window
		super("Garbage Robot AI");
	}

	// start the GAME!
	public static void main(String[] arguments) {

		readMapFromFile();
		generateStains();

		try {
			// set AppGameConteiner and start it
			AppGameContainer app = new AppGameContainer(new GarbageRobot());
			app.setDisplayMode(1024, 640, false);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	// method put stains on room.
	public static void generateStains() {
		Stain tempStain;
		stainList = new ArrayList<Stain>();
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
				}
				tempStain = new Stain();
				tempStain.setxPos(xStain);
				tempStain.setyPos(yStain);
				stainList.add(tempStain);
			}
		}
	}
	//method read params from conf.prp and map from file
	public static void readMapFromFile() {
		PropertiesSupport propertiesSupport = new PropertiesSupport();
		propertiesSupport.load();
		tilesX = Integer.parseInt(propertiesSupport.getProperty("map_x"));
		tilesY = Integer.parseInt(propertiesSupport.getProperty("map_y"));
		numberOfStains = Integer.parseInt(propertiesSupport
				.getProperty("stainsOnRoom"));
		MapReader mapReader = new MapReader(tilesX, tilesY);
		mapReader.loadMapFromFile(propertiesSupport.getProperty("map_path"));

		mapTab = mapReader.getMapTab();
	}
	//method draw elements on screen
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
					g.drawImage(stainPic, i * 32, j * 32);
				}
			}
		sprite.draw(xDisp, yDisp);
		
		g.drawString("PosX: " + xDisp, 10f, 30f);
		g.drawString("PosY: " + yDisp, 10f, 50f);
		g.drawString("-------------", 10f, 70f);
		if(mapTab[yMap][xMap] == 'S'){
			Stain actStain = getStainByPosition(xMap, yMap);
			g.drawString("Wetness: " + actStain.getWetness() , 10f, 90f);
			g.drawString("ColorIntensity: " + actStain.getColorIntensity(), 10f, 110f);
			g.drawString("SmellIntensity: " + actStain.getSmellIntensity() , 10f, 130f);
			g.drawString("Is Sticky?: " + actStain.isSticky(), 10f, 150f);
			g.drawString("Size: " + actStain.getSize() , 10f, 170f);
			g.drawString("Is Dried?: " + actStain.isDried(), 10f, 190f);
			g.drawString("Is Greasy?: " + actStain.isGreasy() , 10f, 210f);
			g.drawString("Softness: " + actStain.getSoftness(), 10f, 230f);
			g.drawString("Dangerous Bacteries: " + actStain.getDangerousBacteries(), 10f, 250f);
			g.drawString("Height: " + actStain.getHeight() , 10f, 270f);
			g.drawString("Is Fruity?: " + actStain.isFruity(), 10f, 290f);
			g.drawString("Density: " + actStain.getDensity(), 10f, 310f);
		}		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {

		floor = new Image("data/grass.png");
		obstacle = new Image("data/smallRocks.png");
		stainPic = new Image("data/stain.png");
		// mapTab[yMap][xMap] = 2;
		xDisp = 32 * xMap;
		yDisp = 32 * yMap;
		Image[] movementUp = { new Image("data/wmg1_bk1.png"),
				new Image("data/wmg1_bk2.png") };
		Image[] movementDown = { new Image("data/wmg1_fr1.png"),
				new Image("data/wmg1_fr2.png") };
		Image[] movementLeft = { new Image("data/wmg1_lf1.png"),
				new Image("data/wmg1_lf2.png") };
		Image[] movementRight = { new Image("data/wmg1_rt1.png"),
				new Image("data/wmg1_rt2.png") };

		// draw image1 every 100ms and image2 every 100ms too
		int[] duration = { 100, 100 };

		// set sprites (images, durations, auto-manual-mode-refreshing)
		up = new Animation(movementUp, duration, false);
		down = new Animation(movementDown, duration, false);
		left = new Animation(movementLeft, duration, false);
		right = new Animation(movementRight, duration, false);
		sprite = down; // main orientation
	}
	//method update needed informations
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_UP) && !(mapTab[yMap - 1][xMap] == '1')) {
			sprite = up; // set sprite
			sprite.update(delta);
			shiftY -= STEP;
			xDisp = xMap * TILE_SIZE;
			yDisp = yMap * TILE_SIZE + shiftY;
			if (shiftY <= (-1 * TILE_SIZE)) {
				shiftY = 0;
				//mapTab[yMap][xMap] = '0';
				yMap = yMap - 1;
				//mapTab[yMap][xMap] = '2';
			}

		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			sprite = down;
			sprite.update(delta);
			if (!(mapTab[yMap + 1][xMap] == '1')) {
				shiftY += STEP;
				xDisp = xMap * TILE_SIZE;
				yDisp = yMap * TILE_SIZE + shiftY;
				if (shiftY >= TILE_SIZE) {
					shiftY = 0;
					//mapTab[yMap][xMap] = '0';
					yMap = yMap + 1;
				//	mapTab[yMap][xMap] = '2';
				}
			}

		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			sprite = left;
			sprite.update(delta);
			if (!(mapTab[yMap][xMap - 1] == '1')) {
				shiftX -= STEP;
				xDisp = xMap * TILE_SIZE + shiftX;
				yDisp = yMap * TILE_SIZE;
				if (shiftX <= (-1 * TILE_SIZE)) {
					shiftX = 0;
					//mapTab[yMap][xMap] = '0';
					xMap = xMap - 1;
					//mapTab[yMap][xMap] = '2';
				}
			}
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			sprite = right;
			sprite.update(delta);
			if (!(mapTab[yMap][xMap + 1] == '1')) {
				shiftX += STEP;
				xDisp = xMap * TILE_SIZE + shiftX;
				yDisp = yMap * TILE_SIZE;
				if (shiftX >= TILE_SIZE) {
					shiftX = 0;
					//mapTab[yMap][xMap] = '0';
					xMap = xMap + 1;
				//	mapTab[yMap][xMap] = '2';
				}
			}
		} else {
			if (shiftX != 0) {
				xDisp = xDisp - shiftX;
				shiftX = 0;
			}
			if (shiftY != 0) {
				yDisp = yDisp - shiftY;
				shiftY = 0;
			}
		}
	}
	//method returns Stain by position.
	private Stain getStainByPosition(int xPos, int yPos) {
		Stain ret = null;
		for (Stain stain : stainList) {
			if (stain.getxPos() == xPos && stain.getyPos() == yPos) {
				ret = stain;
			}
		}
		return ret;
	}

}