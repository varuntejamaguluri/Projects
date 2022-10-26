package main.java.Interfaces;

import java.io.IOException;

import java.awt.Image;

public interface IImageDisplay {

     
    void loadImage() throws IOException ;
    Image getImage() throws IOException;
    
}
