package game.system;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer implements Runnable{
    private Clip clip;
    String filePath;
    public static boolean onOff=true;
    public SoundPlayer(String filePath)
    {
        this.filePath=filePath;
    }
    public  void stop()
    {
        clip.stop();
    }
    private void playMusic()
    {
        File file=new File(filePath);
        try {
            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(file);
            AudioFormat audioFormat=audioInputStream.getFormat();
            DataLine.Info info=new  DataLine.Info(Clip.class,audioFormat);
            clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try {
            AudioInputStream ais= AudioSystem.getAudioInputStream(file);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        playMusic();
    }
}
