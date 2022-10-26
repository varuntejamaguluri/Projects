package main.java.Observer;

import main.java.Interfaces.IObserver;

public class ScoreObserver implements IObserver{

    protected int scoreState;
    protected SubjectScore subject;

    public ScoreObserver(SubjectScore subject){
        this.subject = subject;
        scoreState = this.subject.getState();
    }

    @Override
    public void update() {
        this.scoreState = subject.getState();
    }
    
    public int getScore(){
        return scoreState;
    }

}
