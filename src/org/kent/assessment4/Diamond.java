package org.kent.assessment4;

import java.awt.Image;

/**
 * Diamond class, handles the working values for this bomb 
 * and passes up to FallingEntity.
 * 
 * @author Dan Andrews
 * @version 0.1
 */
public abstract class Diamond extends FallingEntity{
	
	// Field for storing the score this diamond is worth
	private int score;
	// Field for storing the amount of time this diamond is worth
	private int timeAddition;
	
	/**
	 * Creates a new diamond.
	 * @param maxX The width of the JFrame
	 * @param sprite The image that represents this sprite
	 * @param y The Y pixel grid coordinate
	 * @param score The value in score that this diamond is worth
	 * @param timeAddition The amount of time added to the timer for this diamond
	 */
	public Diamond(int maxX, Image sprite, int y, int score, int timeAddition){
		super(maxX, y, sprite);
		this.score = score;
		this.timeAddition = timeAddition;
	}

	/**
	 * Retrieves the score for this Diamond
	 * @return How much this diamond is worth
	 */
	public int getScore(){
		return this.score;
	}
	
	/**
	 * The amount of time this diamond is worth
	 * @return How much this diamond should add to the timer
	 */
	public int timeAddition(){
		return this.timeAddition;
	}
	
	@Override
	/**
	 * Used to end the game when the player collides with a bomb
	 * @param engine The game engine used to end the game.
	 */
	public void absolve(GameEngine engine) {
	
		engine.addMulitplier(score, timeAddition);
		
	}

}
