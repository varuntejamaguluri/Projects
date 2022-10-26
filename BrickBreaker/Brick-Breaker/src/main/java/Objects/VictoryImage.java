package main.java.Objects;

import java.io.IOException;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.java.Interfaces.IImageDisplay;

public class VictoryImage extends ImageDecorator {

    protected Image image;
    
    public VictoryImage( IImageDisplay I ) throws IOException {
        super(I) ;
        this.loadImage();
       
        
    }

   

    @Override
    public void loadImage() throws IOException {
        ImageIcon ii;
        ii = new ImageIcon(ImageIO.read(getClass().getResource("/images/victory_image.png")));
        image = ii.getImage();
    
    }

    @Override
    public Image getImage() throws IOException {
      
        loadImage() ;
        return image ;
    }
    
}
