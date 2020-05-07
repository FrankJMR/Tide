/* 
@author: Frank Martinez
Programming Final: Tide
*/

import javax.swing.JFrame;

public class Tide extends JFrame{
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    private GameScreen game = new GameScreen();
   

    public Tide(){
        
        setTitle("Tide");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        add(game);
        addMouseListener(game);
        addKeyListener(game);
        setVisible(true);
        Music audio = new Music();
        audio.playBackgroundMusic();
        
        
    }
    public static void main(String[] args) {
        new Tide();
    }
}