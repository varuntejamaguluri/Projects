package main.java.com ;

import main.java.Interfaces.IKeyDirection;
import main.java.Interfaces.IRacket;

import java.awt.event.KeyEvent;

public class SwitchedDirectionState implements IKeyDirection {



    // Method for specific movement
    public void specificMove(int key, int select, IRacket racket) {
        if (select == 0) {
            if (key == KeyEvent.VK_LEFT) {
                racket.changeDx(2);
            }
            if (key == KeyEvent.VK_RIGHT) {
                racket.changeDx(-2);
            }
        }
        if (select == 1) {
            if (key == KeyEvent.VK_A) {
                racket.changeDx(2);
            }
            if (key == KeyEvent.VK_D) {
                racket.changeDx(-2);
            }
        }
    }
}
