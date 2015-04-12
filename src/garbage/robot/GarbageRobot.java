package garbage.robot;
 
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

//need to extend BasicGame
public class GarbageRobot extends BasicGame
{
	private TiledMap map;
	
    public GarbageRobot()
    {
        super("Garbage Robot AI");
    }
 
    public static void main(String[] arguments)
    {
        try
        {
        	//set AppGameConteiner and start it
            AppGameContainer app = new AppGameContainer(new GarbageRobot());
            app.setDisplayMode(350, 350, false);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
 
    //need to override init 
    @Override
    public void init(GameContainer container) throws SlickException
    {
    	map = new TiledMap("data/grassmap.tmx");
    }
 
    //need to override update
    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
    }
 
    //need to override render 
    public void render(GameContainer container, Graphics g) throws SlickException
    {
    	map.render(15, 15);
    }
}