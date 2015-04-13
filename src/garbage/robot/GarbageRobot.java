package garbage.robot;
 
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

//need to extend BasicGame
public class GarbageRobot extends BasicGame
{
	private TiledMap map; //map
	private Animation sprite, up, down, left, right; //sprites
	private float x = 64f, y = 64f; //initial position of main sprite
	private boolean[][] blocked; //array of map's squares (blocked property)
	private static final int SIZE = 32; //size of texture
	
	private int points = 0; //points to collect money
	Image money; //money image
	Image coin; //coin image
	float moneyX = 400f; //X axis image
	float moneyY = 50f; //Y axis image
	float coinX = 200f; //X axis image
	float coinY = 300f; //Y axis image
	
    public GarbageRobot()
    {
    	//text in the main window
        super("Garbage Robot AI");
    }
 
    //start the GAME!
    public static void main(String[] arguments)
    {
        try
        {
        	//set AppGameConteiner and start it
            AppGameContainer app = new AppGameContainer(new GarbageRobot());
            app.setDisplayMode(1050, 800, false);
            app.setShowFPS(false);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
 
    //Initialize values (need to override) 
    @Override
    public void init(GameContainer container) throws SlickException
    {
    	//load map
    	map = new TiledMap("data/map.tmx");
    	
    	//set arrays images for sprites emited by keyboards events (image1, image2) to make movement effect
    	Image [] movementUp = {new Image("data/wmg1_bk1.png"), new Image("data/wmg1_bk2.png")};
    	Image [] movementDown = {new Image("data/wmg1_fr1.png"), new Image("data/wmg1_fr2.png")};
    	Image [] movementLeft = {new Image("data/wmg1_lf1.png"), new Image("data/wmg1_lf2.png")};
    	Image [] movementRight = {new Image("data/wmg1_rt1.png"), new Image("data/wmg1_rt2.png")};
    	
    	//draw image1 every 300ms and image2 every 300ms too
    	int [] duration = {100, 100};
    	
    	//set sprites (images, durations, auto-manual-mode-refreshing)
    	up = new Animation(movementUp, duration, false);
    	down = new Animation(movementDown, duration, false);
    	left = new Animation(movementLeft, duration, false);
    	right = new Animation(movementRight, duration, false); 
    	sprite = right; //main orientation  	
    	
    	//set array of blocked property from map's size
    	blocked = new boolean[map.getWidth()][map.getHeight()];
    	for (int x=0; x<map.getWidth(); x++)
    	{
    		for (int y=0; y<map.getHeight(); y++)
    	    {
    			int tileID = map.getTileId(x, y, 0);
    	        String value = map.getTileProperty(tileID, "blocked", "false");
    	        if ("true".equals(value))
    	        {
    	        	blocked[x][y] = true;
    	        }
    	    }
    	 }
    	
    	//init money and coin as Image
    	money = new Image("data/money.png");
    	coin = new Image("data/coin.png");
    }
 
    //Update values (need to override)
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
    	//set input from container
    	Input input = container.getInput();
    	
    	//set event for every key pressed
        if (input.isKeyDown(Input.KEY_UP))
        {
            sprite = up; //set sprite
            if (!isBlocked(x, y - delta * 0.2f))
            {
                sprite.update(delta); //update sprite
                y -= delta * 0.2f; //update position of delta distance (higher delta = faster moving)
                isMoney(x, y);
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN))
        {
            sprite = down;
            if (!isBlocked(x, y + SIZE + delta * 0.2f))
            {
                sprite.update(delta);
                y += delta * 0.2f;
                isMoney(x, y);
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT))
        {
            sprite = left;
            if (!isBlocked(x - delta * 0.2f, y))
            {
                sprite.update(delta);
                x -= delta * 0.2f;
                isMoney(x, y);
            }
        }
        else if (input.isKeyDown(Input.KEY_RIGHT))
        {
            sprite = right;
            if (!isBlocked(x + SIZE + delta * 0.2f, y))
            {
                sprite.update(delta);
                x += delta * 0.2f;
                isMoney(x, y);
            }
        }      
    }
 
    //Render values (need to override)
    public void render(GameContainer container, Graphics g) throws SlickException
    {
    	map.render(10, 15); //position of content render relative to window
    	sprite.draw((int)x, (int)y);
    	g.drawString("POINTS: " + points, 20f, 30f);
    	g.drawString("ROBOT", x-10f, y-20f);
    	
    	//update images if it has changed position
    	g.drawImage(money, moneyX, moneyY); 
    	g.drawImage(coin, coinX, coinY); 
    }
    
    //check if robot is on block position
    private boolean isBlocked(float x, float y)
    {
         int xBlock = (int)x / SIZE;
         int yBlock = (int)y / SIZE;
         return blocked[xBlock][yBlock];
    }
    
    //check if robot is on money position
    private void isMoney(float x, float y)
    {
    	if(x > moneyX - 10f && x < moneyX + 10f && y > moneyY - 10f  && y < moneyY + 10f)
    	{
    		points += 5;
    		Random rand = new Random();
    		moneyX = rand.nextFloat() * (1000f - 100f) + 100f;
    		moneyY = rand.nextFloat() * (700f - 100f) + 100f;
    	}
    	
    	if(x > coinX - 10f && x < coinX + 10f && y > coinY - 10f  && y < coinY + 10f)
    	{
    		points += 1;
    		Random rand = new Random();
    		coinX = rand.nextFloat() * (1000f - 100f) + 100f;
    		coinY = rand.nextFloat() * (700f - 100f) + 100f;
    	}
    }
}