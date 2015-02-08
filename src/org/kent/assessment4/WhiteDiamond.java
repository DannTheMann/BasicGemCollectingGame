package org.kent.assessment4;

import java.awt.Image;

/**
 * White diamond class, handles the working values for this diamond 
 * and passes up to FallingEntity.
 * 
 * @author Dan Andrews
 * @version 0.1
 */
public class WhiteDiamond extends Diamond{

	/**
	 * Creates a new white-diamond.
	 * @param maxX The width of the JFrame
	 * @param y The Y pixel grid coordinate
	 * @param sprite The image that represents this sprite
	 */
	public WhiteDiamond(int maxX, int y, Image sprite) {
		super(maxX, sprite, y, 2, 2);
	}

}
