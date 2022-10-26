package main.java.com;



public class StopMusic implements AudioStrategy {


  // Set strategy to stop music

    @Override
    public void changeAudio() throws Exception {

        GameBoard.musicIsPlaying = false;
        GameBoard.c.stop();
        GameBoard.c.flush();
        GameBoard.c.close();
    }

}
