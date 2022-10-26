package main.java.Observer;

import java.util.ArrayList;
import main.java.Interfaces.IObserver;
import main.java.Interfaces.ISubject;

public class SubjectSpeed implements ISubject {

    private String subjectState;
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();

    public SubjectSpeed(String str){
        subjectState = str;
    }

    public void setState(String status) {
        subjectState = status;
        notifyObservers();
    }

    public String getState(){
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
