package org.kent.assessment4;

import java.awt.Image;
import java.util.Random;

/**
 * FallingEntity class, handles basic actions of all entites 
 * aside the Minecart displayed on screen.
 * 
 * @author Dan Andrews
 * @version 0.1
 */
public abstract class FallingEntity extends Entity{
	
	// The amount of calls made so far to the move method
	private int existanceCalls ;
	// The speed at which to decrement Y
	private int fallingSpeed;
	
	/**
	 * Creates a FallingEntity
	 * @param maxX The width of the JFrame
	 * @param y The Y pixel grid coordinate
	 * @param sprite The image that represents this entity
	 */
	public FallingEntity(int maxX, int y, Image sprite){
		super(new Random().nextInt(maxX)-sprite.getWidth(null), y, sprite);
		fallingSpeed = new Random().nextInt(3)+1;
	}
	
	/**
	 * Updates the Y position of this entity
	 */
	public void move(){
		existanceCalls++;
		setY(getY()  + fallingSpeed);
		
		if(existanceCalls > 30){
			existanceCalls = 0;
			fallingSpeed++;
		}
	}

	/**
	 * Concludes whether the position specified collides with 
	 * the position of this entity. 
	 * @param x The X position
	 * @param y The Y position
	 * @return boolean as to whether they've collided or not
	 */
	public boolean collide(int x, int y) {
		
		int disX = (getImage().getWidth(null) / 2);
		int disY = (getImage().getHeight(null) / 2);
		
		if((getSpriteWidthX()-disX <= x && getSpriteWidthX()+disX >= x)
			&& (getSpriteHeightY()-disY <= y && getSpriteHeightY()+disY >= y)){
			return true;
		}
		
		
		
		return false;
		
	}
	
	/**
	 * The method to absolve the collision of entities.
	 * @param engine The game engine.
	 */
	public abstract void absolve(GameEngine engine);

}
