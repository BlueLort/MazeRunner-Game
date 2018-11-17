package utility;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundThread implements Runnable {
    String path ;
    public SoundThread(){

    }
    public SoundThread(String filename){
        this.path=filename;
    }
    public void run(){
        File file=new File(path);
        try {
            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(file);
            AudioFormat audioFormat=audioInputStream.getFormat();
            DataLine.Info info=new  DataLine.Info(Clip.class,audioFormat);
            Clip clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
