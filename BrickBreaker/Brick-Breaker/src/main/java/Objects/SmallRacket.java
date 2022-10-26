package main.java.Objects;

import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import main.java.Interfaces.*;
import main.java.Config.Configurations;
import main.java.Interfaces.IKeyDirection;
import main.java.com.NormalDirectionState;
import main.java.com.SwitchedDirectionState;
import java.util.LinkedHashSet;
import java.awt.geom.Rectangle2D;

public class SmallRacket extends RacketDecorator {

    public double x;
    public double y;
    protected int imageWidth;
    protected int imageHeight;
    protected Image image;
    private int dx;
    private IKeyDirection directionState ;
    NormalDirectionState normalDirectionState;
    SwitchedDirectionState switchedDirectionState;
    Rectangle2D rectangle2D = new Rectangle2D.Double();
    private LinkedHashSet<Integer> activeKeys = new LinkedHashSet<>();
    
    public SmallRacket( IRacket R ) throws IOException {
        super(R) ;
        loadImage();
        getImageDimensions();
        resetState();
        normalDirectionState = new NormalDirectionState();
        switchedDirectionState = new SwitchedDirectionState();
        directionState = normalDirectionState;
    }

    @Override
    public void loadImage() throws IOException {
        ImageIcon ii;
        ii = new ImageIcon(ImageIO.read(getClass().getResource("/images/shortPaddle.png")));
        image = ii.getImage();
    }

    @Override
    public void move() {
        setX(getX() + dx);
        if (getX() <= 0) {
            setX(0);
        }

        if (getX() >= Configurations.WIDTH - getImageWidth()) {
            setX(Configurations.WIDTH - getImageWidth());
        }
    }

    @Override
    public void keyPressed(KeyEvent e, int select) {
        activeKeys.add(e.getKeyCode());
        directionState.specificMove(e.getKeyCode(), select, this);
    }

    @Override
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

    @Override
    public void setDirectionState( int val ) {
        if ( val == 0 ) {
            directionState = normalDirectionState ;
        } else if ( val == 1 ) {
            directionState = switchedDirectionState ;
        }
    }

    @Override
    public void resetState() {
        // setLocation(Configurations.INIT_PADDLE_X, Configurations.INIT_PADDLE_Y);
        x = Configurations.INIT_PADDLE_X;
        y = Configurations.INIT_PADDLE_Y;
    }

    @Override
    public void changeDx(int dx) {
        this.dx = dx;
    }
   
    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getX() {

        return x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getImageWidth() {
        return imageWidth;
    }

    @Override
    public int getImageHeight() {
        return imageHeight;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Rectangle2D getRect() {
        rectangle2D.setRect(x, y, image.getWidth(null), image.getHeight(null));
        return rectangle2D;
    }

    @Override
    public void getImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }



}
