package org.kent.assessment4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The main point of the entire game logic, handles the timers for
 * the counting, repainting and updating the screen. Handles returning
 * high score values, spawning entities and controlling the movements of
 * the user.
 * 
 * @author Dan Andrews
 * @version 0.1
 */
public class GameEngine extends JPanel {

	// Serialiazable key
	private static final long serialVersionUID = 3390093254070301417L;

	// Final version
	public static final String VERSION = "a_0.3";

	// Whether debug messages should be displayed in the console
	private boolean debugEnabled = false;
	
	// Whether there is a time limit for this game
	private boolean timeLimitEnabled;
	
	// Whether recording of high score values is allowed
	private boolean recordHighScore;
	
	// The current time left before the game ends, defaultly starts at 60
	private int time;
	
	// The maximum allowed entities on screen at once, aside the Minecart
	private int maxAllowedEntites;
	
	// The current score achieved for this game
	private int score;
	
	// The minecart that player controls
	private Minecart cart;
	
	// The Timer object for dealing with the time counting down
	private Timer countDownTimer;
	
	// The Timer object for dealing with updating the screen
	private Timer gameThread;
	
	// The list of all current entities, once removed they're handled by the garbage collector.
	private ArrayList<FallingEntity> fallingEntites;

	// The label which is used to display how much time remains
	private JLabel timeLeft;
	
	// The label which is used to display how much score you've achieved
	private JLabel scoreLabel;

	// The main menu object, used to call and display GUI components
	private GameMainMenu menu;

	/**
	 * The main piece to the game engine is it's constructor.
	 * 
	 * Creates the default settings used by the game, and prepares
	 * for immediate use.
	 * @param menu The Main Menu
	 */
	public GameEngine(GameMainMenu menu) {
		this.menu = menu;
		timeLimitEnabled = true;
		recordHighScore = true;
		time = 60;
		countDownTimer = new Timer();
		gameThread = new Timer();
		maxAllowedEntites = 15;

		fallingEntites = new ArrayList<>();

		constructPanel();

	}

	/**
	 * Constructs the panel for which the game will display in.
	 */
	private void constructPanel() {

		timeLeft = new JLabel("Time Left: " + time);
		timeLeft.setForeground(Color.WHITE);

		scoreLabel = new JLabel("[Score: " + score + "]");
		scoreLabel.setForeground(Color.WHITE);

		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		add(timeLeft);
		add(scoreLabel);

		KeyWatcher kw = new KeyWatcher();
		KeyboardFocusManager kfm = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher(kw);

	}

	/**
	 * If debug is enabled, will output messages to console.
	 * @param log The message to output
	 */
	public void debug(String log) {
		
		if (isDebugEnabled()) {
			
			System.out.println(log);
			
		}

	}

