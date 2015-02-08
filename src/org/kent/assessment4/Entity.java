package org.kent.assessment4;

import java.awt.Image;

/**
 * 
 * The top of the food chain when it comes to handling
 * entities. Stores the basic information for
 * all entites such as grid location and sprite imaging.
 * 
 * @author danslayerx
 *
 * @version 1
 */
public abstract class Entity {
	
	// The X position on screen of this Entity
	private int x;
	// The Y position on screen of this Entity
	private int y;
	// The sprite image representing this Entity
    private Image sprite;
    
    public Entity(int x, int y, Image sprite){
    	this.x = x;
    	this.y = y;
    	this.sprite = sprite;
    }  

	
	/**
	 * Retrieves the center width position of this image
	 * @return The width divided by 2
	 */
    public int getSpriteWidthX() {
        return x + (sprite.getWidth(null) / 2);
    }
    
	/**
	 * Retrieves the center height position of this image
	 * @return The height divided by 2
	 */
    public int getSpriteHeightY() {
        return y + (sprite.getHeight(null) / 2);
    }
    
    /**
     * Get the base location of this entity (Y)
     * @return
     */
    public int getY() { return y; }
    
    /**
     * Get the base location of this entity (X)
     * @return
     */
    public int getX() { return x; }

	/**
	 * Retrieves the sprite used to display this entity
	 * @return Image object for this entity
	 */
    public Image getImage() {
        return sprite;
    }
    
    /**
     * Sets the X position
     * @param x the position for X
     */
    public void setX(int x){
    	this.x = x;
    }
    
    /**
     * Sets the Y Position
     * @param y the positon for Y
     */
    public void setY(int y){
    	this.y = y;
    }
    
    /**
     * A method which forces the entity to move.
     */
    public abstract void move();

}
