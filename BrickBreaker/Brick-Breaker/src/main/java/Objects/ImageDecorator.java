package main.java.Objects;

import java.io.IOException;

import java.awt.Image;

import main.java.Interfaces.*;

public class ImageDecorator implements IImageDisplay {


    private IImageDisplay imageContent;
    protected Image image;
    
    public ImageDecorator( IImageDisplay I ) {
        imageContent = I ;
    }

    @Override
    public void loadImage() throws IOException {
        imageContent.loadImage();
        // TODO Auto-generated method stub
        
    }

    @Override
    public Image getImage() throws IOException {
        imageContent.getImage() ;
        // TODO Auto-generated method stub
        return null;
    }
    
}
