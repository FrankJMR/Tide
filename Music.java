import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Music {
    private String audioFile;
    
    //default and over
    public Music(){
        audioFile = "sound/watery_cave.wav";
    }
    public Music(String song){
        audioFile = song;
    }

    //play background music
    public void playBackgroundMusic(){
        try{
            final File audioPath = new File(audioFile);
            if(audioPath.exists()){
                
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioPath);
                Clip song = AudioSystem.getClip();
                song.open(audioInput);

                //obtain audio control
                FloatControl audioLevel =  (FloatControl) song.getControl(FloatControl.Type.MASTER_GAIN);
                audioLevel.setValue(-10.0f);

                song.start();
                song.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        catch(Exception e){
            System.out.println("Music file doesn't exist");
        }

    }



}