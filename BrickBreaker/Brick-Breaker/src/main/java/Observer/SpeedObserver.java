package main.java.Observer;

import main.java.Interfaces.IObserver;

public class SpeedObserver implements IObserver{

    protected String speedState;
    protected SubjectSpeed subject;

    public SpeedObserver(SubjectSpeed subject){
        this.subject = subject;
        speedState = this.subject.getState();
    }

    @Override
    public void update() {
        this.speedState = subject.getState();
    }
    
    public String getSpeed(){
        return speedState;
    }

}
