package main.java.Interfaces;

public interface ICollider {

	public boolean collidesWith(ICollider gameObject);

	public int[] getBoundaries();

	public void handleCollision();

	public boolean getRemoveFlag();

}