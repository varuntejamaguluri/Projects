package main.java.Interfaces;

import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public interface IRacket {
    
    void loadImage() throws IOException ;
    void move() ;
    void keyPressed(KeyEvent e, int select);
    void keyReleased(KeyEvent e, int select);
    void setDirectionState( int val );
    void resetState();
    void changeDx(int dx);
    void setX(double x);
    double getX();
    void setY(double y);
    double getY();
    int getImageWidth();
    int getImageHeight();
    Image getImage();
    Rectangle2D getRect();
    void getImageDimensions();
}
