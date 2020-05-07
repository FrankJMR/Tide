import java.awt.Image;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
public class Life {
    
    //placement and image of health

    private Image heart;
    private int xPos,yPos,totalLife;
    private int damage;
    private GameScreen game;
    public Life(int x, int y,int hits, Image heart, GameScreen game){
            this.xPos = x;
            this.yPos = y;
            this.heart = heart;
            game = game;
            damage = hits;
            
    }
    public void render(Graphics2D g2, Image h){
        
        g2.drawImage(h, this.xPos, this.yPos,null);
       
    }

}