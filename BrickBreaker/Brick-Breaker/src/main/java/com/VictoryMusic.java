package main.java.com;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class VictoryMusic implements AudioStrategy{

   
   // Set strategy to play victory music
    @Override
    public void changeAudio() throws Exception {

        GameBoard.f = new File("Brick-Breaker/src/main/java/music/victory_music.wav").getAbsoluteFile();
        GameBoard.as = AudioSystem.getAudioInputStream(GameBoard.f);
        GameBoard.c = AudioSystem.getClip();
        GameBoard.c.open(GameBoard.as);
    // Plays audio once
    GameBoard.c.start();
    GameBoard.c.loop(Clip.LOOP_CONTINUOUSLY);
        
    }
    
}
