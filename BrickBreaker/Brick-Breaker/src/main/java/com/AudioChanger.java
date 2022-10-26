package main.java.com;

import java.io.IOException;
import java.util.ArrayList;

public class AudioChanger {
	private AudioStrategy strategy;

    // Constructor
    public AudioChanger() throws IOException
    {
        strategy = new GameMusic() ;
    }

// Change audio strategy
    public void changeStrategy(AudioStrategy s) {
	    strategy = s ;
	}

    // Perform strategy
    public void setAudioOutput() throws Exception {
       
            strategy.changeAudio();
            
	
}
}
