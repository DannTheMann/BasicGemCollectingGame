package org.kent.assessment4;

/**
 * This class controls the game, it is the center of operations. It creates
 * a new object for game and flows the process of the game.
 * 
 * @author Dan Andrews
 * @version 0.1
 */
public class Main
{
	// The main menu for the game which holds all the GUI components is stored here.
    protected static GameMainMenu gameMenu;
    // The resource manager which manages all the .jar resource files.
    protected static ResourceManager resourceManager;
    // The chance of spawning a blue diamond
    public static final double BLUE_DIAMOND_SPAWN_CHANCE = 0.6;
    // The chance of spawning a white diamond
    public static final double WHITE_DIAMOND_SPAWN_CHANCE = 0.6;
    // The chance of spawning a bomb/cherry
    public static final double BOMB_SPAWN_CHANCE = 0.6;
	
    /**
     * Basic funtionality to launch the game. Creates the JFrame, adds
     * components to the GUI and creates the resource manager which then
     * loads all the base images and icons.
     * @param args
     */
    public static void main(String[] args){

        resourceManager = new ResourceManager();
        resourceManager.loadDefaultResources();
    	
    	gameMenu = new GameMainMenu();

    }
    
    public Main(){
    	main(null);
    }

}
