package org.kent.assessment4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

/**
 * Write a description of class Menu here.
 * 
 * @author Dan Andrews
 * @version a_3.1
 */
public class GameMainMenu {

	/*
	 * All components handled by the Frame are below, 
	 * including the frame itself. Mostly used for
	 * ease of access or updating the object without
	 * the need to declare it as final when using
	 * an action listener.
	 */
	
	private JFrame frame;
	
	private IPanel panel;
	
	private JMenuBar menuBar;

	private JButton newGame;
	private JButton highscores;
	private JButton about;
	private JButton debug;

	private JLabel timeLimitLabel;
	private JLabel recordScoreLabel;
	private JLabel entityLimitLabel;
	
	private JCheckBox timeLimit;
	private JCheckBox recordScore;
	
	private JComboBox<Integer> entityLimits;
	
	private JButton startGame;
	private JButton back;

	// The selected dropdown position on the New-Game menu
	private int selectedIndex;
	
	// The screenshot handler deals with storing, taking and masking
	// screenshots. 
	private ScreenshotHandler screenshotHandler;
	// Highscore manager deals with saving, serialiazing and managing all highscores
	private HighscoresManager highscoreManager;
	
	// The game engine itself, used to handle the game.
	private GameEngine game;

	/**
	 * Creates the default window for users to interact with.
	 */
	public GameMainMenu() {
		selectedIndex = 1;
		frame = new JFrame("Gem Catcher (Assessment 4)");
		frame.setSize(800, 680);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);

