import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class KeyInput extends KeyAdapter {
    GameScreen game;

    public KeyInput(GameScreen game){
        this.game = game;
    }
    public void keyPressed(KeyEvent e){
        game.keyPressed(e);
    }
    public void keyReleased(KeyEvent e){
        game.keyReleased(e);
    }
}