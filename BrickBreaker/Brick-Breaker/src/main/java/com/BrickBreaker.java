package main.java.com;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.File;

public class BrickBreaker extends JFrame {

    // Constructor

    public BrickBreaker() throws IOException {

        initUI();
    }

    // UI initialization
    private void initUI() throws IOException {

        add(new MenuScreen());
        setTitle("Smash Them!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            BrickBreaker game = null;
            try {
                AudioChanger ac = new AudioChanger() ;
                //playMusic();
                 ac.changeStrategy(new GameMusic());
                ac.setAudioOutput();
                game = new BrickBreaker();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            game.setVisible(true);
        });
    }

    

    
}