		panel = new IPanel();

		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent event) {

				if (JOptionPane.showConfirmDialog(null, "Do you wish to quit?",
						"Exit Game", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					// quit = true;

					close();

				} else
					return;

			}

			private void close() {
				highscoreManager.save();
				System.exit(0);

			}

		});

		screenshotHandler = new ScreenshotHandler();
		game = new GameEngine(this);
		highscoreManager = new HighscoresManager();

		HighscoresManager loadedScore = highscoreManager.enable();

		if (loadedScore == null) {
			JOptionPane
					.showMessageDialog(
							frame,
							"Unable to create highscores file \n highscores will be disabled. "
									+ "\n \n This is most likely due to file permissions.");
			highscoreManager = null;
		} else {
			this.highscoreManager = loadedScore;
			highscoreManager.validate();
		}

		constructFrameWindow();

		constructMainMenu();

		frame.setVisible(true);

	}

	/**
	 * Constructs the Main Menu
	 */
	public void constructMainMenu() {

		frame.remove(game);

		panel.removeAll();

		panel.changeImageResource("plane");

		panel.revalidate();
		panel.repaint();

		game.debug("Setting background image.");

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		newGame = new JButton("New Game");
		newGame.setFont(new Font("comicsans", Font.BOLD, 18));

		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				openNewGameMenu();

			}

		});

		highscores = new JButton("High Scores");

		if (highscoreManager != null) {

			highscores.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent event) {

					loadHighScores();

				}

			});

		} else {
			highscores.setEnabled(false);
		}

		about = new JButton("About");
		about.setFont(new Font("comicsans", Font.BOLD, 14));
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane
						.showMessageDialog(
								null,
								"I wanna catch the guy!"
										+ "\n Version "
										+ GameEngine.VERSION
										+ "\n Programmed by Dan Andrews"
										+ "\n Email: dja30@kent.ac.uk \n\n Kent University Assessment ");

			}

		});

		debug = new JButton("Console");
		debug.setFont(new Font("comicsans", Font.BOLD, 14));
		debug.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.setDebugEnabled(!game.isDebugEnabled());
			}

		});

		panel.add(Box.createRigidArea(new Dimension(10, 220)));
		panel.add(newGame);
		panel.add(Box.createRigidArea(new Dimension(10, 5)));
		panel.add(highscores);
		panel.add(Box.createRigidArea(new Dimension(10, 5)));
		panel.add(about);

		frame.add(panel);

		frame.setLocationRelativeTo(null);

		frame.validate();

		game.debug("Constructed main menu frame.");

	}

	/**
	 * Constructs the Frames top window, handling options at the top.
	 */
	private void constructFrameWindow() {

		// Taken from ImageViewer 0-4 project.
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();
		// End Code taken from ImageViewer 0-4 project.

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu menu = new JMenu("Options");
		menuBar.add(menu);

		JMenuItem item = new JMenuItem("Screenshot");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,
				SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(screenshotHandler.takeScreenshot(frame));
			}
		});

		menu.add(item);
		
		item = new JCheckBoxMenuItem("Debugging Console");
		item.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_D, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.setDebugEnabled(!game.isDebugEnabled());
			}
		});

		menu.add(item);
		
		menu.addSeparator();

		item = new JMenuItem("About");
		item.setAccelerator(KeyStroke
				.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								frame,
								"I wanna catch the guy!"
										+ "\n Version "
										+ GameEngine.VERSION
										+ "\n Programmed by Dan Andrews"
										+ "\n Email: dja30@kent.ac.uk \n\n Kent University Assessment ");
			}
		});
		
		menu.add(item);

		item = new JMenuItem("Help");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
				.showMessageDialog(
						frame,
						"This is a very simple game, it involves \n collecting diamonds and avoiding bombs. " +
						"\n Use the left and right arrow keys to move \n the minecart. You can enable \n time limits, have high scores and \n" +
						"take screenshots. \n \n \n Be aware, if you're running this on a university machine \n it's likely it might not allow you \n" +
						"to save highscores or screenshots \n due to the permissions on the machines. \n Due to this, it's highly advised you \n" +
						"run this on your own machine. \n \n Please enjoy :) ");
			}
		});

		menu.add(item);

	}

	/**
	 * Opens the new game menu, which uses a lot of components to deal with
	 * setting and looking after the game.
	 */
	public void openNewGameMenu() {

		game.debug("Changing menu screens");

		panel.removeAll();

		panel.changeImageResource("plant");

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.revalidate();
		panel.repaint();

		game.debug("Menu screen changed, components removed");

		timeLimitLabel = new JLabel(
				"<html> 60 second limit <br> picking up diamonds increases time. </html>");
		recordScoreLabel = new JLabel(
				"<html> Add this play-attempt to <br> the highscores. </html>");
		entityLimitLabel = new JLabel(
				"<html> Select how many entites <br> will be on screen at once. </html>");

		timeLimit = new JCheckBox("Time Limit", new ImageIcon(
				Main.resourceManager.getImage("checkbox_unselected")), true);
		timeLimit.setSelectedIcon(new ImageIcon(Main.resourceManager
				.getImage("checkbox_selected")));
		timeLimit.setSelected(game.isTimeLimitEnabled());
		timeLimit.setOpaque(false);

		recordScore = new JCheckBox("Record Score", new ImageIcon(
				Main.resourceManager.getImage("checkbox_unselected")), true);
		recordScore.setSelectedIcon(new ImageIcon(Main.resourceManager
				.getImage("checkbox_selected")));
		recordScore.setSelected(game.isRecordHighScore());
		recordScore.setOpaque(false);

		Integer[] entityLimitsArray = new Integer[11];

		for (int i = 0; i < 10; i++) {
			entityLimitsArray[i] = (i + 1) * 5;
		}
		entityLimitsArray[10] = 10000;

		entityLimits = new JComboBox<Integer>(entityLimitsArray);

		entityLimits.setSelectedIndex(selectedIndex);
		entityLimits.setMaximumSize(entityLimits.getPreferredSize());
		entityLimits.setName("Total Entites");

		entityLimits.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.setEntityLimits((int) entityLimits.getSelectedItem());
				selectedIndex = entityLimits.getSelectedIndex();
			}
		});

		startGame = new JButton("Start Game");
		startGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
			}

		});

		back = new JButton("Main Menu");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				constructMainMenu();
			}
		});

		panel.add(Box.createRigidArea(new Dimension(10, 130)));
		panel.add(timeLimitLabel);
		panel.add(timeLimit);
		panel.add(recordScoreLabel);
		panel.add(recordScore);
		panel.add(entityLimitLabel);
		panel.add(Box.createRigidArea(new Dimension(15, 10)));
		panel.add(entityLimits);
		panel.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(back);
		panel.add(Box.createRigidArea(new Dimension(10, 10)));
		panel.add(startGame);

		timeLimit.setEnabled(!game.isRecordHighScore());
		timeLimit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.setTimeLimitEnabled(!game.isTimeLimitEnabled());

				game.debug("Time limit: " + game.isTimeLimitEnabled());
			}
		});

		recordScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.setRecordHighScore(!game.isRecordHighScore());

				if (game.isRecordHighScore()) {
					game.debug("Enabling High Score and Time Limit.");
					timeLimit.setSelected(true);
				}

				timeLimit.setEnabled(!game.isRecordHighScore());
			}
		});

		frame.validate();

	}

	/**
	 * Starts the game, which then uses game engine to forward this request.
	 */
	protected void startGame() {

		panel.removeAll();

		panel.clearImage();

		panel.revalidate();
		panel.repaint();

		frame.remove(panel);

		frame.add(game);

		game.start();

	}

	/**
	 * Constructs the game over menu, this allows users to
	 * submit a highscore, retry or return to the Main Menu.
	 */
	public void constructGameOver() {

		frame.remove(game);

		panel.removeAll();

		panel.changeImageResource("game_over");

		panel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel finalScore = new JLabel("Final Score: " + game.getScore());
		finalScore.setOpaque(true);
		finalScore.setForeground(Color.yellow);

		final JButton submitScore = new JButton("Submit Score: "
				+ game.getScore());

		if (!game.isRecordHighScore()) {
			submitScore.setEnabled(false);
		}

		submitScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (highscoreManager == null) {
					JOptionPane
							.showMessageDialog(
									frame,
									"Unable to create highscores file \n highscores will be disabled. "
											+ "\n \n This is most likely due to file permissions.");
					return;
				}

				submitScore.setEnabled(false);

				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy_MM_dd_HH_mm_ss");

				String input = JOptionPane.showInputDialog(
						"Enter your selected username",
						System.getProperty("user.name"));

				if (input.length() <= 0)
					input = System.getProperty("user.name");

				highscoreManager.addScore(input, game.getScore(),
						dateFormat.format(new Date()));

				JOptionPane.showMessageDialog(frame, "User: " + input
						+ " \n Final Score: " + game.getScore()
						+ "\n\n Well done :)");

				highscoreManager.save();

			}

		});

		JButton quit = new JButton("Menu");
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				constructMainMenu();
			}

		});

		JButton retry = new JButton("Retry");
		retry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openNewGameMenu();
			}

		});

		frame.add(panel);

		panel.add(retry);
		panel.add(submitScore);
		panel.add(quit);

		panel.revalidate();
		panel.repaint();

	}

	/**
	 * Loads all current highscores that are managed by the highscore 
	 * manager. Represents them in a table.
	 */
	public void loadHighScores() {

		frame.remove(game);

		panel.removeAll();

		panel.changeImageResource("highscore_background");

		panel.setLayout(new FlowLayout(FlowLayout.CENTER));

		Object[][] data = new Object[highscoreManager.getScores().size()][3];
		String[] columnNames = { "Username", "Score   ", "Date & Time" };

		int count = 0;

		for (Score score : highscoreManager.getScores()) {

			data[count][0] = score.getName();
			data[count][1] = score.getScore();
			data[count][2] = score.getDateStamp();

			count++;
		}

		JTable table = new JTable(data, columnNames);

		table.setEnabled(false);

		JButton back = new JButton("Main Menu");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				constructMainMenu();

			}
		});

		panel.add(table);
		panel.add(back);

		panel.validate();
		panel.repaint();

	}

}
