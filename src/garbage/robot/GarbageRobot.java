package garbage.robot;
 
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
    	    sprite.update(delta); //update sprite
    	    y -= delta * 0.2f; //update position of delta distance (higher delta = faster moving)
    	}
    	else if (input.isKeyDown(Input.KEY_DOWN))
    	{
    	    sprite = down;
    	    sprite.update(delta);
    	    y += delta * 0.2f;
    	}
    	else if (input.isKeyDown(Input.KEY_LEFT))
    	{
    	    sprite = left;
    	    sprite.update(delta);
    	    x -= delta * 0.2f;
    	}
    	else if (input.isKeyDown(Input.KEY_RIGHT))
    	{
    	    sprite = right;
    	    sprite.update(delta);
    	    x += delta * 0.2f;
    	}
    }
 
    //Render values (need to override)
    public void render(GameContainer container, Graphics g) throws SlickException
    {
    	map.render(10, 15); //position of content render relative to window
    	sprite.draw((int)x, (int)y);
    }
}