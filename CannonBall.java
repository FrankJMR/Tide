import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class CannonBall {
    private Image projectile;
    public float xPos;
    public float yPos;
    public float vel;

    private final float gravity = 0.3f;
    private final int y_offset = Tide.HEIGHT/2;
    private GameScreen screen;
    private boolean effect = false;
   
    public CannonBall(int mouseX, int mouseY, Image texture, GameScreen game){
        this.xPos = mouseX;
        this.yPos = mouseY;
        vel  = 0;
        screen = game;
        projectile = texture.getScaledInstance(50, 50, BufferedImage.TYPE_INT_ARGB_PRE);

    }
    public float getX(){
        return this.xPos;
    }
    public float getY(){
        return this.yPos;
    }
    public void updateProjectile(){
        vel += gravity;

        yPos += vel;

        if(yPos > y_offset-20 && !effect){
            screen.wave(xPos,vel);
            effect = true;
        }
        if(yPos > y_offset + 20)
            vel /= 6;
		
		if(yPos > Tide.HEIGHT*2) {
			screen.getProjectiles().remove(this);
		}
    }
    public void renderProjectile(Graphics2D g2){
        float alpha = ((yPos-y_offset)/y_offset)/6;

        if(effect && alpha <= 1) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f - alpha));
		}

        g2.drawImage(projectile,(int) xPos, (int) yPos,null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}