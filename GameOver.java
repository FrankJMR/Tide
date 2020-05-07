import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.awt.font.*;
import java.awt.GraphicsEnvironment;
import java.awt.Color;

public class GameOver extends JPanel{
    private static final long serialVersionUID = 1L;
    Image gameOverScreen;
    Font gameOverfont;
    public GameOver(GameScreen game){
        
        try {
            gameOverScreen = ImageIO.read(GameScreen.class.getResource("textures/oceanTemplate.jpg"));
            gameOverScreen = gameOverScreen.getScaledInstance(1200, Tide.HEIGHT+100, BufferedImage.TYPE_INT_ARGB);
            gameOverfont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("Font/boyWonder.ttf"));
            gameOverfont = gameOverfont.deriveFont(100F);
           
        } catch (Exception e) {
            
        }
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(game);
        topFrame.add(this);
        topFrame.setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        final Graphics2D g2 = (Graphics2D) g;
        Color text = new Color(	25, 25, 112);
        g2.drawImage(gameOverScreen, 0, 0, null);
        g.setColor(text);
        g.setFont(gameOverfont);
        g.drawString("Game Over", (Tide.WIDTH/3)-60, (Tide.HEIGHT/2)+80);
       
      
    }
}