package main.java.com;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameMusic implements AudioStrategy{


// Set strategy to play game music
    @Override
    public void changeAudio() throws Exception {

        
        GameBoard.f = new File("Brick-Breaker/src/main/java/music/music_bg.wav").getAbsoluteFile();
        GameBoard.as = AudioSystem.getAudioInputStream(GameBoard.f);
        GameBoard.c = AudioSystem.getClip();
        GameBoard.c.open(GameBoard.as);
        // Plays audio once
        GameBoard.c.start();
        GameBoard.c.loop(Clip.LOOP_CONTINUOUSLY);
        GameBoard.c.stop();
        GameBoard.c.flush();
        GameBoard.c.close();
        GameBoard.f = new File("Brick-Breaker/src/main/java/music/music_bg.wav").getAbsoluteFile();
        GameBoard.as = AudioSystem.getAudioInputStream(GameBoard.f);
        GameBoard.c = AudioSystem.getClip();
        GameBoard.c.open(GameBoard.as);
        // Plays audio once
        GameBoard.c.start();
        GameBoard.c.loop(Clip.LOOP_CONTINUOUSLY);
        GameBoard.musicIsPlaying = true;
        
    }
    
}
