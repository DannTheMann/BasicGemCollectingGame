package org.kent.assessment4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * 
 * The highscore manager is a Serializable object which handles
 * file management and association with all scores achieved in game.
 * 
 * @author danslayerx
 *
 * @version 1
 */
public class HighscoresManager implements Serializable{
	
	// The Serializable ID
	private static final long serialVersionUID = 4684565182076750337L;
	// The directory which is currently storing the file
	private transient String directory;
	// The actual file itself which is handling highscore details.
	private transient File file;
	// An array of all scores from the highscore manager
	private ArrayList<Score> scores;
	
	/**
	 * Creates a new highscore manager which acts default 
	 * if it cannot locate a file that already exists for 
	 * highscores.
	 */
	public HighscoresManager(){
		scores = new ArrayList<>();
		directory = System.getProperty("user.dir")
				+ File.separator + "IWCTG";
		
	}
	
	/**
	 * Enabled the highscore manager, seeks the file
	 * that is holding all current data regarding highscores.
	 * 
	 * If it fails to find the highscore table it will act as
	 * the default handler for highscores. 
	 * 
	 * If it can't save/load or manage the highscore file then
	 * it will return null and disable highscore management ingame.
	 * 
	 * @return The serialized HighscoreManager
	 */
	public HighscoresManager enable() {

		File directory = new File(this.directory);

		try {

			if (!directory.exists()) {

				directory.mkdir();

			}

		} catch (SecurityException e) {
			return null;
		}

		File scores = new File(this.directory + File.separator + "scores.ser");

		file = scores;

		if (scores.exists()) {

			return loadFile();
		} else {

			serailizeFile();

			if (scores.exists()) {
				return loadFile();
			}
		}

		return null;
	}
	
	/**
	 * Serializes the file and stores it at the file path.
	 */
	private void serailizeFile(){
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(file));
			out.writeObject(this);
			out.close();
			System.out.println("Saved file.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to create file for Highscores.");
		}
	}
	
	/**
	 * Loads the file using an ObjectInputStream, should return HighscoreManager
	 * @return The HighscoreManager object for this game.
	 */
	private HighscoresManager loadFile(){
		
		try {
			ObjectInputStream inputStream = 
				new ObjectInputStream (new 
						FileInputStream(file));

			
			HighscoresManager hsm = (HighscoresManager) inputStream.readObject();	
			inputStream.close();
			return hsm;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Gets a score at an index position
	 * @param position The index position
	 * @return The score object at this index
	 */
	public Score getScore(int position){
		if(scores.size() > position){
			return this.scores.get(position);
		}else{
			return null;
		}
	}
	
	/**
	 * Returns the first score based on username
	 * @param name The username of the score keeper
	 * @return The first score applied to this username
	 */
	public Score getScore(String name){
		
		for(Score score : scores){
			
			if(score.getName().equalsIgnoreCase(name)){
				return score;
			}
			
		}
		return null;
		
	}
	
	/**
	 * Retrieves all scores as an ArrayList
	 * @return all scores
	 */
	public ArrayList<Score> getScores(){
		return this.scores;
	}

	/**
	 * Validates whether this object does contain the correct
	 * detailing for scores or has become damaged along the way.
	 */
	public void validate() {
		
		if(this.scores == null){
			this.scores = new ArrayList<Score>();
		}
		
		directory = System.getProperty("user.dir")
				+ File.separator + "IWCTG";
		file = new File(this.directory + File.separator + "scores.ser");
	}

	/**
	 * Saves the file.
	 */
	public void save() {
		
		if(file != null && directory != null){
			serailizeFile();
		}
		
	}

	/**
	 * Adds a new score to the highscore tables.
	 * @param username Username of the score keeper
	 * @param score The score
	 * @param date The date 
	 */
	public void addScore(String username, int score, String date) {
		
		scores.add(new Score(username, score, 0));
		
	}

}
