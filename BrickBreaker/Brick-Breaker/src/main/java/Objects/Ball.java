package main.java.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import main.java.Config.Configurations;

import java.io.IOException;

public class Ball extends Sprite {

    private double xdir;
    private double ydir;


    public Ball() throws IOException {

        initBall();
    }

    // Ball Init
    private void initBall() throws IOException {

        xdir = 1;
        ydir = -1;

        loadImage();
        getImageDimensions();
        resetState();
    }
// Load Image
    private void loadImage() throws IOException {

        var ii = new ImageIcon(ImageIO.read(getClass().getResource("/images/ball.png")));
        image = ii.getImage();

    }
// Move method
    public void move() {

        x += xdir;
        y += ydir;

        if (x + 2 >= (Configurations.WIDTH)) {
            xdir *= -1;
            setXDir(xdir);
        } else if (x <= 0) {
            xdir *= -1;
            x = xdir;
        } else if (y <= 0) {
            ydir *= -1;
            y = ydir;
        }

        if (y + 2 >= (Configurations.HEIGHT)) {
            ydir *= -1;
            y = ydir;
        }
    }
// Reset state
    private void resetState() {
        x = Configurations.INIT_BALL_X;
        y = Configurations.INIT_BALL_Y;
    }

    // Set X dir
    public void setXDir(double x) {

        xdir = x;
    }
// Set Y dir
    public void setYDir(double y) {

        ydir = y;
    }

    // get Y dir
    public double getYDir() {

        return ydir;
    }
}