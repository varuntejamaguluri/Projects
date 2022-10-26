package main.java.Objects;

import java.io.File;
import java.io.IOException;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.java.Interfaces.IImageDisplay;

public class GameOverImage extends ImageDecorator {

    protected Image image;
    
    public GameOverImage( IImageDisplay I ) throws IOException {
        super(I) ;
        this.loadImage();
       
        
    }

 // Load Image

    @Override
    public void loadImage() throws IOException {
        ImageIcon ii;
        ii = new ImageIcon(ImageIO.read(getClass().getResource("/images/game_over.png")));
        image = ii.getImage();
   
    }

    @Override
    public Image getImage() throws IOException {
      
        loadImage() ;
        return image ;
    }
    
}
