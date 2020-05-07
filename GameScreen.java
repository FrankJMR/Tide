import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.AlphaComposite;

import java.awt.geom.Rectangle2D;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.TimerTask;

public class GameScreen extends JPanel implements MouseListener,KeyListener{
	private static final long serialVersionUID = 1L;
	//water characteristics
	private final int columnSpringNum = 250;
	private final int yWaterLevel = (Tide.HEIGHT / 3) + 70;
	private final float propogate = 0.25f;
	private final Color ocean = new Color(100, 255, 235);

	//ArrayList for all our objects
	private final WaterSpring[] columnSprings = new WaterSpring[columnSpringNum];
	private final ArrayList<Enemies> enemyList = new ArrayList<>();
	private final ArrayList<CannonBall> projectiles = new ArrayList<CannonBall>();
	private final ArrayList<Life> hearts = new ArrayList<Life>();

	//Images
	private Image cannon_ball;
	private Image background;
	private Image player;
	private Image ufo;
	private Image health;
	private Image gameEnd;
	private int hits;
	//player instance
	Player p;
	private int life=5;
	//Timers
	private java.util.Timer t ;
	private TimerTask updateTask;
	private final Timer gameLoop;
	private final Timer gameOver;
	public GameScreen getGame(){
		return this;
	}
	public GameScreen() {
		hits=0;
		p = new Player();

		gameLoop = new Timer(1000 / 60, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent input) {
				
				updateSprings();
				repaint();
				if (hearts.size() ==0){
					gameLoop.stop();
					gameOver.start();
				}
				checkCollision();
				
			}
		});
		
		gameOver = new Timer(1000 / 60, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent input) {
				GameOver over = new GameOver(getGame());
				
			}
		});

		// creating our body of water
		for (int springIndex = 0; springIndex < columnSprings.length; springIndex++) {
			final float designatedSpringLength = (float) springIndex / (float) columnSprings.length;
			columnSprings[springIndex] = new WaterSpring(designatedSpringLength * Tide.WIDTH, yWaterLevel - 10);
		}
		try {
			player = ImageIO.read(GameScreen.class.getResource("textures/scuba.png"));
			player = player.getScaledInstance(100, 100, BufferedImage.TYPE_INT_ARGB);
			cannon_ball = ImageIO.read(GameScreen.class.getResource("textures/mine.png"));
			cannon_ball = cannon_ball.getScaledInstance(cannon_ball.getWidth(null), cannon_ball.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			background = ImageIO.read(GameScreen.class.getResource("textures/background.png"));
			background = background.getScaledInstance(Tide.WIDTH, Tide.HEIGHT, BufferedImage.TYPE_INT_ARGB);
			ufo = ImageIO.read(GameScreen.class.getResource("textures/ufo.png"));
			ufo = ufo.getScaledInstance(70, 70, BufferedImage.TYPE_INT_ARGB);
			health = ImageIO.read(Life.class.getResource("textures/health.png")); 
			health = health.getScaledInstance(50, 50, BufferedImage.TYPE_INT_ARGB);
			
			
		} catch (final IOException e) {
			e.printStackTrace();
		}

		setHealth();
		setEnemies();
		Clock projectileTime = new Clock(this);

		gameLoop.start();
		
	}

	public void updateSprings() {
		
		for (int springIndex = 0; springIndex < columnSprings.length; springIndex++) {
			columnSprings[springIndex].update();
		}
		final float leftDeltaList[] = new float[columnSprings.length];
		final float rightDeltaList[] = new float[columnSprings.length];
		/*
		 * notice that: left delta is the difference between the columns height and the
		 * left neighbors height right delta is the difference between the columns
		 * height and the right neighbors height --> this will help us get that wave
		 * effect, if we know how tall the current column is compared to its neighbors
		 */

		for (int updates = 0; updates < 8; updates++) {

			for (int i = 0; i < columnSprings.length; i++) {

				// first column has no left neighbor(beginning of screen)
				if (i > 0) {
					// applying force(propogate) on the previous column before the current one
					leftDeltaList[i] = propogate * (columnSprings[i].posY - columnSprings[i - 1].posY);

					// Updating the force of the left neighbor to get that smooth water movement
					columnSprings[i - 1].speed += leftDeltaList[i];
				}
				// last column has no right neighbor(end of screen)
				if (i < columnSprings.length - 1) {
					rightDeltaList[i] = propogate * (columnSprings[i].posY - columnSprings[i + 1].posY);

					// updating the force of the right neighbor
					columnSprings[i + 1].speed += rightDeltaList[i];
				}

			}
			// update waves
			for (int i = 0; i < columnSprings.length; i++) {
				if (i > 0) {
					columnSprings[i - 1].posY += leftDeltaList[i];
				}
				if (i < columnSprings.length - 1) {
					columnSprings[i + 1].posY += rightDeltaList[i];
				}

			}

			//update projectiles
			for (int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).updateProjectile();
			}
			//update enemies
			for (int i = 0; i< enemyList.size();i++){
				if(i%2==0){
					enemyList.get(i).updateLeftEnemyLocation();
				}
				else{
					enemyList.get(i).updateRightEnemyLocation();
				}
			}
			//update player
			p.update();
			
		}

	}
	//collision between player and projectile
	public void checkCollision(){
		double pX = p.getX();
		double pY = p.getY();

		Rectangle2D pHitbox = new Rectangle2D.Double(pX, pY, 90, 30);
		
		double projectileX;
		double projectileY;

		for (CannonBall mine : projectiles) {
			projectileX = mine.getX();
			projectileY = mine.getY();
			Rectangle2D projectileHitbox = new Rectangle2D.Double(projectileX,projectileY,50, 50);

			if(pHitbox.intersects(projectileHitbox)){
				hits+=1;
				projectiles.remove(mine);
				projectileHitbox = null;
				System.out.println("hit!");
				hearts.remove(hearts.size()-hits);
				break;
			}
		}
		hits = 0;
		}
	
	//render screen
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		

		final Graphics2D g2 = (Graphics2D) g;

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g2.drawImage(background, 0, 0, null);
		
		//render water
		for (int i = 0; i < columnSprings.length - 1; i++) {

			final int[] poly_1_x = { (int) columnSprings[i].posX, (int) columnSprings[i + 1].posX,
					(int) columnSprings[i + 1].posX, (int) columnSprings[i].posX };

			final int[] poly_1_y = { (int) columnSprings[i].posY, (int) columnSprings[i + 1].posY, Tide.HEIGHT,
					Tide.HEIGHT };

			final GradientPaint gp = new GradientPaint(0, Tide.HEIGHT, new Color(0, 0, 90), 0, 0, ocean);

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
			g2.setPaint(gp);
			g2.fillPolygon(poly_1_x, poly_1_y, 4);

		}
		//render enemies
		for(int i = 0; i< enemyList.size();i++){
			enemyList.get(i).renderEnemy(g2,ufo);
		}
		
		//render projectiles
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).renderProjectile(g2);
		}
		for (int i = 0; i<hearts.size();i++){
			hearts.get(i).render(g2,health);
		}
		//render player
		p.render(g2, player);
		
	}

	public void wave(final float x, final float vel) {

		float bestDistanceSoFar = Tide.WIDTH;

		int index = columnSprings.length / 2;

		for (int i = 0; i < columnSprings.length; i++) {

			final float distance = Math.abs(columnSprings[i].posX - x);

			if (distance < bestDistanceSoFar) {
				bestDistanceSoFar = distance;
				index = i;
			}
		}

		columnSprings[index].speed = vel * 20;

		for (int i = 0; i < 20; i++) {
			final float velx = (float) (Math.random() * vel - vel / 2);
			final float vely = (float) (-Math.random() * vel);

		}
	}
	//setter and getters
	public void setProjectiles(){
		
		Random randUfo = new Random();
		double spawnX = enemyList.get(randUfo.nextInt(enemyList.size())).getX();
		double spawnY = enemyList.get(randUfo.nextInt(enemyList.size())).getY();
		
		projectiles.add(new CannonBall((int) spawnX,(int) spawnY,cannon_ball,this));
	}
	public ArrayList<CannonBall> getProjectiles() {
		return projectiles;
	}
	//spawn 6 enemies
	public void setEnemies(){
		Random randX = new Random();
		Random randY = new Random();
		
		for(int i =0; i<7;i++) {
			enemyList.add(new Enemies((double)randX.nextInt(Tide.WIDTH),(double)randY.nextInt(400),ufo));
		}
	}
	public void setHealth(){
		for(int i =1; i<14; i++){
			hearts.add(new Life(i*40,10,hits, health,this));
		}
	}
	public ArrayList<Life> getHearts(){
		return hearts;
	}

	//testing purposes
	@Override
	public void mousePressed(final MouseEvent e) {
		
		projectiles.add(new CannonBall(e.getX(), e.getY(), cannon_ball, this));
		
	}

	@Override
	public void mouseReleased(final MouseEvent e) {

	}

	@Override
	public void mouseEntered(final MouseEvent e) {

	}

	@Override
	public void mouseExited(final MouseEvent e) {

	}

	@Override
	public void mouseClicked(final MouseEvent e) {

	}

	public void keyTyped(KeyEvent e) {
        
      }

    public void keyPressed(KeyEvent e) {
	   
	   	if (e.getKeyCode() == KeyEvent.VK_W){
			
			p.setVelY(-2);
	   	}
	   	if (e.getKeyCode() == KeyEvent.VK_A){
			p.setVelX(-2);
			
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			
			p.setVelY(2);
		}
		if (e.getKeyCode() == KeyEvent.VK_D){
			
			p.setVelX(2);
		}
	}
	
    public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W){
			//System.out.println("W pressed");
			p.setVelY(0);
	   	}
	   	if (e.getKeyCode() == KeyEvent.VK_A){
			//System.out.println("A pressed");
			p.setVelX(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			//System.out.println("S pressed");
			p.setVelY(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_D){
			//System.out.println("D pressed");
			p.setVelX(0);
		}
    }
}
