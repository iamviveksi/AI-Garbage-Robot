package garbage.robot;



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
//	private Image pamperek;
	private Image obstacle;
	private Animation sprite, up, down, left, right; //sprites
	private static final int TILE_SIZE = 32;
	private final int STEP = 2;
	private static int tilesX;
	private static int tilesY;
	private static char[][] mapTab;
	private int xMap = 4;
	private int yMap = 4;
	private int xDisp;
	private int yDisp;

	private int shiftY = 0;
	private int shiftX = 0;


	public GarbageRobot() {
		// text in the main window
		super("Garbage Robot AI");
	}

	// start the GAME!
	public static void main(String[] arguments) {

		readMapFromFile();

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

	public static void readMapFromFile() {
		PropertiesSupport propertiesSupport = new PropertiesSupport();
		propertiesSupport.load();
		tilesX = Integer.parseInt(propertiesSupport.getProperty("mapa_x"));
		tilesY = Integer.parseInt(propertiesSupport.getProperty("mapa_y"));
		MapReader mapReader = new MapReader(tilesX, tilesY);
		mapReader.loadMapFromFile(propertiesSupport.getProperty("mapa_plik"));
		mapTab = mapReader.getMapTab();
		
	}

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
			}
		//g.drawImage(pamperek, xDisp, yDisp);
		//System.out.println(xDisp + "  " + yDisp);
		g.drawString("PosX: " + xDisp, 10f, 30f);
		g.drawString("PosY: " + yDisp, 10f, 50f);
		sprite.draw(xDisp, yDisp);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		floor = new Image("data/grass.png");
		obstacle = new Image("data/smallRocks.png");
	//	pamperek = new Image("data/wmg1_fr1.png");
		mapTab[yMap][xMap] = 2;
		xDisp = 32 * xMap;
		yDisp = 32 * yMap;
		Image [] movementUp = {new Image("data/wmg1_bk1.png"), new Image("data/wmg1_bk2.png")};
    	Image [] movementDown = {new Image("data/wmg1_fr1.png"), new Image("data/wmg1_fr2.png")};
    	Image [] movementLeft = {new Image("data/wmg1_lf1.png"), new Image("data/wmg1_lf2.png")};
    	Image [] movementRight = {new Image("data/wmg1_rt1.png"), new Image("data/wmg1_rt2.png")};
    	
    	//draw image1 every 100ms and image2 every 100ms too
    	int [] duration = {100, 100};
    	
    	//set sprites (images, durations, auto-manual-mode-refreshing)
    	up = new Animation(movementUp, duration, false);
    	down = new Animation(movementDown, duration, false);
    	left = new Animation(movementLeft, duration, false);
    	right = new Animation(movementRight, duration, false); 
    	sprite = down; //main orientation  	
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_UP) && !(mapTab[yMap-1][xMap] == '1')) {
			sprite = up; //set sprite
			sprite.update(delta);
			shiftY -= STEP;
			xDisp = xMap * TILE_SIZE;
			yDisp = yMap * TILE_SIZE +shiftY;
			if (shiftY <= (-1 * TILE_SIZE)) {
				shiftY = 0;
				mapTab[yMap][xMap] = '0';
				yMap = yMap - 1;
				mapTab[yMap][xMap] = '2';
			}

		} else if (input.isKeyDown(Input.KEY_DOWN) && !(mapTab[yMap+1][xMap] == '1'))  {
			sprite = down;
			sprite.update(delta);
			shiftY += STEP;
			xDisp = xMap * TILE_SIZE;
			yDisp = yMap * TILE_SIZE + shiftY;
			if (shiftY >= TILE_SIZE) {
				shiftY = 0;
				mapTab[yMap][xMap] = '0';
				yMap = yMap + 1;
				mapTab[yMap][xMap] = '2';
			}

		} else if (input.isKeyDown(Input.KEY_LEFT) && !(mapTab[yMap][xMap-1] == '1')) {
			sprite = left;
			sprite.update(delta);
			shiftX -= STEP;
			xDisp = xMap * TILE_SIZE + shiftX;
			yDisp = yMap * TILE_SIZE;
			if (shiftX <= (-1 * TILE_SIZE)) {
				shiftX = 0;
				mapTab[yMap][xMap] = '0';
				xMap = xMap - 1;
				mapTab[yMap][xMap] = '2';
			}
		} else if (input.isKeyDown(Input.KEY_RIGHT)&& !(mapTab[yMap][xMap+1] == '1')) {
			sprite = right;
			sprite.update(delta);
			shiftX += STEP;
			xDisp = xMap * TILE_SIZE + shiftX;
			yDisp = yMap * TILE_SIZE;
			if (shiftX >= TILE_SIZE) {
				shiftX = 0;
				mapTab[yMap][xMap] = '0';
				xMap = xMap + 1;
				mapTab[yMap][xMap] = '2';
			}
		} else {
			if(shiftX != 0){
				xDisp = xDisp - shiftX;
				shiftX=0;
			}
			if(shiftY != 0){
				yDisp = yDisp - shiftY;
				shiftY=0;
			}
		}

	}
}