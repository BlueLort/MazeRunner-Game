package game.system;

import com.sun.media.sound.WaveFileReader;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BackGroundMusic implements Runnable {
   private static Clip clip;
    String filePath;
    public static boolean onOff=true;
    public BackGroundMusic(String filePath)
    {
        this.filePath=filePath;
    }
    public static void stop()
    {
        clip.stop();
    }
    private void playMusic()
    {
        File file=new File(filePath);
        try {
            AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(file);
            AudioFormat audioFormat=audioInputStream.getFormat();
            DataLine.Info info=new  DataLine.Info(Clip.class,audioFormat);
            clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
            clip.loop(1000000000);
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

    public static Clip getClip() {
        return clip;
    }

    public static void setClip(Clip clip) {
        BackGroundMusic.clip = clip;
    }
}
