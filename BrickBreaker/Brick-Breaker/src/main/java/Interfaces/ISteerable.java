package main.java.Interfaces;

import java.awt.event.KeyEvent;

public interface ISteerable {

	public void keyPressed(KeyEvent e, int select);

	public void keyReleased(KeyEvent e, int select);

}