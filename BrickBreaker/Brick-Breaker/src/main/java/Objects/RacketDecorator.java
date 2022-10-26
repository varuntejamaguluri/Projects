package main.java.Objects;

import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import main.java.Interfaces.*;

public class RacketDecorator implements IRacket{

    private IRacket racket;
    public double x;
    public double y;
    protected int imageWidth;
    protected int imageHeight;
    protected Image image;
    
    public RacketDecorator( IRacket R ) {
        racket = R ;
    }

    public void loadImage() throws IOException {
        racket.loadImage() ;
    }

    public void move() {
        racket.move() ;
    }

    public void keyPressed(KeyEvent e, int select) {
        racket.keyPressed(e, select);
    }

    public void keyReleased(KeyEvent e, int select) {
        racket.keyPressed(e, select);
    }

    public void setDirectionState( int val ) {
        racket.setDirectionState(val);
    }

    public void resetState() {
       racket.resetState();
    }

    public void changeDx(int dx) {
        racket.changeDx(dx);
    }

    public void setX(double x) {
        racket.setX(x);
    }

    public double getX() {
        return racket.getX();
    }

    public void setY(double y) {
        racket.setY(y);
    }

    public double getY() {
        return racket.getY();
    }

    public int getImageWidth() {
        return racket.getImageWidth();
    }

    public int getImageHeight() {
        return racket.getImageHeight();
    }

    public Image getImage() {
        return racket.getImage();
    }

    public Rectangle2D getRect() {
        return racket.getRect();  
    }

    public void getImageDimensions() {
        racket.getImageDimensions();
    }
}
