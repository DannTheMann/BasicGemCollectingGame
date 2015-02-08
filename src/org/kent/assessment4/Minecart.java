package org.kent.assessment4;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * The minecart, the base layer of the game itself. 
 * Controlled by the player.
 * 
 * @author Dan Andrews
 *
 * @version 1.5
 */
public class Minecart extends Entity{
	
		// The direction in which the cart is moving
	    private int directionalMovement;
	    // The timer which creates momentum and makes the card slide based on speed
	    private Timer slowDown;
	    // The game engine object
	    private GameEngine engine;
	    // Whether the cart is moving forward or backwards (Right, or left)
	    private boolean forward;

	    /**
	     * Creates a new Minecart object.
	     * @param yPos The X position
	     * @param xPos The Y position
	     * @param engine The game engine itself
	     */
	    public Minecart(int yPos, int xPos, GameEngine engine) {
	    	super(yPos, xPos, Main.resourceManager.getImage("mine_cart_sprite"));
	    	this.engine = engine;        
	        slowDown = new Timer();
	    }

	    /**
	     * Moves the entity, based on directionalMovement
	     * adds speed the more the key is pressed which
	     * causes momentum to build up.
	     */
	    public void move() {
	 
	    	int tempX = getX() + directionalMovement;
	    	
	    	if(hitBoundry(tempX)){
	    		engine.debug("Not moving, at Boundry.");
	    		directionalMovement = 0;
	    		return;
	    	}
	    	
	    	if(directionalMovement > 6)
	    		directionalMovement = 6;
	    	else if(directionalMovement < -6)
	    		directionalMovement = -6;
	    	
	        setX(getX() + directionalMovement);
	        
	        
	    }

	    /**
	     * Key pressed events, passed from Game Engine.
	     * @param e The Event
	     */
	    public boolean keyPressed(KeyEvent e) {

	        int key = e.getKeyCode();
	        
	        if (key == 37) {
	        	increaseMomentum(-3);
	        	return true;
	        }

	        if (key == 39) {
	        	increaseMomentum(+3);
	        	return true;
	        }
	        
	        return false;

	    }

	    /**
	     * Increases the momentum of the minecart
	     * @param addTo How much to increase by
	     */
	    private void increaseMomentum(int addTo) {
			directionalMovement += addTo;		
			slowDown.cancel();
		}
	    
	    /**
	     * Key released events, passed from Game Engine.
	     * @param e The Event
	     */
	    public boolean keyReleased(KeyEvent e) {
	        int key = e.getKeyCode();
	        
	        if (key == 37) {
	        	reduceDirectionalMovement();
	        	return true;
	        }

	        if (key == 39) {
	        	reduceDirectionalMovement();
	        	return true;
	        }
	        
	        return false;

	    }

	    /**
	     * Reduces the directionMovement by a small
	     * amount, causing a sliding effect to the
	     * Minecart
	     */
		private void reduceDirectionalMovement() {
			
			if(slowDown != null){
				slowDown.cancel();
				slowDown = new Timer();
			}
			
			 forward = true;
			 
			if(directionalMovement < 0)
				forward = false;
			
			slowDown.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					
					if(forward){
						directionalMovement--;	
						
						if(directionalMovement <= 0){
							finish();
						}
						
					}else{
						directionalMovement++;	
						
						if(directionalMovement >= 0){
							finish();
						}
					}
					
					if(hitBoundry(getX())){
						finish();
					}
	
					
					
				}

				private void finish() {
					directionalMovement = 0;											
					slowDown.cancel();
				}
			}, 0, 50);
			
		}

		/**
		 * Determines whether the Minecart has hit a boundry 
		 * and must stop.
		 * @param x The current position of the Minecart.
		 * @return
		 */
		protected boolean hitBoundry(int x) {
			
			if(x < 0
				|| x > engine.getWidth()-getImage().getWidth(null))
				return true;
			
			return false;
		}
}
