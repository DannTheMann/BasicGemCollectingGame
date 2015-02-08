package org.kent.assessment4;

import java.awt.Image;

/**
 * Bomb class, handles the working values for this bomb 
 * and passes up to FallingEntity.
 * 
 * @author Dan Andrews
 * @version 0.1
 */
public class Bomb extends FallingEntity{
	
	/**
	 * Creates a new bomb
	 * @param maxX The width of the JFrame
	 * @param y The Y pixel grid coordinate
	 * @param sprite The image that represents this sprite
	 */
	public Bomb(int maxX, int y, Image sprite) {
		super(maxX, y, sprite);
	}

	@Override
	/**
	 * Used to end the game when the player collides with a bomb
	 * @param engine The game engine used to end the game.
	 */
	public void absolve(GameEngine engine) {
		
		engine.gameOver();
		
	}

}