	@Override
	/**
	 * Paints the current frame with the entities
	 * @param g The frame graphics
	 */
	public void paint(Graphics g) {

		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(cart.getImage(), cart.getX(), cart.getY(), this);

		for (FallingEntity entity : fallingEntites) {
			g2d.drawImage(entity.getImage(), entity.getX(), entity.getY(),
					this);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();

	}

	/**
	 * Whether debug is enabled
	 * @return debug enabled
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/**
	 * Whether to toggle debug mode
	 * @param debug_enabled Enable or disable debug
	 */
	public void setDebugEnabled(boolean debug_enabled) {
		this.debugEnabled = debug_enabled;

		System.out.println("Debug Enabled: " + isDebugEnabled());
	}

	/**
	 * Counter task, decrements total time and updates
	 * if counter reaches 0 terminates the game and displays
	 * 'Game Over' menu
	 */
	public void task() {

		time--;
		timeLeft.setText("Time Left: " + time);

		if (time <= 0) {
			gameOver();
		}

	}

	/**
	 * Cancel the current game, disables both timers
	 */
	private void cancel() {
		countDownTimer.cancel();
		gameThread.cancel();

		countDownTimer = null;
		gameThread = null;
	}

	/**
	 * End the game and display Game Over menu
	 */
	public void gameOver() {

		cancel();

		menu.constructGameOver();

	}

	/**
	 * Whether there is a time limit for this session
	 * @return time limit session
	 */
	public boolean isTimeLimitEnabled() {
		return timeLimitEnabled;
	}

	/**
	 * Enable or disable whether this session will have a time limit
	 * @param timeLimitEnabled time limit session
	 */
	public void setTimeLimitEnabled(boolean timeLimitEnabled) {
		this.timeLimitEnabled = timeLimitEnabled;
	}

	/**
	 * returns the time remaining for this session.
	 * @return int time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Starts the game, prepares by clearing all values which
	 * could inflate the game such as score.
	 * 
	 * Sets-up timers to handle the time limit and refreshing the 
	 * screen
	 */
	public void start() {

		scoreLabel.setText("[Score: 0]");

		score = 0;
		time = 60;

		fallingEntites.clear();

		if (timeLimitEnabled) {
			timeLeft.setVisible(true);
		} else {
			timeLeft.setVisible(false);
		}

		gameThread = new Timer();
		countDownTimer = new Timer();

		cart = new Minecart(0, 0, this);
		
		cart.setX((getParent().getWidth()/2) - cart.getImage().getWidth(null));
		cart.setY(getParent().getHeight()-cart.getImage().getHeight(null));
		
		gameThread.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				updateScreen();
			}
		}, 1000, 30);

		if (timeLimitEnabled) {

			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					task();
				}
			};

			countDownTimer.scheduleAtFixedRate(task, 1000, 1000);

		}
	}

	/**
	 * Whether the session should allow recording of highscores
	 * @return store highscores
	 */
	public boolean isRecordHighScore() {
		return recordHighScore;
	}

	/**
	 * Set whether the session should allow recording of highscores
	 * @param recordHighScore store highscores
	 */
	public void setRecordHighScore(boolean recordHighScore) {
		this.recordHighScore = recordHighScore;
	}

	/**
	 * Update the current screen, move entities and 
	 * determine whether they need to be removed and
	 * handled by the garbage collector or if they've
	 * collided with the players minecart
	 */
	public void updateScreen() {

		try {

			cart.move();

			for (int i = 0; i < fallingEntites.size(); i++) {

				FallingEntity entity = fallingEntites.get(i);

				entity.move();

				if (entity.getY() > getParent().getHeight()) {
					debug("Removed entity.");
					fallingEntites.remove(entity);
					i--;
				} else if (entity.collide(cart.getSpriteWidthX(), cart.getSpriteHeightY())) {
					debug("Entity Collided");
					entity.absolve(this);
					fallingEntites.remove(entity);
				}

			}

			if (fallingEntites.size() < maxAllowedEntites) {

				int maxX = getParent().getWidth();

				if (new Random().nextDouble() <= Main.BLUE_DIAMOND_SPAWN_CHANCE) {
					fallingEntites.add(new BlueDiamond(maxX, 0,
							Main.resourceManager.getImage("navy_diamond")));
				}
				if (new Random().nextDouble() <= Main.WHITE_DIAMOND_SPAWN_CHANCE) {
					fallingEntites.add(new WhiteDiamond(maxX, 0,
							Main.resourceManager.getImage("white_diamond")));
				}
				if (new Random().nextDouble() <= Main.BOMB_SPAWN_CHANCE) {
					fallingEntites.add(new Bomb(maxX, 0, Main.resourceManager
							.getImage("bomb")));
				}

			}

		} catch (Exception e) {
			return;
		}

		repaint();
	}

	/**
	 * 
	 * Class within class, handles key events since the Key Listener is
	 * a complete utter pain to work with. Requires a lot of tinkering, this
	 * solution allows for the JFrame to always have focus on all keyboard events.
	 * 
	 * @author danslayerx
	 * @version 1
	 */
	public class KeyWatcher implements KeyEventDispatcher {
		
		@Override
		/**
		 * Determines whether the key input was valid for the Minecart to move.
		 * @param event The KeyEvent
		 */
		public boolean dispatchKeyEvent(KeyEvent event) {

			if(cart == null)
				return false;
			
			if (event.getID() == KeyEvent.KEY_PRESSED) {

				cart.keyPressed(event);
					//updateScreen();

			} else if (event.getID() == KeyEvent.KEY_RELEASED) {

				cart.keyReleased(event);
					//updateScreen();

			}

			return false;
		}

	}

	/**
	 * Adds to the current score and time.
	 * @param score The score to add.
	 * @param timeAddition The time to add.
	 */
	public void addMulitplier(int score, int timeAddition) {

		this.score = this.score + score;
		;
		this.time += timeAddition;

		scoreLabel.setText("[Score: " + this.score + "]");

	}

	/**
	 * Set the maximum allowed entites on screen aside the Minecart
	 * @param limit The integer limit
	 */
	public void setEntityLimits(int limit) {
		this.maxAllowedEntites = limit;
	}

	/**
	 * Gets the current score this session has achieved
	 * @return score
	 */
	public int getScore() {
		return score;
	}

}
