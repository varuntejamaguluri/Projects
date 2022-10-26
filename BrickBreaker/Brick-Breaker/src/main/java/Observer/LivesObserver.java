package main.java.Observer;

import main.java.Interfaces.IObserver;

public class LivesObserver implements IObserver{

    protected int livesState;
    protected SubjectLives subject;

    public LivesObserver(SubjectLives subject){
        this.subject = subject;
        livesState = this.subject.getState();
    }

    @Override
    public void update() {
        this.livesState = subject.getState();
    }

    public int getLives(){
        return livesState;
    }
    
}
