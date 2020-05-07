import java.util.TimerTask;
import java.util.Timer;
import java.util.ArrayList;

//timer for ufo to shoot every half second approx.
public class Clock {

    private final ArrayList<CannonBall> projectiles = new ArrayList<CannonBall>();
    private TimerTask t;
    private Timer enemyTimer; 
    private boolean enemyShot;

    public Clock(GameScreen game){
        Timer t = new Timer();

        TimerTask enemyTimer = new TimerTask() {

			@Override
			public void run() {
				//System.out.println("shoot!");
				game.setProjectiles();
			}
		};
		t.schedule(enemyTimer, 0 ,400);
    }
    
    
 }





