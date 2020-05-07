import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public class Player {
    private double xPos;
    private double yPos;

    private double speedX;
    private double speedY;

    private final int y_offset = Tide.HEIGHT/2;

    private Image player;
    public Player(){
        this.xPos = Tide.WIDTH/2;
        this.yPos = Tide.HEIGHT-140;
        
    }
    public Player(double x, double y){
        this.xPos = x;
        this.yPos = y;
    }

    public void update(){
        xPos+=speedX;
        yPos+=speedY;

        if(this.xPos > Tide.WIDTH){
            this.xPos = -10.0;
        }

        if(this.xPos < -40){
            this.xPos = Tide.WIDTH;
        }
    }
    public double getX(){
        return xPos;
    }
    public double getY(){
        return yPos;
    }
    public void setX(double x){
        this.xPos = x;
    }
    public void setY(double y){
        this.yPos = y;
    }
    public void setVelX(double velX){
        this.speedX = velX;
    }
    public void setVelY(double velY){
        this.speedY = velY;
    }
    public void render(Graphics2D g2,Image player){
        float alpha = (float)((yPos-y_offset)/y_offset)/6;

        g2.drawImage(player, (int)xPos,(int)yPos,null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}