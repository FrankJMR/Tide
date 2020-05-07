import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.lang.Math;

import java.util.TimerTask;
import java.util.Timer;

public class Enemies {
    private double xPos, yPos;
    long start;

    private final double yPosition;
    private final Image enemy;

    public Enemies( double x,  double y, Image sprite) {
        this.xPos = x;
        this.yPos = y;
        yPosition = y;
        this.start = System.currentTimeMillis();
        this.enemy = sprite;
        
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }
    
    public void updateRightEnemyLocation(){
        int amplitude = 20;
        this.xPos +=0.3;
        this.yPos = yPosition/3+ (amplitude * -(float)Math.cos(this.xPos / 10) + Tide.HEIGHT/14);
        
        if(this.xPos > Tide.WIDTH){
            this.xPos = -10.0;
        }
    }
    public void updateLeftEnemyLocation(){
        int amplitude = 20;
        this.xPos -=0.5;
        this.yPos = yPosition/3+ (amplitude * -(float)Math.cos(this.xPos / 10) + Tide.HEIGHT/14);

        if(this.xPos < -40){
            this.xPos = Tide.WIDTH;
        }
    
    }
    public void renderEnemy(Graphics2D g2,Image ufo) {
       
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.drawImage(this.enemy, (int) this.xPos, (int) this.yPos, null);
    }





}