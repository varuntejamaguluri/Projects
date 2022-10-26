package main.java.Observer;

import java.util.ArrayList;
import main.java.Interfaces.IObserver;
import main.java.Interfaces.ISubject;

public class SubjectLives implements ISubject {

    private int subjectState;
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();

    public SubjectLives(int str){
        subjectState = str;
    }

    public void setState(int status) {
        subjectState = status;
        notifyObservers();
    }

    public int getState(){
        return this.subjectState;
    }

    @Override
    public void attach(IObserver obj) {
        observers.add(obj);
    }

    @Override
    public void detach(IObserver obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        for (IObserver obj : observers) {
            obj.update();
        }
    }

}
