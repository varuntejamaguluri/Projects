package main.java.Objects;

import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.io.IOException;
//import java.util.HashSet;
import java.util.LinkedHashSet;
//import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import main.java.Config.Configurations;
import main.java.Interfaces.IKeyDirection;
import main.java.com.NormalDirectionState;
import main.java.com.SwitchedDirectionState;
import main.java.Interfaces.*;

//import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Racket implements IRacket {

    public double x;
    public double y;
    protected int imageWidth;
    protected int imageHeight;
    protected Image image;
    private int dx;
    private IKeyDirection directionState ;
    NormalDirectionState normalDirectionState;
    SwitchedDirectionState switchedDirectionState;
    private LinkedHashSet<Integer> activeKeys = new LinkedHashSet<>();

    public Racket(int racket, int select) throws IOException {
        initRacket(racket);
        normalDirectionState = new NormalDirectionState();
        switchedDirectionState = new SwitchedDirectionState();
        directionState = normalDirectionState;
    }

    private void initRacket(int racket) throws IOException {
        loadImage();
        getImageDimensions();
        resetState();
    }

    public void loadImage() throws IOException {
        ImageIcon ii;
        ii = new ImageIcon(ImageIO.read(getClass().getResource("/images/paddle.png")));
        image = ii.getImage();
    }

    public void move() {

        setX(getX() + dx);
        if (getX() <= 0) {
            setX(0);
        }

        if (getX() >= Configurations.WIDTH - getImageWidth()) {
            setX(Configurations.WIDTH - getImageWidth());
        }
    }

    public void keyPressed(KeyEvent e, int select) {
        activeKeys.add(e.getKeyCode());
        directionState.specificMove(e.getKeyCode(), select, this);
    }

    public void keyReleased(KeyEvent e, int select) {
        activeKeys.remove(e.getKeyCode());
        int key = e.getKeyCode();
        if (select == 0) {
            if (key == KeyEvent.VK_LEFT) {
                dx = 0;
            }
            if (key == KeyEvent.VK_RIGHT) {
                dx = 0;
            }
        }
        if (select == 1) {
            if (key == KeyEvent.VK_A) {
                dx = 0;
            }
            if (key == KeyEvent.VK_D) {
                dx = 0;
            }
        }
        if (!activeKeys.isEmpty()) {
            directionState.specificMove((int)activeKeys.toArray()[activeKeys.size()-1], select, this);
        }
    }

    public void setDirectionState( int val ) {
        if ( val == 0 ) {
            directionState = normalDirectionState ;
        } else if ( val == 1 ) {
            directionState = switchedDirectionState ;
        }
    }

    public void resetState() {
       // setLocation(Configurations.INIT_PADDLE_X, Configurations.INIT_PADDLE_Y);
       x = Configurations.INIT_PADDLE_X;
       y = Configurations.INIT_PADDLE_Y;
    }

    public void changeDx(int dx) {
        this.dx = dx;
    }


    Rectangle2D rectangle2D = new Rectangle2D.Double();

    public void setX(double x) {

        this.x = x;
    }

    public double getX() {

        return x;
    }

    public void setY(double y) {

        this.y = y;
    }

    public double getY() {

        return y;
    }

    public int getImageWidth() {

        return imageWidth;
    }

    public int getImageHeight() {

        return imageHeight;
    }

    public Image getImage() {

        return image;
    }

    public Rectangle2D getRect() {
        rectangle2D.setRect(x, y, image.getWidth(null), image.getHeight(null));

        return rectangle2D;
        // return new Rectangle(x, y,
        // image.getWidth(null), image.getHeight(null));
    }

    public void getImageDimensions() {

        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
}